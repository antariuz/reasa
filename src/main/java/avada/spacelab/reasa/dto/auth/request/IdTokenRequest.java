package avada.spacelab.reasa.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdTokenRequest {
    @NotBlank(message = "Must not be empty")
    public String idToken;
}
