package avada.spacelab.reasa.service.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    
    boolean isUserExistByEmail(String email);

    public UserDetails loadUserByUsername(String email);
}
