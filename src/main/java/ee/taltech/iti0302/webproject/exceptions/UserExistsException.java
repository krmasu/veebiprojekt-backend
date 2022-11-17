package ee.taltech.iti0302.webproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username exists")
public class UserExistsException extends RuntimeException {
}
