package view;

import apiData.OnlineTableApi;
import apiData.UserRequestInfo;
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
    private ArrayList<Map<String, String>> cityAirports;

    public ConsoleView() {
        this.flightInfo = new UserRequestInfo();
        this.onlineTableApi = new OnlineTableApi();
    }

    public void printData(){
        this.originCoutry ();
        this.originCity();
        this.chooseOriginCityAirport();
        this.chooseDestinationDeparOrArriv();
        this.onlineTableApi.getData().printTableData();
    }

    public void flightsService(){
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
        FlightsDaoImpl db = new FlightsDaoImpl().requestApiData(flightInfo);
        FlightsService fs = new FlightsService(db);
        FlightsController fc = new FlightsController(fs);
        fc.printFlights();
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
            checkAiroportsCity(1);
        } else {
            checkAiroportsCity(0);
        }
    }

    private void chooseOriginCityFlightsInfo(){
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
        System.out.println("Choose origin city airport:");
        if ((originCityAirports.size() > 1)) {
            checkOriginAiroports(1);
        } else {
            checkOriginAiroports(0);
        }
    }

    private void chooseDestinationCityAirport() {
        destinationCityAirports = flightInfo.getCityInfo(flightInfo.getDestinationCity(), flightInfo.getDestinationCountry());
        System.out.println("Choose destionation city airport:");
        if ((originCityAirports.size() > 1)) {
            checkDestinationAiroports(1);
        } else {
            checkDestinationAiroports(0);
        }
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

    private void checkAiroportsCity(int index){
        for (int i = index == 1 ? 0 : 1; i < originCityAirports.size(); i++) {
            System.out.println((i + index) + ". " + originCityAirports.get(i).get("PlaceName"));
        }
        String chosenOriginAirportIndex = read.nextLine();
        String unformattedAirportCode = getUnformattedAirportCode(chosenOriginAirportIndex, index);
        setOnlineTableApi(this.onlineTableApi, unformattedAirportCode);
    }

    private void checkOriginAiroports(int index) {
        for (int i = index == 1 ? 0 : 1; i < originCityAirports.size(); i++) {
            if (i == 0) {
                System.out.println((i + 1) + ". " + "Any airport");
                continue;
            }
            System.out.println((i + 1) + ". " + originCityAirports.get(i).get("PlaceName"));
        }
        String chosenOriginAirportIndex = read.nextLine();
        flightInfo.setChosenOriginAirport(originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - index));
    }

    private void checkDestinationAiroports(int index) {
        for (int i = index == 1 ? 0 : 1; i < destinationCityAirports.size(); i++) {
            if (i == 0) {
                System.out.println((i + 1) + ". " + "Any airport");
                continue;
            }
            System.out.println((i + 1) + ". " + destinationCityAirports.get(i).get("PlaceName"));
        }
        String chosenDestinationAirportIndex = read.nextLine();
        flightInfo.setChosenDestinationAirport(destinationCityAirports.get(Integer.parseInt(chosenDestinationAirportIndex) - 1));
    }
}
