package model.flights.service;

import exceptions.AirportsNotFoundException;
import model.dto.Airport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class FlightsServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void listOfKievAirportsSizeShouldBeEqualToThree() {
        //given
        String city = "Kiev";
        String country = "Ukraine";
        FlightsService fs = new FlightsService();
        //when
        List<Airport> airports = fs.getAirportsByCityAndCountry(city, country);
        //then
        assertEquals(3, airports.size());
    }

    @Test(expected = AirportsNotFoundException.class)
    public void gettingAirportsByCityAndCountryShouldThrowAirportsNotFoundException() {
        //given
        String city = "LKDNfndsg";
        String country = "Uganda";
        FlightsService fs = new FlightsService();
        //when
        List<Airport> airports = fs.getAirportsByCityAndCountry(city, country);
    }

    @Test
    public void shouldGetRuntimeExceptionWhenFlightServiceDaoIsNotSet() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("User request wasn't finished");
        //given
        FlightsService fs = new FlightsService();
        //when
        fs.getAvailableFlights();
    }
}