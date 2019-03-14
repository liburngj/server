package ch.uzh.ifi.seal.soprafs19.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already taken!")
public class ExceptionLogin extends RuntimeException {
}
