package model.flights.service;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.AirportsNotFoundException;
import me.tongfei.progressbar.ProgressBar;
import model.UserRequest;
import model.dto.Airport;
import model.dto.FlightOffer;
import model.flights.dao.FlightsDaoImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightsService {

    private FlightsDaoImpl dao;
    private UserRequest userRequest = new UserRequest();




    public void printAvialableFlights() {

        int[] counter = {1};
        String[] price = {""};
        String[] buy = {""};

        getAvailableFlights().forEach(e -> {
            Map<String, String> outbound = e.getOutboundFlight();
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

            if (!userRequest.isTwoWayTrip()) {
                System.out.println();
                System.out.println();
                System.out.println("Price: " + "$" + price[0]);
                System.out.println("Buy ticket: " + buy[0]);
            }

            if (e.getInboundFlight() != null) {
                Map<String, String> inbound = e.getInboundFlight();
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
    }

    public FlightOffer getFlight(int index) {
        return getAvailableFlights().get(index);
    }

    public List<FlightOffer> getAvailableFlights() {
        if (dao != null || userRequest.getAdultsNumber() != null) return dao.getAll();
        throw new RuntimeException("User request wasn't finished");
    }

    public List<Airport> getAirportsByCityAndCountry(String city, String country) {

        List<Airport> airports = new ArrayList<>();

        try {

            ProgressBar pb = new ProgressBar("Loading city airports", 100);
            pb.start();


            HttpResponse<JsonNode> response = Unirest.get(String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/US/USD/en-US/?query=%s", city))
                    .header("X-RapidAPI-Key", "c401012205msh38febc44f4dbc18p159169jsn0206f678248f")
                    .asJson();

            pb.stepTo(100);
            pb.stop();

            Thread.sleep(500);

            JSONObject responseJSON = new JSONObject(response);

            JSONArray citiesJSONArray = responseJSON.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Places");



            for (int i = 0; i < citiesJSONArray.length(); i++) {

                JSONObject cityAirport = citiesJSONArray.getJSONObject(i);

                String airportCountryName = cityAirport.getString("CountryName");


                if (airportCountryName.equals(country)) {
                    Airport cityAirportInfo = new Airport();
                    cityAirportInfo.setPlaceId(cityAirport.getString("PlaceId"));
                    cityAirportInfo.setPlaceName(cityAirport.getString("PlaceName"));
                    cityAirportInfo.setCountryId(cityAirport.getString("CountryId"));
                    cityAirportInfo.setRegionId(cityAirport.getString("RegionId"));
                    cityAirportInfo.setCountryName(cityAirport.getString("CountryName"));

                    airports.add(cityAirportInfo);
                }
            }
            if (airports.size() < 1) throw new AirportsNotFoundException();
            printAirports(airports);
            return airports;
        } catch (UnirestException | InterruptedException e) {
            throw new AirportsNotFoundException();
        }

    }

    private void printAirports(List<Airport> airports) {
        for (int i = 0; i < airports.size(); i++) {
            if (airports.size() == 1) {
                System.out.printf("\n%d. %s", i + 1, airports.get(i).getPlaceName());
            } else {
                if (i == 0) {
                    System.out.print("\n1. Any airport");
                } else {
                    System.out.printf("\n%d. %s", i + 1, airports.get(i).getPlaceName());
                }
            }
        }
    }


    public void setOriginAirportCode(String code) {
        userRequest.setChosenOriginAirport(code);
    }

    public void setDestinationAirportCode(String code) {
        userRequest.setChosenDestinationAirport(code);
    }

    public void setOutboundDate(String date) {
        userRequest.setOutboundDate(date);
    }

    public void setInboundDate(String date) {
        userRequest.setInboundDate(date);
    }

    public void setCabinClass(String cabinClass) {
        userRequest.setCabinClass(cabinClass);
    }

    public void setAdultsNumber(String adultsNumber) {
        userRequest.setAdultsNumber(adultsNumber);
    }

    public void setIsTwoWayTrip(boolean isTwoWayTrip) {
        userRequest.setTwoWayTrip(isTwoWayTrip);
    }

    public void setDao() {
        this.dao = new FlightsDaoImpl(userRequest);
    }
}
