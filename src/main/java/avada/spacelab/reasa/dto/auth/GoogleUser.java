package avada.spacelab.reasa.dto.auth;

import lombok.Data;

@Data
public class GoogleUser {
    private String email;
    private String email_verified;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
}
