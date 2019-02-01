package model.flights.controller;

import apiData.UserRequestInfo;
import model.flights.service.FlightsService;
import java.util.Map;

public class FlightsController {

    private FlightsService flightsService;

    private FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    public void printFlights() {
        flightsService.printFlights();
    }

    public Map<String, Map<String, String>> flightToBook(int userInput) {
        return flightsService.flightToBook(userInput);
    }

    public static FlightsController instance(UserRequestInfo userRequestInfo) {
        return new FlightsController(FlightsService.instance(userRequestInfo));
    }

}
