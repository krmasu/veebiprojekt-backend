package ee.taltech.iti0302.webproject.services;

import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.entities.AppUser;
import ee.taltech.iti0302.webproject.repositories.UserRepository;
import ee.taltech.iti0302.webproject.tools.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Transactional
@Service
public class RegisterUserService {
    private final UserRepository userRepository;

    public void registerUser(RegisterUserDto request) throws NoSuchAlgorithmException {
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        RandomString generator = new RandomString(32);
        String salt = generator.nextString();
        user.setPasswordSalt(salt);
        user.setPasswordHash(toHexString(hashPassword(request.getPassword())));
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

}
