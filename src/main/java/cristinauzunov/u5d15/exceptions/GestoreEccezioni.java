package cristinauzunov.u5d15.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GestoreEccezioni {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessaggioErrore gestisciNotFound(NotFoundException ex) {
        return new MessaggioErrore(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessaggioErrore gestisciBadRequest(BadRequestException ex) {
        return new MessaggioErrore(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessaggioErrore gestisciUnauthorized(UnauthorizedException ex) {
        return new MessaggioErrore(ex.getMessage(), LocalDateTime.now(), HttpStatus.FORBIDDEN.value());
    }
}