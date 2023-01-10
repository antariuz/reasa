package avada.spacelab.reasa.service.user;

import avada.spacelab.reasa.dto.auth.request.IdTokenRequest;
import avada.spacelab.reasa.dto.auth.request.LoginRequest;
import avada.spacelab.reasa.dto.auth.response.AccessRefreshTokenResponse;
import avada.spacelab.reasa.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserService {
    
    boolean isUserExistByEmail(String email);

    User loadUserByUsername(String email);

    AccessRefreshTokenResponse login(LoginRequest loginRequest);

    AccessRefreshTokenResponse localRegistration(LoginRequest registrationRequest);

    void registration(String email, String name, User.Provider provider);

    void update(String email, String name, User.Provider provider);

    ResponseEntity<?> getTokensByProvider(IdTokenRequest idTokenRequest, User.Provider provider);

    void loginValidation(BindingResult bindingResult, LoginRequest loginRequest);

    void registrationValidation(BindingResult bindingResult, String email);


}
