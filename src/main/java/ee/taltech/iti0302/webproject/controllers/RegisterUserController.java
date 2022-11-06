package ee.taltech.iti0302.webproject.controllers;

import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.exceptions.HashAlgorithmNotFoundException;
import ee.taltech.iti0302.webproject.services.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class RegisterUserController {

    private final RegisterUserService registerUserService;

    @PostMapping("api/register")
    public void registerUser(@RequestBody RegisterUserDto request) throws HashAlgorithmNotFoundException {
        try {
            registerUserService.registerUser(request);
        } catch (NoSuchAlgorithmException e) {
            throw new HashAlgorithmNotFoundException();
        }
    }
}
