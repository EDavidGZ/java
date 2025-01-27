package com.edgz.authudemy.controllers;


import com.edgz.authudemy.models.PasswordRecovery;
import com.edgz.authudemy.models.User;
import com.edgz.authudemy.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api" )
public class AuthController {

    @Autowired
    public AuthService authService;

    record RegisterRequest(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, String email, String password, @JsonProperty("password_confirm") String passwordConfirm){}
    record RegisterResponse(Long id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, String email){}

    @PostMapping(value = "/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
        var user = authService.register(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                registerRequest.password(),
                registerRequest.passwordConfirm()
        );
        return new RegisterResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail() );
    }

    record LoginRequest(String email, String password){}
    record LoginResponse(String token){}

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        var login = authService.login(loginRequest.email(), loginRequest.password());

        Cookie cookie = new Cookie("refresh_token", login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");

        response.addCookie(cookie);

        return new LoginResponse(login.getAccessToken().getToken());
    }

    record UserResponse(Long id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, String email){}

    @GetMapping(value = "/user")
    public UserResponse user(HttpServletRequest request) {
        var user = (User) request.getAttribute("user");

        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    record RefreshResponse(String token) {}

    @PostMapping(value = "/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token") String refreshToken) {
        return new RefreshResponse(authService.refreshAccess(refreshToken).getAccessToken().getToken());
    }

    record LogoutResponse(String message) {}

    @PostMapping(value = "/logout")
    public LogoutResponse logout(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        authService.logout(refreshToken);

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new LogoutResponse("success");
    }

    record ForgotRequest(String email) {}
    record ForgotResponse(String message) {}

    @PostMapping(value = "/forgot")
    public ForgotResponse forgot(@RequestBody ForgotRequest forgotRequest, HttpServletRequest request) {
        var originUrl = request.getHeader("Origin");

        authService.forgot(forgotRequest.email(), originUrl);

        return new ForgotResponse("success");
    }

    record ResetRequest(String token, String password, @JsonProperty(value = "password_confirm") String passwordConfirm) {}
    record ResetResponse(String message) {}

    @PostMapping(value = "/reset")
    public ResetResponse reset(@RequestBody ResetRequest resetRequest) {
        authService.reset(resetRequest.token(), resetRequest.password, resetRequest.passwordConfirm());

        return new ResetResponse("success");
    }

    @GetMapping(value = "/alls")
    public List<PasswordRecovery> alls(){
       return authService.getAlls();

    }

    @DeleteMapping(value = "/alls")
    public ResponseEntity<Void> deleteAlls(){
        authService.deleteAlls();
        return ResponseEntity.noContent().build();
    }
}
