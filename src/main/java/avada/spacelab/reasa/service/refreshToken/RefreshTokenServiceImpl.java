package avada.spacelab.reasa.service.refreshToken;

import avada.spacelab.reasa.config.security.jwt.JwtUtils;
import avada.spacelab.reasa.dto.auth.response.RefreshTokenResponse;
import avada.spacelab.reasa.model.RefreshToken;
import avada.spacelab.reasa.repo.RefreshTokenRepo;
import avada.spacelab.reasa.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService{
    @Value("${app.jwt.refreshToken.expired}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;

    public String createToken(String email) {
        log.info("create new refresh token for user email: {}", email);
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setBuyer(userRepo.findUserByEmail(email));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        log.info("create new refresh token for user");
        return refreshToken.getToken();
    }

    public RefreshTokenResponse createNewJwt(String token) {
        log.info("create access token from refresh token: {}", token);
        String userEmail = userRepo.findUserEmailByToken(token);

        String accessToken = jwtUtils.generateJwtToken(userEmail);
        RefreshTokenResponse responseDto = new RefreshTokenResponse();
        responseDto.setAccessToken(accessToken);
        log.info("success create access token");
        return responseDto;
    }

    public void validateToken(
            String token,
            BindingResult bindingResult
    ) {
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token);
        if (refreshToken == null){
            bindingResult.addError(new FieldError(
                    "refreshToken",
                    "refreshToken",
                    "There is no this refresh token"));
        } else if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(refreshToken);
            bindingResult.addError(new FieldError(
                    "refreshToken",
                    "refreshToken",
                    "Refresh token was expired please make a new login request"));
        }
    }
}
