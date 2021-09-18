package formula1.api.exceptions.driver;

public class DriverNotFoundByNationalityException extends RuntimeException {
    public DriverNotFoundByNationalityException(String nationality) {
        super("Driver not found by nationality " + nationality);
    }
}
