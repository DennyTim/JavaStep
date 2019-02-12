package apiServices;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.FlightsNotFoundException;
import me.tongfei.progressbar.ProgressBar;
import model.UserRequest;
import model.dto.FlightOffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightsApiService {
    private String origin;
    private String destination;
    private String outboundDate;
    private String inboundDate = "";
    private int adultsNumber;
    private String cabinClass = "";
    private boolean twoWayTrip;
    private String apiKey = "c401012205msh38febc44f4dbc18p159169jsn0206f678248f";
    public static ProgressBar pb = new ProgressBar("Loading avialable flights", 100);


    public FlightsApiService(UserRequest flightInfo) {
        this.origin = flightInfo.getChosenOriginAirport();
        this.destination = flightInfo.getChosenDestinationAirport();
        this.outboundDate = flightInfo.getOutboundDate();
        this.inboundDate = flightInfo.getInboundDate();
        this.adultsNumber = Integer.parseInt(flightInfo.getAdultsNumber());
        this.cabinClass = flightInfo.getCabinClass();
        this.twoWayTrip = flightInfo.isTwoWayTrip();
    }


    public List<FlightOffer> getData() {
        pb = new ProgressBar("Loading avialable flights", 100);
        pb.start();
        String sessionDemo = createSession();
        String session = createSession();
        pb.stepTo(25);
        try {
            pb.stepTo(50);
            HttpResponse<JsonNode> responseData = Unirest.get(String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/%s?pageIndex=0&pageSize=10", session))
                    .header("X-RapidAPI-Key", "c401012205msh38febc44f4dbc18p159169jsn0206f678248f")
                    .asJson();

            pb.stepTo(100);
            pb.stop();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e1) {
                System.out.println(e1.getMessage());
            }

            return jsonToDto(new JSONObject(responseData));

        } catch (UnirestException e) {
            pb.stop();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e1) {
                System.out.println(e1.getMessage());
            }

            throw new FlightsNotFoundException();
        }

    }


    private String createSession() {
        try {
            HttpResponse<JsonNode> response = Unirest.post("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0")
                    .header("X-RapidAPI-Key", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("inboundDate", inboundDate)
                    .field("cabinClass", cabinClass)
                    .field("children", 0)
                    .field("infants", 0)
                    .field("groupPricing", "false")
                    .field("country", "US")
                    .field("currency", "USD")
                    .field("locale", "en-US")
                    .field("originPlace", origin)
                    .field("destinationPlace", destination)
                    .field("outboundDate", outboundDate)
                    .field("adults", adultsNumber)
                    .asJson();

            JSONObject servResp = new JSONObject(response);

            String key = servResp.getJSONObject("headers").getJSONArray("Location").toString();

            int keyIndex = key.lastIndexOf("/") + 1;

            int keyStringLength = key.length();

            return key.substring(keyIndex, keyStringLength - 2);

        } catch (UnirestException |JSONException e) {
            pb.stop();
            throw new FlightsNotFoundException();
        }

    }



    private List<FlightOffer> jsonToDto(JSONObject data) {

        List<FlightOffer> result = new ArrayList<>();

        JSONArray itineraries = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Itineraries");
        JSONArray legs = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Legs");
        JSONArray carriers = data.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("Carriers");




        ArrayList<Map<String, String>> variants = new ArrayList<Map<String, String>>();




        for (int i = 0; i < itineraries.length(); i++) {
            final String price = String.valueOf(itineraries.getJSONObject(i).getJSONArray("PricingOptions").getJSONObject(0).getInt("Price"));
            final String buyURL = itineraries.getJSONObject(i).getJSONArray("PricingOptions").getJSONObject(0).getString("DeeplinkUrl");
            final String outboundID = itineraries.getJSONObject(i).getString("OutboundLegId");
            final String inboundID = twoWayTrip ? itineraries.getJSONObject(i).getString("InboundLegId") : null;

            variants.add(new HashMap<String, String>() {{
                put("buyURL", buyURL);
                put("price", price);
                put("outboundID", outboundID);
                put("inboundID", inboundID);
            }});


        }


        for (Map<String, String> m : variants) {

            FlightOffer flightOffer = new FlightOffer();

            for (int i = 0; i < legs.length(); i++) {

                Map<String, String> flight;

                if (m.get("outboundID").equals(legs.getJSONObject(i).getString("Id"))) {


                    flight = new HashMap<String, String>();

                    String price = m.get("price");
                    String buyURL = m.get("buyURL");
                    String carrier = "";
                    String carrierCode = "";
                    int numberOfStops = legs.getJSONObject(i).getJSONArray("Stops").length();
                    String stops = numberOfStops > 0 ? String.format("Trip has %d catch flights", numberOfStops) : "The flight is straight";
                    String departure = legs.getJSONObject(i).getString("Departure");
                    String arrival = legs.getJSONObject(i).getString("Arrival");
                    String flightNumber = legs.getJSONObject(i).getJSONArray("FlightNumbers").getJSONObject(0).getString("FlightNumber");
                    int minutesDuration = legs.getJSONObject(i).getInt("Duration");
                    int hours = minutesDuration / 60;
                    int minutes = minutesDuration % 60;
                    String duration = String.format("%d hours %d minutes", hours, minutes);

                    for (int j = 0; j < carriers.length(); j++) {
                        if (legs.getJSONObject(i).getJSONArray("Carriers").getInt(0) == carriers.getJSONObject(j).getInt("Id")) {
                            carrier = carriers.getJSONObject(j).getString("Name");
                            carrierCode = carriers.getJSONObject(j).getString("Code");
                        }
                    }


                    flight.put("price", price);
                    flight.put("carrier", carrier);
                    flight.put("stops", stops);
                    flight.put("departure", departure);
                    flight.put("arrival", arrival);
                    flight.put("flightNumber", flightNumber);
                    flight.put("carrierCode", carrierCode);
                    flight.put("duration", duration);
                    flight.put("buy", buyURL);

                    flightOffer.setOutboundFlight(flight);
                }

                if (twoWayTrip && m.get("inboundID").equals(legs.getJSONObject(i).getString("Id"))) {
                    flight = new HashMap<String, String>();
                    String price = m.get("price");
                    String buyURL = m.get("buyUrl");
                    String carrier = "";
                    String carrierCode = "";
                    int numberOfStops = legs.getJSONObject(i).getJSONArray("Stops").length();
                    String stops = numberOfStops > 0 ? String.format("Trip has %d catch flights", numberOfStops) : "The flight is straight";
                    String departure = legs.getJSONObject(i).getString("Departure");
                    String arrival = legs.getJSONObject(i).getString("Arrival");
                    String flightNumber = legs.getJSONObject(i).getJSONArray("FlightNumbers").getJSONObject(0).getString("FlightNumber");
                    int minutesDuration = legs.getJSONObject(i).getInt("Duration");
                    int hours = minutesDuration / 60;
                    int minutes = minutesDuration % 60;
                    String duration = String.format("%d hours %d minutes", hours, minutes);

                    for (int j = 0; j < carriers.length(); j++) {
                        if (legs.getJSONObject(i).getJSONArray("Carriers").getInt(0) == carriers.getJSONObject(j).getInt("Id")) {
                            carrier = carriers.getJSONObject(j).getString("Name");
                            carrierCode = carriers.getJSONObject(j).getString("Code");
                        }
                    }


                    flight.put("price", price);
                    flight.put("carrier", carrier);
                    flight.put("stops", stops);
                    flight.put("departure", departure);
                    flight.put("arrival", arrival);
                    flight.put("flightNumber", flightNumber);
                    flight.put("carrierCode", carrierCode);
                    flight.put("duration", duration);
                    flight.put("buy", buyURL);

                    flightOffer.setInboundFlight(flight);

                }


            }
            result.add(flightOffer);
        }

        if (result.size() < 1) throw new FlightsNotFoundException();

        return result;
}

}
