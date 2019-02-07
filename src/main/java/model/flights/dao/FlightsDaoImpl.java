package model.flights.dao;


import apiServices.FlightsApiService;
import contracts.DAO;
import model.UserRequest;
import model.dto.FlightOffer;

import java.util.List;

public class FlightsDaoImpl implements DAO {

    private UserRequest userRequest;
    private List<FlightOffer> flightOffers;

    public FlightsDaoImpl(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    @Override
    public List<FlightOffer> getAll() {
        if (flightOffers == null) flightOffers = new FlightsApiService(userRequest).getData();
        return flightOffers;
    }

    @Override
    public FlightOffer get(int index) {
        return getAll().get(index);
    }

    @Override
    public void add(Object element) {
        throw new IllegalStateException("Implementation doesn't imply add() method");
    }

    @Override
    public void remove(int index) {
        throw new IllegalStateException("Implementation doesn't imply remove() method");
    }
}
