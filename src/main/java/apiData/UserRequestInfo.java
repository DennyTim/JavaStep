package apiData;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.tongfei.progressbar.ProgressBar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserRequestInfo {
    private String originCountry;
    private String originCity;
    private String destinationCountry;
    private String destinationCity;
    private ArrayList<Map<String, String>> originAirports;
    private ArrayList<Map<String, String>> destinationAirports;
    private Map<String, String> chosenOriginAirport;
    private Map<String, String> chosenDestinationAirport;
    private Map<String, String> countries = new HashMap<String, String>();
    private String outboundDate;
    private String inboundDate;
    private String cabinClass;
    private String adultsNumber;
    private boolean twoWayTrip = false;
    private boolean requireCabinClass = false;


    public UserRequestInfo(String originCountry, String originCity, String destinationCountry, String destinationCity, ArrayList<Map<String, String>> originAirports, ArrayList<Map<String, String>> destinationAirports, Map<String, String> chosenOriginAirport, Map<String, String> chosenDestinationAirport, Map<String, String> countries, String outboundDate, String inboundDate) {
        this.originCountry = originCountry;
        this.originCity = originCity;
        this.destinationCountry = destinationCountry;
        this.destinationCity = destinationCity;
        this.originAirports = originAirports;
        this.destinationAirports = destinationAirports;
        this.chosenOriginAirport = chosenOriginAirport;
        this.chosenDestinationAirport = chosenDestinationAirport;
        this.countries = countries;
        this.outboundDate = outboundDate;
        this.inboundDate = inboundDate;
    }

    public UserRequestInfo() {
    }

    public String getCountryCode(String countryName) {
        return countries.get(countryName);
    }

    public ArrayList<Map<String, String>> getCityInfo(String city, String country) {

        ArrayList<Map<String, String>> airports = new ArrayList<Map<String, String>>();

        try {

            ProgressBar pb = new ProgressBar("Loading city airports", 100);
            pb.start();


            HttpResponse<JsonNode> response = Unirest.get(String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/US/USD/en-US/?query=%s", city))
                    .header("X-RapidAPI-Key", "c401012205msh38febc44f4dbc18p159169jsn0206f678248f")
                    .asJson();

            pb.stepTo(100);
            pb.stop();

            Thread.sleep(300);

            JSONObject responseJSON = new JSONObject(response);

            JSONArray citiesJSONArray = responseJSON.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Places");



            for (int i = 0; i < citiesJSONArray.length(); i++) {

                JSONObject cityAirport = citiesJSONArray.getJSONObject(i);

                String airportCountryName = cityAirport.getString("CountryName");


                if (airportCountryName.equals(country)) {
                    Map<String, String> cityAirportInfo = new HashMap<String, String>();
                    cityAirportInfo.put("PlaceId", cityAirport.getString("PlaceId"));
                    cityAirportInfo.put("PlaceName", cityAirport.getString("PlaceName"));
                    cityAirportInfo.put("CountryId", cityAirport.getString("CountryId"));
                    cityAirportInfo.put("RegionId", cityAirport.getString("RegionId"));
                    cityAirportInfo.put("CountryName", cityAirport.getString("CountryName"));

                    airports.add(cityAirportInfo);
                }
            }
            return airports;
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String toString() {
        return "FlightInfo{" +
                "chosenOriginAirport=" + chosenOriginAirport.get("PlaceId") +
                ", chosenDestinationAirport=" + chosenDestinationAirport.get("PlaceId") +
                ", outboundDate='" + outboundDate + '\'' +
                ", inboundDate='" + inboundDate + '\'' +
                ", cabinClass='" + cabinClass + '\'' +
                ", adultsNumber='" + adultsNumber + '\'' +
                '}';
    }


    public boolean isTwoWayTrip() {
        return twoWayTrip;
    }

    public void setTwoWayTrip(boolean twoWayTrip) {
        this.twoWayTrip = twoWayTrip;
    }

    public boolean isRequireCabinClass() {
        return requireCabinClass;
    }

    public void setRequireCabinClass(boolean requireCabinClass) {
        this.requireCabinClass = requireCabinClass;
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

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public ArrayList<Map<String, String>> getOriginAirports() {
        return originAirports;
    }

    public void setOriginAirports(ArrayList<Map<String, String>> originAirports) {
        this.originAirports = originAirports;
    }

    public ArrayList<Map<String, String>> getDestinationAirports() {
        return destinationAirports;
    }

    public void setDestinationAirports(ArrayList<Map<String, String>> destinationAirports) {
        this.destinationAirports = destinationAirports;
    }

    public Map<String, String> getChosenOriginAirport() {
        return chosenOriginAirport;
    }

    public void setChosenOriginAirport(Map<String, String> chosenOriginAirport) {
        this.chosenOriginAirport = chosenOriginAirport;
    }

    public Map<String, String> getChosenDestinationAirport() {
        return chosenDestinationAirport;
    }

    public void setChosenDestinationAirport(Map<String, String> chosenDestinationAirport) {
        this.chosenDestinationAirport = chosenDestinationAirport;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
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

