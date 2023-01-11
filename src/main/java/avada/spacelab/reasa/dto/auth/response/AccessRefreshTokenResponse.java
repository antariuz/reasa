package avada.spacelab.reasa.dto.auth.response;

import lombok.Data;

@Data
public class AccessRefreshTokenResponse {
    private String accessToken;
    private String accessTokenType = "Bearer";
    private String refreshToken;

}
