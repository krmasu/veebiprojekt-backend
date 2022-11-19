package ee.taltech.iti0302.webproject.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String message;

    public ApplicationException(String message) {
        this.message = message;
    }
}
