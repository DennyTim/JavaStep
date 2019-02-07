package model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.tongfei.progressbar.ProgressBar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRequest {

    private String chosenOriginAirport;
    private String chosenDestinationAirport;
    private String outboundDate;
    private String inboundDate;
    private String cabinClass;
    private String adultsNumber;
    private boolean twoWayTrip = false;


    public UserRequest(String chosenOriginAirport, String chosenDestinationAirport, String outboundDate, String inboundDate) {

        this.chosenOriginAirport = chosenOriginAirport;
        this.chosenDestinationAirport = chosenDestinationAirport;
        this.outboundDate = outboundDate;
        this.inboundDate = inboundDate;
    }

    public UserRequest() {}



    public boolean isTwoWayTrip() {
        return twoWayTrip;
    }

    public void setTwoWayTrip(boolean twoWayTrip) {
        this.twoWayTrip = twoWayTrip;
    }

    public String getAdultsNumber() {
        return adultsNumber;
    }

    public void setAdultsNumber(String adultsNumber) {
        this.adultsNumber = adultsNumber;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }


    public String getChosenOriginAirport() {
        return chosenOriginAirport;
    }

    public void setChosenOriginAirport(String chosenOriginAirport) {
        this.chosenOriginAirport = chosenOriginAirport;
    }

    public String getChosenDestinationAirport() {
        return chosenDestinationAirport;
    }

    public void setChosenDestinationAirport(String chosenDestinationAirport) {
        this.chosenDestinationAirport = chosenDestinationAirport;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
    }
}

