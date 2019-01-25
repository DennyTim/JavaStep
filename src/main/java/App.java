import apiData.*;
import model.flights.controller.FlightsController;
import model.flights.dao.FlightsDaoImpl;
import model.flights.service.FlightsService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Online table

        /*Scanner read = new Scanner(System.in);

        UserRequestInfo flightInfo = new UserRequestInfo();

        OnlineTableApi table = new OnlineTableApi();

            System.out.println("Enter origin country:");
            String originCountry = read.nextLine();
            flightInfo.setOriginCountry(originCountry);

            System.out.println("Enter origin city:");
            String originCity = read.nextLine();
            flightInfo.setOriginCity(originCity);

            System.out.println("Choose origin city airport:");
            ArrayList<Map<String, String>> originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());

            if (originCityAirports.size() == 1) {
                for (int i = 0; i < originCityAirports.size(); i++) {
                    System.out.println((i + 1) + ". " + originCityAirports.get(i).get("PlaceName"));
                }

                String chosenOriginAirportIndex = read.nextLine();
                String unformattedAirportCode = originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - 1).get("PlaceId");
                table.setAirportCode(unformattedAirportCode.substring(0, unformattedAirportCode.indexOf("-")));

            } else {
                for (int i = 1; i < originCityAirports.size(); i++) {
                    System.out.println((i) + ". " + originCityAirports.get(i).get("PlaceName"));
                }

                String chosenOriginAirportIndex = read.nextLine();
                String unformattedAirportCode = originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex)).get("PlaceId");
                table.setAirportCode(unformattedAirportCode.substring(0, unformattedAirportCode.indexOf("-")));
            }


        System.out.println("Choose destination (departures: d, arrivals: a):");
        String destination = read.nextLine();
        String destinationFormat = destination.equals("d") ? "departures" : "arrivals";
        table.setDepartureOrArrival(destinationFormat);


        table.getData().printTableData();*/








        //Flight search




        OnlineTableApi table = new OnlineTableApi();

        Scanner read = new Scanner(System.in);

        UserRequestInfo flightInfo = new UserRequestInfo();

        while (true) {
            System.out.println("Enter origin country:");
            String originCountry = read.nextLine();
            flightInfo.setOriginCountry(originCountry);

            System.out.println("Enter origin city:");
            String originCity = read.nextLine();
            flightInfo.setOriginCity(originCity);

            System.out.println("Choose origin city airport:");
            ArrayList<Map<String, String>> originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
            if (originCityAirports.size() > 1) {
                for (int i = 0; i < originCityAirports.size(); i++) {
                    if (i == 0) {
                        System.out.println((i + 1) + ". " + "Any airport");
                        continue;
                    }
                    System.out.println((i + 1) + ". " + originCityAirports.get(i).get("PlaceName"));
                }
            } else {
                System.out.println("1. " + originCityAirports.get(0).get("PlaceName"));
            }

            String chosenOriginAirportIndex = read.nextLine();
            flightInfo.setChosenOriginAirport(originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - 1));


            System.out.println("Enter destination country:");
            String destinationCountry = read.nextLine();
            flightInfo.setDestinationCountry(destinationCountry);

            System.out.println("Enter destination city:");
            String destinationCity = read.nextLine();
            flightInfo.setDestinationCity(destinationCity);

            System.out.println("Choose destination city airport:");
            ArrayList<Map<String, String>> destinationCityAirports = flightInfo.getCityInfo(flightInfo.getDestinationCity(), flightInfo.getDestinationCountry());

            if (destinationCityAirports.size() > 1) {
                for (int i = 0; i < destinationCityAirports.size(); i++) {
                    if (i == 0) {
                        System.out.println((i + 1) + ". " + "Any airport");
                        continue;
                    }
                    System.out.println((i + 1) + ". " + destinationCityAirports.get(i).get("PlaceName"));
                }
            } else {
                System.out.println("1. " + destinationCityAirports.get(0).get("PlaceName"));
            }

            String chosenDestinationAirportIndex = read.nextLine();
            flightInfo.setChosenDestinationAirport(destinationCityAirports.get(Integer.parseInt(chosenDestinationAirportIndex) - 1));

            System.out.println("Enter outbound date(yyyy-mm-dd):");
            String outboundDate = read.nextLine();
            flightInfo.setOutboundDate(outboundDate);

            System.out.println("Do you want to search two way flights?(yes/no)");
            String twoWayTrip = read.nextLine();

            if (twoWayTrip.equals("yes")) {
                System.out.println("Enter inbound date(yyyy-mm-dd):");
                String inboundDate = read.nextLine();
                flightInfo.setTwoWayTrip(true);
                flightInfo.setInboundDate(inboundDate);
            }

            System.out.println("Do you want to set cabin class?(yes/no)");
            String requireCabinClass = read.nextLine();

            if (requireCabinClass.equals("yes")) {
                System.out.println("Enter cabin class (economy, premiumeconomy, business, first):");
                String cabinClass = read.nextLine();
                flightInfo.setRequireCabinClass(true);
                flightInfo.setCabinClass(cabinClass);
            }


            System.out.println("Enter number of adult passengers");
            String adultsNumber = read.nextLine();
            flightInfo.setAdultsNumber(adultsNumber);

            FlightsDaoImpl db = new FlightsDaoImpl().requestApiData(flightInfo);
            FlightsService fs = new FlightsService(db);
            FlightsController fc = new FlightsController(fs);
            fc.printFlights();

            break;
        }
    }
}
