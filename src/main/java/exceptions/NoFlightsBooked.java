package exceptions;

public class NoFlightsBooked extends RuntimeException {
    public NoFlightsBooked() {
        super("NoFlightsBooked");
    }
}
