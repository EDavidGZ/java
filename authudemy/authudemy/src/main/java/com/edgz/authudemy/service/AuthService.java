package com.edgz.authudemy.service;

import com.edgz.authudemy.error.*;
import com.edgz.authudemy.models.PasswordRecovery;
import com.edgz.authudemy.models.Tokens;
import com.edgz.authudemy.models.User;
import com.edgz.authudemy.repos.IPasswordRecovery;
import com.edgz.authudemy.repos.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class AuthService {
    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IPasswordRecovery passwordRecovery;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MailService mailService;
    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(
            MailService mailService, @Value("${application.security.access-token-secret}") String accessTokenSecret,
            @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.mailService = mailService;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }


    public User register(String firstName, String lastName, String email, String password, String passwordConfirm) {
        if (!Objects.equals(password, passwordConfirm))
            throw new PasswordsDontMatchError();

        User user;
        try {
            user = userRepo.save(User.of(firstName, lastName, email, passwordEncoder.encode(password) ));
        } catch (DbActionExecutionException exception){
        throw new EmailAlreadyExistsError();
    }
        return user;
    }

    public Login login(String email, String password) {
        // find user by email
        var user = userRepo.findByEmail(email)
                .orElseThrow(InvalidCredentialsError::new);

        //see if the passwords match
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidCredentialsError();

        var login =  Login.of(user.getId(), accessTokenSecret, refreshTokenSecret);
        var refreshJwt = login.getRefreshToken();

        // Agregar el nuevo token al usuario
        user.addToken(new Tokens(refreshJwt.getToken(), refreshJwt.getIssuedAt(), refreshJwt.getExpiration()));
        userRepo.save(user);  // Guardar el usuario con el nuevo token

        return login;
    }

    public User getUserFromToken(String token) {
            Long userId = Jwt.from(token, accessTokenSecret).getUserId(); // Obtener el ID de usuario desde el token
            log.info("Extracted user ID from token: " + userId);
            return userRepo.findById(userId)
                    .orElseThrow(() -> new UserNotFoundError());
    }

    public Login refreshAccess(String refreshToken) {
        var refreshJwt = Jwt.from(refreshToken, refreshTokenSecret);

        var user = userRepo.findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(refreshJwt.getUserId(), refreshJwt.getToken(), refreshJwt.getExpiration())
                .orElseThrow(UnauthenticatedError::new);

        return Login.of(refreshJwt.getUserId(), accessTokenSecret, refreshJwt);
    }

    public Boolean logout(String refreshToken) {
        var refreshJwt = Jwt.from(refreshToken, refreshTokenSecret);

        var user = userRepo.findById(refreshJwt.getUserId())
                .orElseThrow(UnauthenticatedError::new);

        var tokenIsRemoved = user.removeTokenIf(token -> Objects.equals(token.getRefreshToken(), refreshToken));

        if (tokenIsRemoved)
            userRepo.save(user);

        return tokenIsRemoved;
    }

    public void forgot(String email, String originUrl) {
        var token = UUID.randomUUID().toString().replace("-", "");
        var user = userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundError::new);

        PasswordRecovery passwordRecovery = new PasswordRecovery(token);
        user.addPasswordRecovery(passwordRecovery);

        mailService.sendForgotMessage(email, token, originUrl);

        userRepo.save(user);
    }

    public void reset(String token, String password, String passwordConfirm) {
        if (!Objects.equals(password, passwordConfirm))
            throw new PasswordsDontMatchError();

        var user = userRepo.findByPasswordRecoveriesToken(token)
                .orElseThrow(InvalidLinkError::new);

        user.setPassword(passwordEncoder.encode(password));
        user.removePasswordRecoveryIf(passwordRecovery -> Objects.equals(passwordRecovery.getToken(), token));

        userRepo.save(user);
    }

    public List<PasswordRecovery> getAlls(){
        return passwordRecovery.findAll();
    }

    public void deleteAlls(){
        passwordRecovery.deleteAll();
    }

}
