package apiData;

import org.json.JSONArray;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class OnlineTableApiTest {

    private OnlineTableApi table = new OnlineTableApi();


    @Test
    public void TimeTableRequestShouldReturnJsonWithStatus200() {
        //given
        String airportCode = "kbp";
        String departureOrArrival = "departures";
        table.setAirportCode(airportCode);
        table.setDepartureOrArrival(departureOrArrival);
        //when
        table = table.getData();
        table.printTableData();
        JSONArray fidsArray = table.getTableJson().getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("fidsData");
        //then
        assertEquals(200, table.getTableJson().getInt("status"));
        assertTrue(fidsArray.length() > 0);

    }

    @Test
    public void TimeTableRequestShouldReturnJsonWithEmptyFidsArray() {
        //given
        String airportCode = "tik";
        String departureOrArrival = "arrivals";
        table.setAirportCode(airportCode);
        table.setDepartureOrArrival(departureOrArrival);
        //when
        table = table.getData();
        table.printTableData();
        JSONArray fidsArray = table.getTableJson().getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("fidsData");
        //then
        assertEquals(0, fidsArray.length());
    }


}