package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.UserCreatedDto;
import ee.taltech.iti0302.webproject.dto.UserDto;
import ee.taltech.iti0302.webproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("api/user")
    public LoginUserDto registerUser(@RequestBody UserDto request) {
        return userService.getUserData(request);
    }
}
