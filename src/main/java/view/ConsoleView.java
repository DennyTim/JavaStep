package view;


import auth.UserAuthService;
import auth.UserData;
import exceptions.AirportsNotFoundException;
import exceptions.FlightsNotFoundException;
import logging.Logger;
import model.bookings.controller.BookingsController;
import model.dto.Airport;
import model.flights.controller.FlightsController;
import model.onlineTable.controller.OnlineTableController;

import java.util.List;

public class ConsoleView {

    private UserInput ui = new UserInput();
    private FlightsController fc = new FlightsController();
    private BookingsController bc = new BookingsController();
    private boolean isLogged = false;
    private boolean isGuest = false;


    private void showMainMenu(boolean isUser) {
        String menu = isUser ? " 1. Online-table\n 2. Flights search & booking\n 3. Cancel booking\n 4. My bookings\n 5. Log out\n 6. Exit" : " 1. Online-table\n 2. Flights search\n 3. Exit";
        System.out.println("\n\n\n");
        System.out.println(menu);
    }

    private void backToMainMenu(boolean isImmediate) {
        if (!isImmediate) ui.get0();
        startApp();
    }

    private void setAirportCode(String type) {
        String country = ui.getCountry(type);
        String city = ui.getCity(type);
        List<Airport> airports = fc.getAirportByCityAndCountry(city, country);
        fc.printAirports(airports, true);
        int chosenAirportIndex = ui.getAirportIndex(airports.size());

        if (type.equals("origin")) {
            fc.setOriginAirportCode(airports.get(chosenAirportIndex).getPlaceId());
        } else if (type.equals("destination")) {
            fc.setDestinationAirportCode(airports.get(chosenAirportIndex).getPlaceId());
        }
    }

    private void bookFlight(boolean isUser) {
        setAirportCode("origin");
        setAirportCode("destination");
        String outboundDate = ui.getDate("outbound");
        fc.setOutboundDate(outboundDate);
        String yesNoInput = ui.getYesOrNo("Do you want to search two way flights(yes/no)?");

        if (yesNoInput.equals("yes")) {
            String inboundDate = ui.getDate("inbound");
            fc.setInboundDate(inboundDate);
            fc.setIsTwoWayTrip(true);
        } else {
            fc.setIsTwoWayTrip(false);
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
        if (isUser) {
            int flightToBookIndex = ui.getFlightToBookIndex(fc.getAvailableFlights().size());
            if (flightToBookIndex != -1) bc.add(fc.getAvailableFlights().get(flightToBookIndex));
            Logger.info("ConsoleView: FlightOffer booked");
        }
    }

    private void showOnlineTable() {
        String country = ui.getCountry("");
        String city = ui.getCity("");
        List<Airport> airports = fc.getAirportByCityAndCountry(city, country);
        airports.remove(0);
        fc.printAirports(airports, false);
        int airportIndex = ui.getAirportIndex(airports.size());
        String unformattedAirportCode = airports.get(airportIndex).getPlaceId();
        String formattedAirportCode = unformattedAirportCode.substring(0, unformattedAirportCode.indexOf("-"));
        String departureOrArrival = ui.getDepartureOrArrival();
        new OnlineTableController(formattedAirportCode, departureOrArrival).printAirportTable();
    }


    private void switchThroughMenuByIndex(String menuIndex, boolean isUser) {

        if (isUser) {
            switch (menuIndex) {
                case "1":
                    showOnlineTable();
                    backToMainMenu(false);
                    break;
                case "2":
                    bookFlight(true);
                    backToMainMenu(true);
                    break;
                case "3":
                    bc.displayBookedFlights();
                    int indexOfBookedFlightToCancel = ui.getBookedFlightIndex(bc.getAll().size());
                    if (indexOfBookedFlightToCancel != -1) bc.delete(indexOfBookedFlightToCancel);
                    backToMainMenu(true);
                    break;
                case "4":
                    bc.displayBookedFlights();
                    backToMainMenu(false);
                    break;
                case "5":
                    isLogged = false;
                    backToMainMenu(true);
                    break;
            }
        } else {



            switch (menuIndex) {
                case "1":
                    showOnlineTable();
                    backToMainMenu(false);
                    break;
                case "2":
                    bookFlight(false);
                    backToMainMenu(false);
                    break;
            }
        }
    }


    public void startApp() {

        UserData user;
        boolean isUser = isLogged;

        if (!isLogged) {
            user = isGuest ? null : UserAuthService.returnActualUser();
            isUser = user != null;

            if (isUser) {
                bc.setBookingsData(user);
                isLogged = true;
                isGuest = false;
            } else {
                isGuest = true;
            }
        }


        showMainMenu(isUser);

        String menuIndex = ui.getMenuItemIndex(isUser);

        try {

            switchThroughMenuByIndex(menuIndex, isLogged);


        } catch (AirportsNotFoundException e) {
            System.out.println("No airports found.");
            backToMainMenu(false);
            Logger.error(e.getMessage());
        } catch (FlightsNotFoundException e) {
            System.out.println("No flights found.");
            backToMainMenu(false);
            Logger.error(e.getMessage());
        }

    }
}
