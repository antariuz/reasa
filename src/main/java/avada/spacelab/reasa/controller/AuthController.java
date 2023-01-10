package avada.spacelab.reasa.controller;

import avada.spacelab.reasa.dto.auth.request.IdTokenRequest;
import avada.spacelab.reasa.dto.auth.request.LoginRequest;
import avada.spacelab.reasa.dto.auth.request.RefreshTokenRequest;
import avada.spacelab.reasa.dto.auth.response.AccessRefreshTokenResponse;
import avada.spacelab.reasa.dto.auth.response.RefreshTokenResponse;
import avada.spacelab.reasa.model.User;
import avada.spacelab.reasa.service.refreshToken.RefreshTokenService;
import avada.spacelab.reasa.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(code = 500, message = "Server Error - Internal Server Error")
})
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @ApiOperation(value = "Login google user", notes = "Login user by google id_token (get access, refreshToken)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok - Successfully google login"),
            @ApiResponse(code = 400, message = "Bad Request - The fields are filled incorrectly"),
            @ApiResponse(code = 404, message = "Not Found - Google user was not found for this id_token"),
            @ApiResponse(code = 503, message = "Service Unavailable - Server error not valid request to google api")
    })
    @PostMapping("/login/google")
    public ResponseEntity<?> loginGoogle(
            @Valid @RequestBody IdTokenRequest idTokenRequest,
            BindingResult bindingResult
    ) {
        // validation
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // action
        return userService.getTokensByProvider(idTokenRequest, User.Provider.GOOGLE);
    }

    @ApiOperation(value = "Login facebook user", notes = "Login user by facebook access_token (get access, refreshToken)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok - Successfully facebook login"),
            @ApiResponse(code = 400, message = "Bad Request - The fields are filled incorrectly"),
            @ApiResponse(code = 404, message = "Not Found - Facebook user was not found for this access_token"),
            @ApiResponse(code = 503, message = "Service Unavailable - Server error not valid request to facebook api")
    })
    @PostMapping("/login/facebook")
    public ResponseEntity<?> loginFacebook(
            @Valid @RequestBody IdTokenRequest idTokenRequest,
            BindingResult bindingResult
    ) {
        // validation
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // action
        return userService.getTokensByProvider(idTokenRequest, User.Provider.FACEBOOK);
    }
    @ApiOperation(value = "Local registration", notes = "Registration by email, password (get access, refreshToken)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok - Successfully local registration"),
            @ApiResponse(code = 400, message = "Bad Request - The fields are filled incorrectly"),
    })
    @PostMapping("/registration")
    public ResponseEntity<?> registrationUser(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult
    ) {
        // validation
        if(!bindingResult.hasErrors()) {
            userService.registrationValidation(bindingResult, loginRequest.getEmail());
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // action
        AccessRefreshTokenResponse response = userService.localRegistration(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Local login", notes = "Login by email, password (get access, refreshToken)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok - Successfully local login"),
            @ApiResponse(code = 400, message = "Bad Request - The fields are filled incorrectly"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult
    ) {
        // validation
        if(!bindingResult.hasErrors()) {
            userService.loginValidation(bindingResult, loginRequest);
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // action
        AccessRefreshTokenResponse response = userService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Access Token", notes = "Get access token by refresh token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok - Successfully get access token"),
            @ApiResponse(code = 400, message = "Bad Request - The fields are filled incorrectly"),
    })
    @GetMapping("/accessToken")
    public ResponseEntity<?> accessToken(
            @Valid @RequestBody RefreshTokenRequest refreshToken,
            BindingResult bindingResult
    ) {
        //validation
        if(!bindingResult.hasErrors()) {
            refreshTokenService.validateToken(refreshToken.getToken(), bindingResult);
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //action
        RefreshTokenResponse responseDto = refreshTokenService.createNewJwt(refreshToken.getToken());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
