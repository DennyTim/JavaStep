package model.flights.controller;


import model.dto.Airport;
import model.dto.FlightOffer;
import model.flights.dao.FlightsDaoImpl;
import model.flights.service.FlightsService;

import java.util.List;

public class FlightsController {

    private FlightsService fs = new FlightsService();

    public void printAvialableFlights() {
        fs.printAvialableFlights();
    }

    public FlightOffer getFlight(int index) {
        return fs.getFlight(index);
    }

    public List<FlightOffer> getAvialableFlights() {
        return fs.getAvailableFlights();
    }

    public List<Airport> getAirportByCityAndCountry(String city, String country) {
        return fs.getAirportsByCityAndCountry(city, country);
    }

    public void setOriginAirportCode(String code) {
        fs.setOriginAirportCode(code);
    }

    public void setDestinationAirportCode(String code) {
        fs.setDestinationAirportCode(code);
    }

    public void setOutboundDate(String date) {
        fs.setOutboundDate(date);
    }

    public void setInboundDate(String date) {
        fs.setInboundDate(date);
    }

    public void setCabinClass(String cabinClass) {
        fs.setCabinClass(cabinClass);
    }

    public void setAdultsNumber(String adultsNumber) {
        fs.setAdultsNumber(adultsNumber);
    }

    public void setIsTwoWayTrip(boolean isTwoWayTrip) {
        fs.setIsTwoWayTrip(isTwoWayTrip);
    }

    public void setDao() {
        fs.setDao();
    }

}
