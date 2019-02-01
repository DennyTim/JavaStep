package apiData;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.tongfei.progressbar.ProgressBar;
import org.json.JSONArray;
import org.json.JSONObject;

public class OnlineTableApi {

    private String airportCode;
    private String departureOrArrival;
    private JSONObject tableJson;
    private ProgressBar pb;

    public OnlineTableApi() {
    }

    public OnlineTableApi getData() {

        pb = new ProgressBar("Loading airport table", 100);


        pb.start();


        try {
            HttpResponse data = Unirest.get(

                    "https://api.flightstats.com/flex/fids/rest/v1/json/" + airportCode + "/" + departureOrArrival + "?appId=f462bd1a&appKey=024a0bb186e058950898e5c9bde1d251&requestedFields=airlineCode%2CflightNumber%2Ccity%2CcurrentTime%2Cgate%2Cremarks&sortFields=currentTime&lateMinutes=15&useRunwayTimes=false&excludeCargoOnlyFlights=false"
            )
                    .asJson();

            tableJson = jsonData(data);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        pb.stepTo(50);

        return this;
    }


    public void printTableData() {

        pb.stepTo(100);
        pb.stop();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (tableJson == null) {
            System.out.println("No airport data");
            return;
        }

        JSONArray tableArray = tableJson.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("fidsData");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("%10s %25s %30s %30s", "Flight number", "Time", "City", "Status");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < tableArray.length(); i++) {
            JSONObject flightInfo = tableArray.getJSONObject(i);

            String flightNumber = flightInfo.getString("airlineCode") + " " + flightInfo.getString("flightNumber");
            String time = flightInfo.getString("currentTime");
            String city = flightInfo.getString("city");
            String status = flightInfo.getString("remarks");

            System.out.format("%10s %29s %29s %32s"
                    , flightNumber, time, city, status);
            System.out.println();
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------");


    }



    public JSONObject jsonData(HttpResponse data) {
        return new JSONObject(data);
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public void setDepartureOrArrival(String departureOrArrival) {
        this.departureOrArrival = departureOrArrival;
    }

    public JSONObject getTableJson() {
        return tableJson;
    }
}
