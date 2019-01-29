package view;

import apiData.OnlineTableApi;
import apiData.UserRequestInfo;
import auth.UserAuth;
import auth.UserData;
import model.bookings.controller.BookingsController;
import model.bookings.dao.BookingsDao;
import model.bookings.dao.BookingsDaoImpl;
import model.bookings.service.BookingsService;
import model.flights.controller.FlightsController;
import model.flights.dao.FlightsDaoImpl;
import model.flights.service.FlightsService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {
    private UserRequestInfo flightInfo;
    private OnlineTableApi onlineTableApi;
    private Scanner read = new Scanner(System.in);
    private ArrayList<Map<String, String>> originCityAirports;
    private ArrayList<Map<String, String>> destinationCityAirports;
    private static BookingsController bookingsController;
    private static FlightsController flightsController;

    public ConsoleView() {
        this.flightInfo = new UserRequestInfo();
        this.onlineTableApi = new OnlineTableApi();
    }

    public void printOnlineTableData(){
        originCoutry ();
        originCity();
        chooseOriginCityAirport();
        chooseDestinationDeparOrArriv();
        onlineTableApi.getData().printTableData();
    }

    private void getInfoWays() {
        originCoutry();
        originCity();
        chooseOriginCityFlightsInfo();
        destinationCoutry();
        destinationCity();
        chooseDestinationCityAirport();
    }
    private void getInfoDate() {
        outboundDate();
        inboundDate();
        cabineClass();
        adultsNumber();
    }

    private UserData userData(){
        return UserAuth.returnActualUser();
    }

    private BookingsController bookingsController(UserData actualUser) {
        BookingsDao bookingsDao = new BookingsDaoImpl(actualUser);
        BookingsService bookingService = new BookingsService(bookingsDao);
        return new BookingsController(bookingService);
    }

    private FlightsController fc() {
        FlightsDaoImpl db = new FlightsDaoImpl().requestApiData(flightInfo);
        FlightsService fs = new FlightsService(db);
        return new FlightsController(fs);
    }

    private int returnInput() {
        System.out.println();
        System.out.println("--------------------------------" );
        System.out.println();
        System.out.println("Enter flight");
        String index = read.nextLine();
        return Integer.parseInt(index)-1;
    }

    private void flightService() {
        getInfoWays();
        getInfoDate();
        flightsController = fc();
        flightsController.printFlights();
        bookingsController.add(flightsController.flightToBook(returnInput()));
    }

    public void mainMenu() {
        UserData actualUser = userData();
        bookingsController = bookingsController(actualUser);
        System.out.println("---------------------------------------------------------------");
        System.out.println();
        System.out.println(" Our service welcomes you! What do you want to see from list? ");
        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.println("1. List of flights");
        System.out.println("2. Flights today");
        System.out.println("3. My bookings");
        while(true) {
            String index = read.nextLine();
            switch (index) {
                case "1":
                    flightService();
                    break;
                case "2":
                    printOnlineTableData();
                    break;
                case "3":
                    bookingsController.displayBookedFlights();
                    break;
                case "exit":
                    System.exit(0);
            }
        }
    }

    private void originCoutry() {
        System.out.println("Enter origin country:");
        String originCountry = read.nextLine();
        flightInfo.setOriginCountry(originCountry);
    }

    private void destinationCoutry() {
        System.out.println("Enter destination country:");
        String destinationCountry = read.nextLine();
        flightInfo.setDestinationCountry(destinationCountry);
    }

    private void destinationCity() {
        System.out.println("Enter destination city:");
        String destinationCity = read.nextLine();
        flightInfo.setDestinationCity(destinationCity);
    }

    private void originCity() {
        System.out.println("Enter origin city:");
        String originCity = read.nextLine();
        flightInfo.setOriginCity(originCity);
    }

    private void chooseOriginCityAirport() {
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
        System.out.println("Choose origin city airport:");
        if ((originCityAirports.size() == 1)) {
            checkAiroportsTable(1, originCityAirports);
            getUnformatedAiroportsCity(1);
        } else {
            checkAiroportsTable(0, originCityAirports);
            getUnformatedAiroportsCity(0);
        }
    }

    private void chooseOriginCityFlightsInfo(){
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
        System.out.println("Choose origin city airport:");
        checkAiroports(originCityAirports);
        setChoosenOriginAirports(originCityAirports);
    }

    private void chooseDestinationCityAirport() {
        destinationCityAirports = flightInfo.getCityInfo(flightInfo.getDestinationCity(), flightInfo.getDestinationCountry());
        System.out.println("Choose destionation city airport:");
        checkAiroports(destinationCityAirports);
        setChoosenDestAirports(destinationCityAirports);
    }

    private void outboundDate(){
        System.out.println("Enter outbound date(yyyy-mm-dd):");
        String outboundDate = read.nextLine();
        flightInfo.setOutboundDate(outboundDate);
    }

    private void inboundDate(){
        System.out.println("Do you want to search two way flights?(yes/no)");
        String twoWayTrip = read.nextLine();

        if (twoWayTrip.equals("yes")) {
            System.out.println("Enter inbound date(yyyy-mm-dd):");
            String inboundDate = read.nextLine();
            flightInfo.setTwoWayTrip(true);
            flightInfo.setInboundDate(inboundDate);
        }
    }

    private void cabineClass(){
        System.out.println("Do you want to set cabin class?(yes/no)");
        String requireCabinClass = read.nextLine();
        if (requireCabinClass.equals("yes")) {
            System.out.println("Enter cabin class (economy, premiumeconomy, business, first):");
            String cabinClass = read.nextLine();
            flightInfo.setRequireCabinClass(true);
            flightInfo.setCabinClass(cabinClass);
        }
    }

    private void adultsNumber(){
        System.out.println("Enter number of adult passengers");
        String adultsNumber = read.nextLine();
        flightInfo.setAdultsNumber(adultsNumber);
    }

    private void chooseDestinationDeparOrArriv() {
        System.out.println("Choose destination (departures: d, arrivals: a):");
        String destination = read.nextLine();
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

    private void checkAiroports(ArrayList<Map<String, String>> cityAirports) {
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

    private void getUnformatedAiroportsCity(int index){
        String chosenOriginAirportIndex = read.nextLine();
        String unformattedAirportCode = getUnformattedAirportCode(chosenOriginAirportIndex, index);
        setOnlineTableApi(onlineTableApi, unformattedAirportCode);
    }

    private void setChoosenOriginAirports(ArrayList<Map<String, String>> cityAirports) {
        String chosenOriginAirportIndex = read.nextLine();
        flightInfo.setChosenOriginAirport(cityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - 1));
    }

    private void setChoosenDestAirports(ArrayList<Map<String, String>> cityAirports) {
        String chosenDestinationAirportIndex = read.nextLine();
        flightInfo.setChosenDestinationAirport(cityAirports.get(Integer.parseInt(chosenDestinationAirportIndex) - 1));
    }
}
