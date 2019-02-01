package view;

import apiData.FlightsResponseInfo;
import apiData.OnlineTableApi;
import apiData.UserRequestInfo;
import auth.UserAuth;
import auth.UserData;
import exceptions.AirportsNotFoundException;
import exceptions.NoFlightsBooked;
import logging.Logger;
import model.bookings.controller.BookingsController;
import model.flights.controller.FlightsController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {
    private UserRequestInfo flightInfo = new UserRequestInfo();
    private OnlineTableApi onlineTableApi;
    private static Scanner read = new Scanner(System.in);
    private static ArrayList<Map<String, String>> originCityAirports;
    private static ArrayList<Map<String, String>> destinationCityAirports;
    private UserData actualUser;
    private boolean logged = false;
    private static BookingsController bookingsController = new BookingsController();

    public ConsoleView() {
        this.onlineTableApi = new OnlineTableApi();
    }


    public void backToMainMenu() {
        System.out.println("To show main menu type 0");
        String input = read.nextLine();
        while (!input.equals("0")) {
            System.out.println("To show main menu type 0");
            input = read.nextLine();
        }

        userInputController();
    }


    public void printOnlineTableData(){
        originCoutry ();
        originCity();
        chooseOriginCityAirport();
        chooseDestinationDeparOrArriv();
        onlineTableApi.getData().printTableData();

        Logger.info("ConsoleView: printed online table");

        backToMainMenu();
    }

    public boolean flightsService() {

        originCoutry();
        originCity();
        chooseOriginCityFlightsInfo();


        destinationCoutry();
        destinationCity();
        chooseDestinationCityAirport();

        outboundDate();
        inboundDate();
        cabineClass();
        adultsNumber();

        try {
            FlightsController fc = FlightsController.instance(flightInfo);
            fc.printFlights();
            bookingsController.add(fc.flightToBook(returnInput()));
        } catch (RuntimeException e) {
            FlightsResponseInfo.pb.stop();
            Logger.error("ConsoleView: no flights found");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("Nothing was found, check flight data and try again.");
            System.out.println();
            System.out.println();
            System.out.println();
            flightsService();
        } catch (NoFlightsBooked noFlightsBooked) {
            Logger.error("ConsoleView: user didn't book any flight by search");
            userInputController();
            return false;
        }

        return true;
    }

    private static int returnInput() throws NoFlightsBooked {
        System.out.println();
        System.out.println("--------------------------------" );
        System.out.println();
        System.out.println("Enter index of flight to book or type menu:");
        String index = read.nextLine();
        while (!index.matches("(\\d+)|(menu)")) {
            System.out.println("Input must be a valid index of flight or menu, try again:");
            index = read.nextLine();
        }
        if (index.equals("menu"))  throw new NoFlightsBooked("");
        return  Integer.parseInt(index)-1;
    }

    private void originCoutry() {
        System.out.println("Enter origin country:");
        String originCountry = read.nextLine();
        flightInfo.setOriginCountry(Validation.validateCountry(originCountry));
    }

    private void destinationCoutry() {
        System.out.println("Enter destination country:");
        String destinationCountry = read.nextLine();
        flightInfo.setDestinationCountry(Validation.validateCountry(destinationCountry));
    }

    private void destinationCity() {
        System.out.println("Enter destination city:");
        String destinationCity = read.nextLine();
        flightInfo.setDestinationCity(Validation.validateCity(destinationCity));
    }

    private void originCity() {
        System.out.println("Enter origin city:");
        String originCity = read.nextLine();
        flightInfo.setOriginCity(Validation.validateCity(originCity));
    }

    private void chooseOriginCityAirport() {
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
        System.out.println("Choose origin city airport:");
        if ((originCityAirports.size() == 1)) {
            checkAiroportsTable(1, originCityAirports);
            getUnformatedAiroportsCity(1, originCityAirports.size());
        } else if (originCityAirports.size() == 0) {
            try {
                throw new AirportsNotFoundException("No airports found, try again.");
            } catch (AirportsNotFoundException e) {
                System.out.println(e.getMessage());
                originCity();
                chooseOriginCityAirport();
            }
        } else {
            checkAiroportsTable(0, originCityAirports);
            getUnformatedAiroportsCity(0, originCityAirports.size() - 1);
        }
    }

    private void chooseOriginCityFlightsInfo(){
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());


        if (originCityAirports.size() == 0) try {
            throw new AirportsNotFoundException("No airports found, try again.");
        } catch (AirportsNotFoundException e) {
            System.out.println(e.getMessage());
            originCity();
            chooseOriginCityFlightsInfo();
            return;
        }
        System.out.println("Choose origin city airport:");
        checkAiroports(originCityAirports);
        setChoosenOriginAirports(originCityAirports);
    }

    private void chooseDestinationCityAirport() {
        destinationCityAirports = flightInfo.getCityInfo(flightInfo.getDestinationCity(), flightInfo.getDestinationCountry());


        if (destinationCityAirports.size() == 0) {
            try {
                throw new AirportsNotFoundException("Airports not found");
            } catch (AirportsNotFoundException e) {
                System.out.println(e.getMessage());
                destinationCity();
                chooseDestinationCityAirport();
                return;
            }
        }
        System.out.println("Choose destionation city airport:");
        checkAiroports(destinationCityAirports);
        setChoosenDestAirports(destinationCityAirports);
    }

    private void outboundDate(){
        System.out.println("Enter outbound date(yyyy-mm-dd):");
        String outboundDate = read.nextLine();
        flightInfo.setOutboundDate(Validation.validateDate(outboundDate));
    }

    private void inboundDate(){
        System.out.println("Do you want to search two way flights?(yes/no)");
        String twoWayTrip = Validation.validateYesNoInput(read.nextLine());

        if (twoWayTrip.equals("yes")) {
            System.out.println("Enter inbound date(yyyy-mm-dd):");
            String inboundDate = read.nextLine();
            flightInfo.setTwoWayTrip(true);
            flightInfo.setInboundDate(Validation.validateDate(inboundDate));
        }
    }

    private void cabineClass(){
        System.out.println("Do you want to set cabin class?(yes/no)");
        String requireCabinClass = Validation.validateYesNoInput(read.nextLine());
        if (requireCabinClass.equals("yes")) {
            System.out.println("Enter cabin class (economy, premiumeconomy, business, first):");
            String cabinClass = Validation.validateCabinClass(read.nextLine());
            flightInfo.setRequireCabinClass(true);
            flightInfo.setCabinClass(cabinClass);
        }
    }

    private void adultsNumber(){
        System.out.println("Enter number of adult passengers");
        String adultsNumber = read.nextLine();
        flightInfo.setAdultsNumber(Validation.validateAdultsNumber(adultsNumber));
    }

    private void chooseDestinationDeparOrArriv() {
        System.out.println("Choose destination (departures: d, arrivals: a):");
        String destination = Validation.validateDepartureOrArrivalInput(read.nextLine());
        String destinationFormat = destination.equals("a") ? "arrivals" : "departures";
        onlineTableApi.setDepartureOrArrival(destinationFormat);
    }

    // Доп методы

    private String getUnformattedAirportCode(String chosenOriginAirportIndex, int index) {
        return originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - index).get("PlaceId");
    }

    private void setOnlineTableApi(OnlineTableApi onlineTableApi, String unformattedAirportCode) {
        onlineTableApi.setAirportCode(unformattedAirportCode.substring(0, unformattedAirportCode.indexOf("-")));
    }

    private static void checkAiroports(ArrayList<Map<String, String>> cityAirports) {
        for (int i = 0; i < cityAirports.size(); i++) {
            if (i == 0 && cityAirports.size() > 1) {
                System.out.println((i + 1) + ". " + "Any airport");
                continue;
            }
            System.out.println((i + 1) + ". " + cityAirports.get(i).get("PlaceName"));
        }
    }

    private void checkAiroportsTable(int index, ArrayList<Map<String, String>> cityAirports){
        for (int i = index == 1 ? 0 : 1; i < cityAirports.size(); i++) {
            System.out.println((i + index) + ". " + cityAirports.get(i).get("PlaceName"));
        }
    }

    private void getUnformatedAiroportsCity(int index, int listSize){
        String chosenOriginAirportIndex = Validation.validateAirportIndex(read.nextLine(), listSize);
        String unformattedAirportCode = getUnformattedAirportCode(chosenOriginAirportIndex, index);
        setOnlineTableApi(onlineTableApi, unformattedAirportCode);
    }

    private void setChoosenOriginAirports(ArrayList<Map<String, String>> cityAirports) {
        String chosenOriginAirportIndex = Validation.validateAirportIndex(read.nextLine(), cityAirports.size());
        flightInfo.setChosenOriginAirport(cityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - 1));
    }

    private void setChoosenDestAirports(ArrayList<Map<String, String>> cityAirports) {
        String chosenDestinationAirportIndex = Validation.validateAirportIndex(read.nextLine(), cityAirports.size());
        flightInfo.setChosenDestinationAirport(cityAirports.get(Integer.parseInt(chosenDestinationAirportIndex) - 1));
    }


    private void showMainMenu() {
        String menu = " 1. Online-table\n 2. Flights search & booking\n 3. Cancel booking\n 4. My bookings\n 5. Log out\n 6. Exit";
        System.out.println("\n\n\n");
        System.out.println(menu);
    }

    private void deleteBookedFlight(String index) {
        int flightsListSize = bookingsController.getAll().size();
        int validatedIndex = Integer.parseInt(Validation.validateBookedFlightIndex(index, flightsListSize)) - 1;
        bookingsController.delete(validatedIndex);
    }


    public void userInputController() {


        if (!logged) {
            actualUser = UserAuth.returnActualUser();
            logged = true;
            bookingsController.setBookingsData(actualUser);
        }

        showMainMenu();

        System.out.println("\n\nEnter index of menu item:");

        String input = Validation.validateMenuItemInput(read.nextLine());




        switch (input) {
            case "1":
                printOnlineTableData();
                break;
            case "2":
                boolean checkMenuExit = flightsService();
                if (checkMenuExit) userInputController();
                break;
            case "3":
                if (bookingsController.getAll().size() > 0) {
                    bookingsController.displayBookedFlights();
                    System.out.println("Do you want to cancel booking (yes/no):");
                    input = Validation.validateYesNoInput(read.nextLine());

                    while (!input.equals("no")) {
                        System.out.println("Choose index of booking to cancel:");
                        input = read.nextLine();
                        deleteBookedFlight(input);
                        System.out.println("\n\n");
                        if (bookingsController.getAll().size() == 0) {
                            input = "no";
                            continue;
                        }
                        bookingsController.displayBookedFlights();
                        System.out.println("Do you want to cancel booking (yes/no):");
                        input = Validation.validateYesNoInput(read.nextLine());
                    }

                    backToMainMenu();

                } else {
                    System.out.println("There are no bookings to cancel.");
                    backToMainMenu();
                }
                break;
            case "4":
                if (bookingsController.getAll().size() > 0) {
                    bookingsController.displayBookedFlights();
                    backToMainMenu();
                } else {
                    System.out.println("Currently you don't have bookings.");
                    backToMainMenu();
                }
                break;
            case "5":
                Logger.info("ConsoleView: log out");
                actualUser = UserAuth.returnActualUser();
                bookingsController.setBookingsData(actualUser);
                userInputController();
                break;
            case "6":
                Logger.info("ConsoleView: exit");
                System.out.println("Good bye!");
                break;
        }
    }
}
