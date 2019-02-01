package model.flights.dao;

import apiData.FlightsData;
import apiData.FlightsResponseInfo;
import apiData.UserRequestInfo;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class FlightsDaoImpl implements FlightsDao {

    private ArrayList<Map<String, Map<String, String>>> flightsData;
    private boolean twoWayTrip;

    public FlightsDaoImpl requestApiData(UserRequestInfo userRequest) {
        FlightsResponseInfo apiRequest = new FlightsResponseInfo(userRequest);
        JSONObject response = apiRequest.getResponceData();
        twoWayTrip = apiRequest.isTwoWayTrip();
        FlightsData flightsContainer = new FlightsData().obtainFlightData(response, twoWayTrip);
        flightsData = flightsContainer.getFlights();
        return this;
    }

    public void setFlightsData(FlightsData data) {
        flightsData = data.getFlights();
    }

    public ArrayList<Map<String, Map<String, String>>> getFlightsData() {
        return flightsData;
    }

    public boolean isTwoWayTrip() {
        return twoWayTrip;
    }

}
