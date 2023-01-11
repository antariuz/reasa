package avada.spacelab.reasa.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(code = 500, message = "Server Error - Internal Server Error")
})
public class UserController {
}
