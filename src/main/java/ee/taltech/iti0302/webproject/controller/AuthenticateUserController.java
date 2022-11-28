package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.LoginRequestDto;
import ee.taltech.iti0302.webproject.dto.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.UserCreatedDto;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticateUserController {

    private final AuthenticateUserService authenticateUserService;
    private static final Logger log = LoggerFactory.getLogger(AuthenticateUserController.class);

    @PostMapping("api/public/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDto request) {
        log.info("Registering new user with name: {}", request.getUsername());
        Integer userId = authenticateUserService.registerUser(request);
        log.info("Registration successful for user with id: {}", userId);
        return new ResponseEntity<>(new UserCreatedDto("Registration successful", true), HttpStatus.CREATED);
    }

    @PostMapping("api/public/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto request) {
        log.info("Authenticating user with username: {}", request.getUsername());
        return authenticateUserService.loginUser(request);
    }


}
