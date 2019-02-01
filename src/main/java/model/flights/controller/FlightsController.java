package model.flights.controller;

import apiData.UserRequestInfo;
import logging.Logger;
import model.flights.service.FlightsService;
import java.util.Map;

public class FlightsController {

    private FlightsService flightsService;

    private FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    public void printFlights() {
        flightsService.printFlights();
        Logger.info("Flights: printed flights");
    }

    public Map<String, Map<String, String>> flightToBook(int userInput) {
        return flightsService.flightToBook(userInput);
    }

    public static FlightsController instance(UserRequestInfo userRequestInfo) {
        return new FlightsController(FlightsService.instance(userRequestInfo));
    }

}
