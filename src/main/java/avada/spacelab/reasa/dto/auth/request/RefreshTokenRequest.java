package avada.spacelab.reasa.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Must not be empty")
    @Size(min = 1, max = 255, message = "Must be greater then 0 and less then 255 characters")
    private String token;
}
