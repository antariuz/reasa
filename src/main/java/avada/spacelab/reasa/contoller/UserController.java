package avada.spacelab.reasa.contoller;

import avada.spacelab.reasa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("isUserExistByEmail")
    public boolean isUserExistByEmail(String email) {
        //  fixme: regex check before
        return userService.isUserExistByEmail(email);
    }

}
