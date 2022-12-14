package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.ProjectMapperImpl;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapperImpl;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthenticateUserServiceTest {
    @InjectMocks
    private AuthenticateUserService authenticateUserService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ProjectMapper projectMapper = new ProjectMapperImpl();

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void registerUser_ValidData_Returns() {

    }

}