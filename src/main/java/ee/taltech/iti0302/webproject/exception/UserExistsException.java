package ee.taltech.iti0302.webproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserExistsException extends RuntimeException {
    private final Reason reason;

    @Getter
    @AllArgsConstructor
    public enum Reason {
        USERNAME("Username"),
        EMAIL("Email"),
        ID("Id");
        private final String capitalized;
    }
}
