package exceptions;

public class FlightsNotFoundException extends RuntimeException {

    public FlightsNotFoundException() {
        super("No flights found");
    }
}
