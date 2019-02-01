package model.flights.service;

import apiData.UserRequestInfo;
import model.flights.dao.FlightsDaoImpl;
import view.Validation;

import java.util.ArrayList;
import java.util.Map;

public class FlightsService {
    private FlightsDaoImpl data;
    private ArrayList<Map<String, Map<String, String>>> flightInfo;
    private ArrayList<Map<String, Map<String, String>>> formattedFlights;


    private FlightsService(FlightsDaoImpl data) {
        this.data = data;
    }

    public static FlightsService instance(UserRequestInfo userRequest) {
        FlightsDaoImpl db = new FlightsDaoImpl().requestApiData(userRequest);
        return new FlightsService(db);
    }



    public void printFlights() {

        if (flightInfo == null) setFlightInfo();

        System.out.println(flightInfo.size());

        int[] counter = {1};
        String[] price = {""};
        String[] buy = {""};

        flightInfo.forEach(e -> {


            if (e.keySet().contains("outbound")) {

                Map<String, String> outbound = e.get("outbound");

                System.out.println();
                System.out.println();
                System.out.println(counter[0] + ".");
                System.out.println("Outbound: ");
                System.out.println("Carrier: " + outbound.get("carrier"));
                System.out.println("Departure: " + outbound.get("departure"));
                System.out.println("Arrival: " + outbound.get("arrival"));
                System.out.println("Flight number: " + outbound.get("carrierCode") + outbound.get("flightNumber"));
                System.out.println("Trip duration: " + outbound.get("duration"));

                price[0] = outbound.get("price");
                buy[0] = outbound.get("buy");
                counter[0]++;

                if (!data.isTwoWayTrip()) {
                    System.out.println();
                    System.out.println();
                    System.out.println("Price: " + "$" + price[0]);
                    System.out.println("Buy ticket: " + buy[0]);
                }


            }

            if (e.keySet().contains("inbound")) {
                Map<String, String> inbound = e.get("inbound");
                System.out.println();
                System.out.println();
                System.out.println("Inbound: ");
                System.out.println("Carrier: " + inbound.get("carrier"));
                System.out.println("Departure: " + inbound.get("departure"));
                System.out.println("Arrival: " + inbound.get("arrival"));
                System.out.println("Flight number: " + inbound.get("carrierCode") + inbound.get("flightNumber"));
                System.out.println("Trip duration: " + inbound.get("duration"));
                System.out.println();
                System.out.println();
                System.out.println("Price: " + "$" + price[0]);
                System.out.println("Buy ticket: " + buy[0]);
            }




        });

        if (flightInfo.size() == 0) throw new RuntimeException("Flights not found");
    }


    public Map<String, Map<String, String>> flightToBook(int userInput) {

        return flightInfo.get(Validation.validateFlightToBookInput(userInput, flightInfo.size()));

    }

    public ArrayList<Map<String, Map<String, String>>> getFormattedFlights() {
        return formattedFlights;
    }

    public void setFlightInfo() {
        this.flightInfo = data.getFlightsData();
    }
}
