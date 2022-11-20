package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.AuthenticateUserDto;
import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.UserCreatedDto;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticateUserController {

    private final AuthenticateUserService authenticateUserService;

    @PostMapping("api/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDto request) {
        authenticateUserService.registerUser(request);
        return new ResponseEntity<>(new UserCreatedDto("Registration successful", true), HttpStatus.CREATED);
    }

    @PostMapping("api/login")
    public LoginUserDto loginUser(@RequestBody AuthenticateUserDto request) {
        return authenticateUserService.loginUser(request);
    }


}
