package model.flights.service;

import model.flights.dao.FlightsDaoImpl;

import java.util.ArrayList;
import java.util.Map;

public class FlightsService {
    private FlightsDaoImpl data;
    private ArrayList<Map<String, Map<String, String>>> flightInfo;


    public FlightsService(FlightsDaoImpl data) {
        this.data = data;
    }

    public void printFlights() {
        ArrayList<Map<String, Map<String, String>>> flights = data.getFlightsData();

        int[] counter = {1};
        String[] price = {""};
        String[] buy = {""};

        flights.forEach(e -> {


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


            } else if (e.keySet().contains("inbound")) {
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
        flightInfo = flights;
    }


    public Map<String, Map<String, String>> flightToBook(int userInput) {
        ArrayList<Map<String, Map<String, String>>> formatedFlights = new ArrayList<>();


        for (int i = 0, counter = 0; i < flightInfo.size(); i++) {
            if (flightInfo.get(i).containsKey("outbound")) {
                formatedFlights.add(flightInfo.get(i));
            } else if (flightInfo.get(i).containsKey("inbound")) {
                formatedFlights.get(counter).put("inbound", flightInfo.get(i).get("inbound"));
                counter ++;
            }
        }



        return formatedFlights.get(userInput);

    }

}
