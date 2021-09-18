package formula1.api.exceptions.circuit;

public class CircuitNotFoundForNationalityException extends RuntimeException {

    public CircuitNotFoundForNationalityException(String country) {
        super("Could not find circuits for nationality " + country);
    }
}
