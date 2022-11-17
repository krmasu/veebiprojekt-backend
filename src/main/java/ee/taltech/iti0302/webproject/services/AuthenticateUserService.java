package ee.taltech.iti0302.webproject.services;

import ee.taltech.iti0302.webproject.dto.AuthenticateUserDto;
import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.entities.AppUser;
import ee.taltech.iti0302.webproject.entities.Project;
import ee.taltech.iti0302.webproject.exceptions.InvalidPasswordException;
import ee.taltech.iti0302.webproject.exceptions.InvalidUsernameException;
import ee.taltech.iti0302.webproject.exceptions.UserExistsException;
import ee.taltech.iti0302.webproject.repositories.UserRepository;
import ee.taltech.iti0302.webproject.tools.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticateUserService {
    private final UserRepository userRepository;

    public void registerUser(RegisterUserDto request) throws NoSuchAlgorithmException {
        String requestUsername = request.getUsername().toLowerCase();
        boolean usernameExists = userRepository.existsByUsername(requestUsername);
        if (usernameExists) {
            throw new UserExistsException();
        }
        AppUser user = new AppUser();
        user.setUsername(requestUsername);
        user.setEmail(request.getEmail().toLowerCase());

        RandomString generator = new RandomString(32);
        String salt = generator.nextString();
        String password = toHexString(hashPassword(request.getPassword() + salt));

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

    public LoginUserDto loginUser(AuthenticateUserDto request) throws NoSuchAlgorithmException, InvalidUsernameException {
        AppUser user = userRepository.findByUsernameIgnoreCase(request.getUsername());
        if (user == null) {
            throw new InvalidUsernameException();
        }
        String hashedPassword = user.getPasswordHash();
        String salt = user.getPasswordSalt();
        String hashedInput = toHexString(hashPassword(request.getPassword() + salt));

        if (Objects.equals(hashedPassword, hashedInput)) {
            LoginUserDto loginUserDto = new LoginUserDto();
            loginUserDto.setId(user.getId());
            loginUserDto.setUsername(user.getUsername());
            loginUserDto.setEmail(user.getEmail());
            List<Project> projects = user.getProjects();
            Map<Integer, String> projectMap = new HashMap<>();
            for (Project project : projects) {
                projectMap.put(project.getId(), project.getTitle());
            }
            loginUserDto.setProjects(projectMap);
            return loginUserDto;
        } else {
            throw new InvalidPasswordException();
        }
    }
}
