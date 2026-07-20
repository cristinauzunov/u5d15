package cristinauzunov.u5d15.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String messaggio) {
        super(messaggio);
    }
}