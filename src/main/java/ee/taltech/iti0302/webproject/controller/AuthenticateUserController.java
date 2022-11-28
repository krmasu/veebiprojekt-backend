package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.authentication.LoginRequestDto;
import ee.taltech.iti0302.webproject.dto.authentication.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.UserCreatedDto;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticateUserController {

    private final AuthenticateUserService authenticateUserService;

    @PostMapping("api/public/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDto request) {
        authenticateUserService.registerUser(request);
        return new ResponseEntity<>(new UserCreatedDto("Registration successful", true), HttpStatus.CREATED);
    }

    @PostMapping("api/public/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto request) {
        return authenticateUserService.loginUser(request);
    }


}
