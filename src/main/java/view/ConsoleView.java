package view;


import auth.UserAuthService;
import auth.UserData;
import model.bookings.controller.BookingsController;
import model.dto.Airport;
import model.flights.controller.FlightsController;
import model.onlineTable.controller.OnlineTableController;

import java.util.List;

public class ConsoleView {

    private UserInput ui = new UserInput();
    private FlightsController fc = new FlightsController();
    private BookingsController bc = new BookingsController();

    private void showMainMenu(boolean user) {
        String menu = user ? " 1. Online-table\n 2. Flights search & booking\n 3. Cancel booking\n 4. My bookings\n 5. Log out\n 6. Exit" : " 1. Online-table\n 2. Flights search & booking\n 3. Exit";
        System.out.println("\n\n\n");
        System.out.println(menu);
    }

    private void setAirportCode(String type) {
        String country = ui.getCountry(type);
        String city = ui.getCity(type);
        List<Airport> airports = fc.getAirportByCityAndCountry(city, country);
        int chosenAirportIndex = ui.getAirportIndex(airports.size());

        if (type.equals("origin")) {
            fc.setOriginAirportCode(airports.get(chosenAirportIndex).getPlaceId());
        } else if (type.equals("destination")) {
            fc.setDestinationAirportCode(airports.get(chosenAirportIndex).getPlaceId());
        } else {
            throw new RuntimeException("Wrong type, must be origin or destination");
        }
    }

    private void bookFlight() {
        setAirportCode("origin");
        String outboundDate = ui.getDate("outbound");
        fc.setOutboundDate(outboundDate);
        String yesNoInput = ui.getYesOrNo("Do you want to search two way flights(yes/no)?");

        if (yesNoInput.equals("yes")) {
            setAirportCode("destination");
            String inboundDate = ui.getDate("inbound");
            fc.setInboundDate(inboundDate);
            fc.setIsTwoWayTrip(true);
        }

        String adultsNumber = ui.getAdultsNumber();
        fc.setAdultsNumber(adultsNumber);

        String cabinClassQuestion = ui.getYesOrNo("Do you want to set cabin class(yes/no)?");

        if (cabinClassQuestion.equals("yes")) {
            String cabinClass = ui.getCabinClass();
            fc.setCabinClass(cabinClass);
        }

        fc.setDao();
        fc.printAvialableFlights();
        int flightToBookIndex = ui.getFlightToBookIndex(fc.getAvialableFlights().size());
        bc.add(fc.getAvialableFlights().get(flightToBookIndex));
    }


    public void startApp() {

        UserData user = UserAuthService.returnActualUser();
        boolean userOrGuestChecker = user != null;
        if (userOrGuestChecker) bc.setBookingsData(user);


        showMainMenu(userOrGuestChecker);

        String menuIndex = ui.getMenuItemIndex(userOrGuestChecker);

        switch (menuIndex) {
            case "2":
                bookFlight();
        }

    }
}
