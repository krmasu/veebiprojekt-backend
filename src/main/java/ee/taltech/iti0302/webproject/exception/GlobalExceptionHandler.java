package ee.taltech.iti0302.webproject.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExists(UserExistsException exception) {
        log.error("User already exists. Reason: " + exception.getReason(), exception);
        return new ResponseEntity<>(new ErrorResponse(exception.getReason().getCapitalized() + " exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException exception) {
        log.error("Invalid credentials. Reason: " + exception.getReason(), exception);
        return new ResponseEntity<>(new ErrorResponse(exception.getReason().getCapitalized() + " invalid"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        log.error("Internal server error.", exception);
        return new ResponseEntity<>(new ErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
