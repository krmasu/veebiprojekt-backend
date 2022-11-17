package ee.taltech.iti0302.webproject.controllers;

import ee.taltech.iti0302.webproject.dto.AuthenticateUserDto;
import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.exceptions.HashAlgorithmNotFoundException;
import ee.taltech.iti0302.webproject.services.AuthenticateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class AuthenticateUserController {

    private final AuthenticateUserService authenticateUserService;

    @PostMapping("api/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDto request) throws HashAlgorithmNotFoundException {
        try {
            authenticateUserService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NoSuchAlgorithmException e) {
            throw new HashAlgorithmNotFoundException();
        }
    }

    @GetMapping("api/login")
    public LoginUserDto loginUser(@RequestBody AuthenticateUserDto request) {
        try {
            return authenticateUserService.loginUser(request);
        } catch (NoSuchAlgorithmException e) {
            throw new HashAlgorithmNotFoundException();
        }
    }


}
