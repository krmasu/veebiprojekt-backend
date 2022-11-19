package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.AuthenticateUserDto;
import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ApplicationException;
import ee.taltech.iti0302.webproject.exception.InvalidCredentialsException;
import ee.taltech.iti0302.webproject.exception.UserExistsException;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.service.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.service.mapper.UserMapper;
import ee.taltech.iti0302.webproject.tools.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticateUserService {
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    public void registerUser(RegisterUserDto request) {
        String requestUsername = request.getUsername().toLowerCase();
        String requestEmail = request.getEmail().toLowerCase();

        boolean usernameExists = userRepository.existsByUsername(requestUsername);
        boolean emailExists = userRepository.existsByEmail(requestEmail);
        if (usernameExists) {
            throw new UserExistsException(UserExistsException.Reason.USERNAME);
        }
        if (emailExists) {
            throw new UserExistsException(UserExistsException.Reason.EMAIL);
        }

        AppUser user = new AppUser();
        user.setUsername(requestUsername);
        user.setEmail(requestEmail);

        RandomString generator = new RandomString(32);
        String salt = generator.nextString();
        String password;

        try {
            password = toHexString(hashPassword(request.getPassword() + salt));
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException("Hash algorithm not found");
        }

        user.setPasswordSalt(salt);
        user.setPasswordHash(password);
        userRepository.save(user);
    }

    public byte[] hashPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 64) {
            hexString.insert(0, "0");
        }
        return hexString.toString();
    }

    public LoginUserDto loginUser(AuthenticateUserDto request) {
        Optional<AppUser> optionalUser = userRepository.findByUsernameIgnoreCase(request.getUsername());
        AppUser user = optionalUser.orElseThrow(() -> new InvalidCredentialsException(InvalidCredentialsException.Reason.USERNAME));

        String hashedPassword = user.getPasswordHash();
        String salt = user.getPasswordSalt();
        String hashedInput;

        try {
            hashedInput = toHexString(hashPassword(request.getPassword() + salt));
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException("Hash algorithm not found");
        }

        if (Objects.equals(hashedPassword, hashedInput)) {
            List<Project> projects = user.getProjects();
            List<ProjectDto> projectDtoList = projectMapper.toDtoList(projects);
            return userMapper.toDto(user, projectDtoList);
        } else {
            throw new InvalidCredentialsException(InvalidCredentialsException.Reason.PASSWORD);
        }
    }
}
