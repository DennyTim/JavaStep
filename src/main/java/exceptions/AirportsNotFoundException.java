package exceptions;

public class AirportsNotFoundException extends RuntimeException {
    public AirportsNotFoundException() {
        super("No airports found");
    }
}
