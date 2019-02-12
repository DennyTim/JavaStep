package model.onlineTable.dao;

import apiServices.OnlineTableApiService;
import contracts.DAO;
import model.dto.AirportTable;

import java.util.List;

public class OnlineTableDao implements DAO<AirportTable> {

    private String airportCode;
    private String departureOrArrival;
    private List<AirportTable> airportsList;

    public OnlineTableDao(String airportCode, String departureOrArrival) {
        this.airportCode = airportCode;
        this.departureOrArrival = departureOrArrival;
    }

    @Override
    public List<AirportTable> getAll() {
        if (airportsList == null) airportsList = new OnlineTableApiService(airportCode, departureOrArrival).getData();
        return airportsList;
    }

    @Override
    public AirportTable get(int index) {
        throw new IllegalStateException("Implementation doesn't imply get() method");
    }

    @Override
    public void add(AirportTable element) {
        throw new IllegalStateException("Implementation doesn't imply add() method");
    }

    @Override
    public void remove(int index) {
        throw new IllegalStateException("Implementation doesn't imply remove() method");
    }
}
