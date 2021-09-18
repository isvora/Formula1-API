package formula1.api.exceptions.driver;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(Long id) {
        super("Driver not found for id " + id);
    }

    public DriverNotFoundException(String ref) {
        super("Driver not found for ref " + ref);
    }
}
