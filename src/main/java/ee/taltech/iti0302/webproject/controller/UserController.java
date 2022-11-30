package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.authentication.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.UserRequestDto;
import ee.taltech.iti0302.webproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("api/user")
    public LoginUserDto getUserData(@RequestBody UserRequestDto request) {
        return userService.getUserData(request);
    }
}
