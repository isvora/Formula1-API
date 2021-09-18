package formula1.api.exceptions.constructor;

public class ConstructorNotFoundException extends RuntimeException {

    public ConstructorNotFoundException(Long id) {
        super("Could not find constructor with id " + id);
    }

    public ConstructorNotFoundException(String ref) {
        super("Could not find circuit constructor ref " + ref);
    }
}
