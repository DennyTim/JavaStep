import auth.User;
import auth.UserData;
import contracts.DAO;
import model.bookings.controller.BookingsController;
import model.bookings.dao.BookingsDaoImpl;
import model.dto.Airport;
import model.dto.FlightOffer;
import model.flights.controller.FlightsController;
import model.onlineTable.controller.OnlineTableController;
import view.ConsoleView;

import java.util.List;


public class App {
    public static void main(String[] args) {
//        new OnlineTableController("KBP", "departures").printAirportTable();

        FlightsController fc = new FlightsController();

        List<Airport> oList = fc.getAirportByCityAndCountry("Kiev", "Ukraine");

        List<Airport> dList = fc.getAirportByCityAndCountry("paris", "France");

        String o = oList.get(0).getPlaceId();
        String d = dList.get(0).getPlaceId();


        fc.setOriginAirportCode(o);
        fc.setDestinationAirportCode(d);
        fc.setOutboundDate("2019-03-10");
        fc.setInboundDate("2019-03-20");
        fc.setIsTwoWayTrip(false);
        fc.setAdultsNumber("2");
        fc.setCabinClass("economy");
        fc.setDao();

//        fc.printAvialableFlights();

        User test = new User("Test", "Test", 33);
        UserData actualUser = new UserData(test, "test", "test");

        BookingsController bc = new BookingsController();
        bc.setBookingsData(actualUser);

        for (FlightOffer avialableFlight : fc.getAvialableFlights()) {
            bc.add(avialableFlight);
        }

        bc.delete(0);
        bc.delete(1);
        bc.delete(2);
        bc.delete(3);

        bc.displayBookedFlights();

    }
}
