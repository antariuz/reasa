package avada.spacelab.reasa.controller;

import avada.spacelab.reasa.service.user.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(code = 500, message = "Server Error - Internal Server Error")
})
public class UserController {
    private final UserService userService;

    @PostMapping("/isUserExistByEmail")
    public boolean isUserExistByEmail(String email) {
        //  fixme: regex check before
        return userService.isUserExistByEmail(email);
    }

}
