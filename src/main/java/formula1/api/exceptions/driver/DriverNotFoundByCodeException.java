package formula1.api.exceptions.driver;

public class DriverNotFoundByCodeException extends RuntimeException {
    public DriverNotFoundByCodeException(String code) {
        super("Driver not found by code " + code);
    }
}
