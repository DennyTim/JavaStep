package model.flights.dao;

import apiData.FlightsResponseInfo;
import apiData.UserRequestInfo;
import org.json.JSONObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FlightsDaoImplTest {

    @Test
    public void FlightsDataArraySizeShouldBeGreaterThenZero() {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calOutbound = Calendar.getInstance();
        Calendar calInbound = Calendar.getInstance();
        calOutbound.setTime(new Date());
        calInbound.setTime(new Date());
        calOutbound.add(Calendar.DAY_OF_MONTH, 14);
        calInbound.add(Calendar.DAY_OF_MONTH, 28);
        String outboundDate = sdf.format(calOutbound.getTime());
        String inboundDate = sdf.format(calInbound.getTime());

        UserRequestInfo userRequestInfo = new UserRequestInfo();
        userRequestInfo.setOriginCountry("Ukraine");
        userRequestInfo.setOriginCity("Kiev");
        userRequestInfo.setDestinationCountry("France");
        userRequestInfo.setOriginCity("Paris");
        userRequestInfo.setChosenOriginAirport(new HashMap<String, String>() {{
            put("PlaceId", "KBP-sky");
        }});
        userRequestInfo.setChosenDestinationAirport(new HashMap<String, String>() {{
            put("PlaceId", "CDG-sky");
        }});
        userRequestInfo.setInboundDate(inboundDate);
        userRequestInfo.setOutboundDate(outboundDate);
        userRequestInfo.setAdultsNumber("2");
        userRequestInfo.setCabinClass("economy");
        FlightsDaoImpl fdi = new FlightsDaoImpl();
        //when
        fdi = fdi.requestApiData(userRequestInfo);
        //then
        assertTrue(fdi.getFlightsData().size() > 0);
    }
}