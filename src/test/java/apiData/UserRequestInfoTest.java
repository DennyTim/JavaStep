package apiData;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserRequestInfoTest {
    private UserRequestInfo userRequestInfo = new UserRequestInfo();



    @Test
    public void numberOfAirportsReturnedMustBeEqualToOptionsOfFlightFromKiev() {
        //given
        String country = "Ukraine";
        String city = "Kiev";
        //when
        List<Map<String, String>> airports = userRequestInfo.getCityInfo(city, country);
        //then
        assertEquals(3, airports.size());
    }

    @Test
    public void shouldReturnEmptyListIfCountryAndCityAreInvalid() {
        //given
        String country = "knsdlf";
        String city = "lkdsnl";
        //when
        List<Map<String, String>> airports = userRequestInfo.getCityInfo(city, country);
        //then
        assertEquals(0, airports.size());
    }
}