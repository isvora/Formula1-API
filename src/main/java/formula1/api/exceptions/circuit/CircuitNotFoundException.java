package formula1.api.exceptions.circuit;

public class CircuitNotFoundException extends RuntimeException {

    public CircuitNotFoundException(Long id) {
        super("Could not find circuit with id " + id);
    }

    public CircuitNotFoundException(String ref) {
        super("Could not find circuit with ref " + ref);
    }
}
