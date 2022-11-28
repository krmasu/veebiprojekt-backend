package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.authentication.LoginRequestDto;
import ee.taltech.iti0302.webproject.dto.authentication.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.InvalidCredentialsException;
import ee.taltech.iti0302.webproject.exception.UserExistsException;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticateUserService {
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final long TWELVE_HOURS_AS_MILLI = 43200000;

    public Integer registerUser(RegisterUserDto request) {
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
        AppUser user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        AppUser savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public LoginResponseDto loginUser(LoginRequestDto request) {
        Optional<AppUser> optionalUser = userRepository.findByUsernameIgnoreCase(request.getUsername());
        AppUser user = optionalUser.orElseThrow(() -> new InvalidCredentialsException(InvalidCredentialsException.Reason.USERNAME));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", user.getUsername());
            long issuedAt = System.currentTimeMillis();
            byte[] keyBites = Decoders.BASE64.decode("OERweXEyQ1B1cmJaNW9taklBR0xrSnVKMGFmbG9wTXNJUjBiQkZrdQ==");
            Key key = Keys.hmacShaKeyFor(keyBites);
            String authToken = Jwts.builder()
                    .setSubject(user.getId().toString())
                    .addClaims(claims)
                    .setIssuedAt(new Date(issuedAt))
                    .setExpiration(new Date(issuedAt + TWELVE_HOURS_AS_MILLI))
                    .signWith(key)
                    .compact();
            List<Project> projects = user.getProjects();
            List<ProjectDto> projectDtoList = projectMapper.toDtoList(projects);
            return userMapper.toLoginResponseDto(authToken, user.getEmail(), projectDtoList, user.getId());
        } else {
            throw new InvalidCredentialsException(InvalidCredentialsException.Reason.PASSWORD);
        }
    }
}
