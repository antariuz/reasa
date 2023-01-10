package avada.spacelab.reasa.service.user;

import avada.spacelab.reasa.config.security.jwt.JwtUtils;
import avada.spacelab.reasa.dto.auth.FacebookUser;
import avada.spacelab.reasa.dto.auth.GoogleUser;
import avada.spacelab.reasa.dto.auth.request.IdTokenRequest;
import avada.spacelab.reasa.dto.auth.response.AccessRefreshTokenResponse;
import avada.spacelab.reasa.dto.auth.request.LoginRequest;
import avada.spacelab.reasa.model.User;
import avada.spacelab.reasa.model.UserProfile;
import avada.spacelab.reasa.repo.UserRepo;
import avada.spacelab.reasa.service.refreshToken.RefreshTokenService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final ObjectMapper mapper;
    private final RefreshTokenService refreshTokenService;

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.existsUserByEmail(email);
    }

    @Override
    public User loadUserByUsername(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public AccessRefreshTokenResponse login(LoginRequest loginRequest) {
        log.info("get login response email:{}, password:{}", loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, String> tokens = createNewTokens(loginRequest.getEmail());

        AccessRefreshTokenResponse responseDto = new AccessRefreshTokenResponse();
        responseDto.setAccessToken(tokens.get("accessToken"));
        responseDto.setRefreshToken(tokens.get("refreshToken"));
        log.info("success get login response");
        return responseDto;
    }

    @Override
    public AccessRefreshTokenResponse localRegistration(LoginRequest loginRequest) {
        log.info("get registration response email:{}, password:{}", loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, String> tokens = createNewTokens(loginRequest.getEmail());

        User user = new User();
        user.setEmail(loginRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(loginRequest.getPassword()));
        userRepo.save(user);

        AccessRefreshTokenResponse responseDto = new AccessRefreshTokenResponse();
        responseDto.setAccessToken(tokens.get("accessToken"));
        responseDto.setRefreshToken(tokens.get("refreshToken"));
        log.info("success get registration response");
        return responseDto;
    }

    public Map<String, String> createNewTokens(String email) {
        log.info("create tokens email: {}", email);

        String accessToken = jwtUtils.generateJwtToken(email);
        String refreshToken = refreshTokenService.createToken(email);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        log.info("success create tokens");
        return response;
    }

    @Override
    public void registration(String email, String name, User.Provider provider) {
        log.info("register new {} user", provider);
        User user = new User();
        user.setEmail(email);
        UserProfile userProfile = new UserProfile();
        userProfile.setNickname(name);
        user.setUserProfile(userProfile);
        user.setProvider(provider);

        userRepo.save(user);
        log.info("success register new user");
    }

    @Override
    public void update(String email, String name, User.Provider provider) {
        log.info("update {} user", provider);
//        userRepo.update(email, name, provider);
        log.info("success update user");
    }

    @Override
    public ResponseEntity<?> getTokensByProvider(IdTokenRequest idTokenRequest, User.Provider provider) {
        OkHttpClient client = new OkHttpClient();
        ResponseEntity<?> responseEntity = null;

        switch (provider) {
            case GOOGLE: {
                Request request = new Request.Builder()
                        .url("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idTokenRequest.getIdToken())
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if(response.isSuccessful()){
                        GoogleUser googleUser = mapper.readValue(response.body().string(), new TypeReference<>(){});
                        responseEntity = new ResponseEntity<>(getTokensByGoogleUser(googleUser), HttpStatus.OK);
                    } else {
                        responseEntity = new ResponseEntity<>(response.body().string(), HttpStatus.BAD_REQUEST);
                    }
                } catch (IOException e) {
                    responseEntity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                }
                break;
            }
            case FACEBOOK: {
                Request request = new Request.Builder()
                        .url("https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=" + idTokenRequest.getIdToken())
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if(response.isSuccessful()){
                        FacebookUser facebookUser = mapper.readValue(response.body().string(), new TypeReference<>(){});
                        responseEntity = new ResponseEntity<>(getTokensByFacebookUser(facebookUser), HttpStatus.OK);
                    } else {
                        responseEntity = new ResponseEntity<>(response.body().string(), HttpStatus.BAD_REQUEST);
                    }
                } catch (IOException e) {
                    responseEntity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                }
                break;
            }
        }
        return responseEntity;
    }

    public AccessRefreshTokenResponse getTokensByGoogleUser(GoogleUser googleUser) {
        if(userRepo.existsUserByEmail(googleUser.getEmail())){
            log.info("success search google user");
//            log.info("update google user");
//            userRepo.updateUser(googleUser.getEmail(), User.Provider.GOOGLE);
//            userRepo.updateUserProfile(googleUser.getEmail(), googleUser.getGiven_name(), googleUser.getName());
//            log.info("success update google user");
        } else {
            log.info("save google user");
            User user = new User();
            user.setEmail(googleUser.getEmail());
            user.setProvider(User.Provider.GOOGLE);
            UserProfile userProfile = new UserProfile();
            userProfile.setNickname(googleUser.getGiven_name());
            userProfile.setFullName(googleUser.getName());
            user.setUserProfile(userProfile);
            userRepo.save(user);
            log.info("success save google user");
        }
        log.info("get tokens by google user");
        Map<String, String> tokens = createNewTokens(googleUser.getEmail());

        AccessRefreshTokenResponse responseDto = new AccessRefreshTokenResponse();
        responseDto.setAccessToken(tokens.get("accessToken"));
        responseDto.setRefreshToken(tokens.get("refreshToken"));
        log.info("get tokens by google user");
        return responseDto;
    }

    public AccessRefreshTokenResponse getTokensByFacebookUser(FacebookUser facebookUser) {
        if(userRepo.existsUserByEmail(facebookUser.getEmail())){
            log.info("success search facebook user");
//            log.info("update facebook user");
//            userRepo.updateUser(facebookUser.getEmail(), User.Provider.GOOGLE);
//            userRepo.updateUserProfile(facebookUser.getEmail(), facebookUser.getFirst_name(),
//                    facebookUser.getFirst_name() + " " + facebookUser.getLast_name());
//            log.info("success update facebook user");
        } else {
            log.info("save facebook user");
            User user = new User();
            user.setEmail(facebookUser.getEmail());
            user.setProvider(User.Provider.GOOGLE);
            UserProfile userProfile = new UserProfile();
            userProfile.setNickname(facebookUser.getFirst_name());
            userProfile.setFullName(facebookUser.getFirst_name() + " " + facebookUser.getLast_name());
            user.setUserProfile(userProfile);
            userRepo.save(user);
            log.info("success save facebook user");
        }
        log.info("get tokens by facebook user");
        Map<String, String> tokens = createNewTokens(facebookUser.getEmail());

        AccessRefreshTokenResponse responseDto = new AccessRefreshTokenResponse();
        responseDto.setAccessToken(tokens.get("accessToken"));
        responseDto.setRefreshToken(tokens.get("refreshToken"));
        log.info("get tokens by facebook user");
        return responseDto;
    }

    @Override
    public void registrationValidation(BindingResult bindingResult, String email) {
        log.info("registration validation email: {}", email);
        boolean user = userRepo.existsUserByEmail(email);
        if (user) {
            log.info("user with email: {} is exist", email);
            bindingResult.addError(new FieldError("loginRequestDto", "user", "User with email "
                    + email + " is exist"
            ));
        }
        log.info("success registration validation");
    }

    @Override
    public void loginValidation(BindingResult bindingResult, LoginRequest loginRequest) {
        log.info("login validation email: {}, password: {}", loginRequest.getEmail(), loginRequest.getPassword());
        UserDetails user = loadUserByUsername(loginRequest.getEmail());
        if (user == null) {
            log.info("user is null");
            bindingResult.addError(new FieldError("loginRequestDto", "user", "Incorrect user login data"));
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                log.info("user invalid password");
                bindingResult.addError(new FieldError("loginRequestDto", "user", "Incorrect user login data"));
            }
        }
        log.info("success login validation");
    }

}
