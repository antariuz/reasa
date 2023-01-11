package avada.spacelab.reasa.dto.auth.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @Email(message = "Must be valid")
    @NotBlank(message = "Must not be empty")
    private String email;
    @NotBlank(message = "Must not be empty")
    @Size(min = 8, max = 255, message = "Must be greater then 8 and less then 255 characters")
    private String password;
}
