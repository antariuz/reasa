package avada.spacelab.reasa.service.refreshToken;

import avada.spacelab.reasa.dto.auth.response.RefreshTokenResponse;
import org.springframework.validation.BindingResult;

public interface RefreshTokenService {
    String createToken(String email);

    RefreshTokenResponse createNewJwt(String token);

    void validateToken(
            String token,
            BindingResult bindingResult
    );
}
