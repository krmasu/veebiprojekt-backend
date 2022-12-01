package ee.taltech.iti0302.webproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidCredentialsException extends RuntimeException {
    private final Reason reason;

    @Getter
    @AllArgsConstructor
    public enum Reason {
        USERNAME("Username"),
        PASSWORD("Password"),
        ID("Id");

        private final String capitalized;
    }
}
