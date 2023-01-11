package avada.spacelab.reasa.dto.auth.response;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String accessToken;
    private String accessTokenType = "Bearer";
}
