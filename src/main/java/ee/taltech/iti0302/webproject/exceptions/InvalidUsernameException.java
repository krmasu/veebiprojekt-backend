package ee.taltech.iti0302.webproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect username")
public class InvalidUsernameException extends RuntimeException {

}
