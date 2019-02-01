package apiData;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.tongfei.progressbar.ProgressBar;
import org.json.JSONObject;

public class FlightsResponseInfo {
    private String origin;
    private String destination;
    private String outboundDate;
    private String inboundDate = "";
    private int adultsNumber;
    private String cabinClass = "";
    private boolean twoWayTrip;
    private String apiKey = "c401012205msh38febc44f4dbc18p159169jsn0206f678248f";
    public static ProgressBar pb = new ProgressBar("Loading avialable flights", 100);


    public FlightsResponseInfo(UserRequestInfo flightInfo) {
        this.origin = flightInfo.getChosenOriginAirport().get("PlaceId");
        this.destination = flightInfo.getChosenDestinationAirport().get("PlaceId");
        this.outboundDate = flightInfo.getOutboundDate();
        this.inboundDate = flightInfo.getInboundDate();
        this.adultsNumber = Integer.parseInt(flightInfo.getAdultsNumber());
        this.cabinClass = flightInfo.getCabinClass();
        this.twoWayTrip = flightInfo.isTwoWayTrip();
    }



    public String createSession() {
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

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return "";
    }


    public JSONObject getResponceData() {
        pb = new ProgressBar("Loading avialable flights", 100);
        pb.start();
        String session = createSession();
        pb.stepTo(25);
        try {
            HttpResponse<JsonNode> demoData = Unirest.get(String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/%s?pageIndex=0&pageSize=10", session))
                    .header("X-RapidAPI-Key", "c401012205msh38febc44f4dbc18p159169jsn0206f678248f")
                    .asJson();
            pb.stepTo(50);
            HttpResponse<JsonNode> responseData = Unirest.get(String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/%s?pageIndex=0&pageSize=10", session))
                    .header("X-RapidAPI-Key", "c401012205msh38febc44f4dbc18p159169jsn0206f678248f")
                    .asJson();

            pb.stepTo(100);
            pb.stop();

            return new JSONObject(responseData);

        } catch (UnirestException e) {
            pb.stop();
            e.printStackTrace();
        }

        return null;
    }

    public boolean isTwoWayTrip() {
        return twoWayTrip;
    }
}

