package apiServices;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.AirportsNotFoundException;
import me.tongfei.progressbar.ProgressBar;
import model.dto.AirportTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnlineTableApiService {

    private String airportCode;
    private String departureOrArrival;
    private ProgressBar pb;

    public OnlineTableApiService(String airportCode, String departureOrArrival) {
        this.airportCode = airportCode;
        this.departureOrArrival = departureOrArrival;
    }

    public List<AirportTable> getData() {

        pb = new ProgressBar("Loading airport table", 100);
        pb.start();

        HttpResponse data = null;


        try {
            data = Unirest.get(

                    "https://api.flightstats.com/flex/fids/rest/v1/json/" + airportCode + "/" + departureOrArrival + "?appId=f462bd1a&appKey=024a0bb186e058950898e5c9bde1d251&requestedFields=airlineCode%2CflightNumber%2Ccity%2CcurrentTime%2Cgate%2Cremarks&sortFields=currentTime&lateMinutes=15&useRunwayTimes=false&excludeCargoOnlyFlights=false"
            )
                    .asJson();




        } catch (UnirestException e) {
            throw new AirportsNotFoundException();
        }

        pb.stepTo(50);

        return jsonToDto(new JSONObject(data));
    }


    private List<AirportTable> jsonToDto(JSONObject airportData) {
        List<AirportTable> result = new ArrayList<>();

        JSONArray tableArray;

        try {
            tableArray = airportData.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("fidsData");
        } catch (JSONException e) {
            pb.stepTo(100);
            pb.stop();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e1) {
                System.out.println(e1.getMessage());
            }
            throw new AirportsNotFoundException();
        }

        if (tableArray.length() < 1) throw new AirportsNotFoundException();

        for (int i = 0; i < tableArray.length(); i++) {
            AirportTable tableItemDto = new AirportTable();
            JSONObject tableItemJson = tableArray.getJSONObject(i);
            String flightNumber = tableItemJson.getString("airlineCode") + " " + tableItemJson.getString("flightNumber");
            String time = tableItemJson.getString("currentTime");
            String city = tableItemJson.getString("city");
            String status = tableItemJson.getString("remarks");
            tableItemDto.setFlightNumber(flightNumber);
            tableItemDto.setTime(time);
            tableItemDto.setCity(city);
            tableItemDto.setStatus(status);
            result.add(tableItemDto);
        }

        pb.stepTo(100);
        pb.stop();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }
}
