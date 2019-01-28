package model.flights.controller;

import model.flights.service.FlightsService;

import java.util.Map;

public class FlightsController {
    private FlightsService flightsService;

    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    public void printFlights() {
        flightsService.printFlights();
    }

    public Map<String, Map<String, String>> flightToBook(int userInput) {
        return flightsService.flightToBook(userInput);
    }
}
