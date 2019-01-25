package model.flights.controller;

import model.flights.service.FlightsService;

public class FlightsController {
    private FlightsService flightsService;

    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    public void printFlights() {
        flightsService.printFlights();
    }
}
