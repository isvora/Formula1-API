package formula1.api.exceptions;

public class LapTimeNotFoundException extends RuntimeException {
    public LapTimeNotFoundException(String msg) {
        super(msg);
    }
}
