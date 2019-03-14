package ch.uzh.ifi.seal.soprafs19.exceptions;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found!")
public class NotFoundException extends RuntimeException {
}