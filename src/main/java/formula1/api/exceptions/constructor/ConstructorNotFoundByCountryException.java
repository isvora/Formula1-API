package formula1.api.exceptions.constructor;

public class ConstructorNotFoundByCountryException extends RuntimeException {

    public ConstructorNotFoundByCountryException(String country) {
        super("Could not find circuit for country " + country);
    }
}
