package ee.taltech.iti0302.webproject.unit.exception;

import ee.taltech.iti0302.webproject.exception.ApplicationException;
import ee.taltech.iti0302.webproject.exception.ErrorResponse;
import ee.taltech.iti0302.webproject.exception.GlobalExceptionHandler;
import ee.taltech.iti0302.webproject.exception.InvalidCredentialsException;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.exception.UserExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleUserExists_UserExists_ReturnResponseEntity() {
        UserExistsException exception = new UserExistsException(UserExistsException.Reason.USERNAME);

        var result = globalExceptionHandler.handleUserExists(exception);

        assertEquals(new ErrorResponse("Username exists"), result.getBody());
    }

    @Test
    void handleInvalidCredentials_InvalidCredentials_ReturnResponseEntity() {
        InvalidCredentialsException exception = new InvalidCredentialsException(InvalidCredentialsException.Reason.USERNAME);

        var result = globalExceptionHandler.handleInvalidCredentials(exception);

        assertEquals(new ErrorResponse("Username invalid"), result.getBody());
    }

    @Test
    void handleResourceNotFound_InvalidCredentials_ReturnResponseEntity() {
        ResourceNotFoundException exception = new ResourceNotFoundException("not found");

        var result = globalExceptionHandler.handleResourceNotFound(exception);

        assertEquals(new ErrorResponse("not found"), result.getBody());
    }

    @Test
    void handleApplicationException_InvalidCredentials_ReturnResponseEntity() {
        ApplicationException exception = new ApplicationException("not found");

        var result = globalExceptionHandler.handleApplicationException(exception);

        assertEquals(new ErrorResponse("not found"), result.getBody());
    }

    @Test
    void handleException_InvalidCredentials_ReturnResponseEntity() {
        ApplicationException exception = new ApplicationException("Internal server error");

        var result = globalExceptionHandler.handleException(exception);

        assertEquals(new ErrorResponse("Internal server error"), result.getBody());
    }
}
