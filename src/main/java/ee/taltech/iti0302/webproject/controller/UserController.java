package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.user.UserRequestDto;
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
    public UserDto getUserData(@RequestBody UserRequestDto request) {
        return userService.getUserData(request);
    }
}
