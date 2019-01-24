package apiData;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class OnlineTableApi {

    private String airportCode;
    //departures or arrivals
    private String departureOrArrival;


    public OnlineTableApi(String airportCode, String departureOrArrival) {

        this.airportCode = airportCode;
        this.departureOrArrival = departureOrArrival;

    }

    public OnlineTableApi() {
    }

    public JSONObject getData() {

        HttpResponse data = null;

        try {
            data = Unirest.get(

                    "https://api.flightstats.com/flex/fids/rest/v1/json/" + airportCode + "/" + departureOrArrival + "?appId=f462bd1a&appKey=024a0bb186e058950898e5c9bde1d251&requestedFields=airlineCode%2CflightNumber%2Ccity%2CcurrentTime%2Cgate%2Cremarks&sortFields=currentTime&lateMinutes=15&useRunwayTimes=false&excludeCargoOnlyFlights=false"
            )
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return jsonData(data);
    }



    public JSONObject jsonData(HttpResponse data) {
        return new JSONObject(data);
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getDepartureOrArrival() {
        return departureOrArrival;
    }

    public void setDepartureOrArrival(String departureOrArrival) {
        this.departureOrArrival = departureOrArrival;
    }
}
