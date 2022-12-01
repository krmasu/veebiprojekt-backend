package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.authentication.LoginRequestDto;
import ee.taltech.iti0302.webproject.dto.authentication.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.user.UserCreatedDto;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserControllerTest {
    @InjectMocks
    private AuthenticateUserController authenticateUserController;
    @Mock
    private AuthenticateUserService authenticateUserService;

    //MethodName_StateUnderTest_ExpectedBehavior

    @Test
    void RegisterUser_Valid_ReturnsResponseEntityWithHttpCreated() {
        // given
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                        .username("aadu")
                        .email("aadu@aadumail.ee")
                        .password("salajane")
                        .build();
        given(authenticateUserService.registerUser(registerUserDto)).willReturn(1);
        // when
        var result = authenticateUserController.registerUser(registerUserDto);
        //then
        ResponseEntity<Object> expected = new ResponseEntity<>(new UserCreatedDto("Registration successful", true), HttpStatus.CREATED);
        assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void LoginUser_Valid_ReturnsLoginResponse() {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("aadu")
                .password("salajane")
                .build();
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .authToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwidXNlcm5hbWUiOiJhYWR1IiwiaWF0IjoxNjY5OTA2OTk0LCJleHAiOjE2Njk5NTAxOTR9.UXN0f8S6Ta0ye6qIq_-gCxCCQrNMGh2ugpfxtQkJwwU")
                .email("aadu@aadumail.ee")
                .projects(List.of())
                .id(1)
                .build();
        given(authenticateUserService.loginUser(loginRequestDto)).willReturn(loginResponseDto);
        assertEquals(loginResponseDto, authenticateUserController.loginUser(loginRequestDto));
    }
}