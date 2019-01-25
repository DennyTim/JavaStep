import apiData.*;
import org.json.JSONObject;
import view.ConsoleView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Online table

        ConsoleView consoleView = new ConsoleView();
        consoleView.printData();

        //Flight search

//        OnlineTableApi table = new OnlineTableApi();
//        table.printData();

//        Scanner read = new Scanner(System.in);
//
//        UserRequestInfo flightInfo = new UserRequestInfo();
//
//        while (true) {
//            System.out.println("Enter origin country:");
//            String originCountry = read.nextLine();
//            flightInfo.setOriginCountry(originCountry);
//
//            System.out.println("Enter origin city:");
//            String originCity = read.nextLine();
//            flightInfo.setOriginCity(originCity);
//
//            System.out.println("Choose origin city airport:");
//            ArrayList<Map<String, String>> originCityAirports = flightInfo.getCityInfo(flightInfo.getOriginCity(), flightInfo.getOriginCountry());
//            for (int i = 0; i < originCityAirports.size(); i++) {
//                System.out.println((i + 1) + ". " + originCityAirports.get(i).get("PlaceName"));
//                System.out.println(originCityAirports.get(i).get("PlaceId"));
//            }
//
//            String chosenOriginAirportIndex = read.nextLine();
//            flightInfo.setChosenOriginAirport(originCityAirports.get(Integer.parseInt(chosenOriginAirportIndex) - 1));
//
//
//            System.out.println("Enter destination country:");
//            String destinationCountry = read.nextLine();
//            flightInfo.setDestinationCountry(destinationCountry);
//
//            System.out.println("Enter destination city:");
//            String destinationCity = read.nextLine();
//            flightInfo.setDestinationCity(destinationCity);
//
//            System.out.println("Choose origin city airport:");
//            ArrayList<Map<String, String>> destinationCityAirports = flightInfo.getCityInfo(flightInfo.getDestinationCity(), flightInfo.getDestinationCountry());
//            for (int i = 0; i < destinationCityAirports.size(); i++) {
//                System.out.println((i + 1) + ". " + destinationCityAirports.get(i).get("PlaceName"));
//            }
//
//            String chosenDestinationAirportIndex = read.nextLine();
//            flightInfo.setChosenDestinationAirport(destinationCityAirports.get(Integer.parseInt(chosenDestinationAirportIndex) - 1));
//
//            System.out.println("Enter outbound date(yyyy-mm-dd):");
//            String outboundDate = read.nextLine();
//            flightInfo.setOutboundDate(outboundDate);
//
//            System.out.println("Do you want to search two way flights?(yes/no)");
//            String twoWayTrip = read.nextLine();
//
//            if (twoWayTrip.equals("yes")) {
//                System.out.println("Enter inbound date(yyyy-mm-dd):");
//                String inboundDate = read.nextLine();
//                flightInfo.setTwoWayTrip(true);
//                flightInfo.setInboundDate(inboundDate);
//            }
//
//            System.out.println("Do you want to set cabin class?(yes/no)");
//            String requireCabinClass = read.nextLine();
//
//            if (requireCabinClass.equals("yes")) {
//                System.out.println("Enter cabin class (economy, premiumeconomy, business, first):");
//                String cabinClass = read.nextLine();
//                flightInfo.setRequireCabinClass(true);
//                flightInfo.setCabinClass(cabinClass);
//            }
//
//
//            System.out.println("Enter number of adult passengers");
//            String adultsNumber = read.nextLine();
//            flightInfo.setAdultsNumber(adultsNumber);
//
//            FlightsResponseInfo apiRequest = new FlightsResponseInfo(flightInfo);
//
//            JSONObject response = apiRequest.getResponceData();
//
//            System.out.println(response);
//
//            FlightsData flightData = new FlightsData();
//
//            flightData.obtainFlightData(response, apiRequest.isTwoWayTrip());
//            flightData.printFlights();
//
//            break;
//        }
    }
}
