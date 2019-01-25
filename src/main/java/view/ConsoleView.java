package view;

import apiData.OnlineTableApi;
import apiData.UserRequestInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {
    private UserRequestInfo flightInfo;
    private OnlineTableApi onlineTableApi;
    private Scanner read = new Scanner(System.in);
    private ArrayList<Map<String, String>> originCityAirports;

    public ConsoleView() {
        this.flightInfo = new UserRequestInfo();
        this.onlineTableApi = new OnlineTableApi();
    }

    private void originCoutry() {
        System.out.println("Enter origin country:");
        String originCountry = read.nextLine();
        flightInfo.setOriginCountry(originCountry);
    }

    private void originCity() {
        System.out.println("Enter origin city:");
        String originCity = read.nextLine();
        flightInfo.setOriginCity(originCity);
    }

    private void chooseOriginCity() {
        originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
        System.out.println("Choose origin city airport:");
        if (originCityAirports.size() == 1) {
            for (int i = 0; i < originCityAirports.size(); i++) {
                System.out.println((i + 1) + ". " + originCityAirports.get(i).get("PlaceName"));
            }
            String chosenOriginAirportIndex = read.nextLine();
            String unformattedAirportCode = getUnformattedAirportCode(chosenOriginAirportIndex, 1);
            setOnlineTableApi(this.onlineTableApi, unformattedAirportCode);
        } else {
            for (int i = 1; i < originCityAirports.size(); i++) {
                System.out.println((i) + ". " + originCityAirports.get(i).get("PlaceName"));
            }
            String chosenOriginAirportIndex = read.nextLine();
            String unformattedAirportCode = getUnformattedAirportCode(chosenOriginAirportIndex, 0);
            setOnlineTableApi(this.onlineTableApi, unformattedAirportCode);
        }
    }

    private void chooseDestinationDeparOrArriv() {
        System.out.println("Choose destination (departures: d, arrivals: a):");
        String destination = read.nextLine();
        String destinationFormat = destination.equals("a") ? "arrivals" : "departures";
        onlineTableApi.setDepartureOrArrival(destinationFormat);
    }

    public void printData(){
        this.originCoutry ();
        this.originCity();
        this.chooseOriginCity();
        this.chooseDestinationDeparOrArriv();
        this.onlineTableApi.getData().printTableData();
    }

    // Доп методы

    private String getUnformattedAirportCode(String chosenOriginAirportIndex, int index) {
        return originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - index).get("PlaceId");
    }

    private void setOnlineTableApi(OnlineTableApi onlineTableApi, String unformattedAirportCode) {
        onlineTableApi.setAirportCode(unformattedAirportCode.substring(0, unformattedAirportCode.indexOf("-")));
    }

//    private void checkAiroportsCity(){
//
//    }
}
