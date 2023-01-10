package avada.spacelab.reasa.controller;

import avada.spacelab.reasa.dto.auth.request.IdTokenRequest;
import avada.spacelab.reasa.dto.auth.request.LoginRequest;
import avada.spacelab.reasa.dto.auth.request.RefreshTokenRequest;
import avada.spacelab.reasa.dto.auth.response.AccessRefreshTokenResponse;
import avada.spacelab.reasa.dto.auth.response.RefreshTokenResponse;
import avada.spacelab.reasa.model.User;
import avada.spacelab.reasa.service.refreshToken.RefreshTokenService;
import avada.spacelab.reasa.service.user.UserService;


import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

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
@Slf4j
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

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

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
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
