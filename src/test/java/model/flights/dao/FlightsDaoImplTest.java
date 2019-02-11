package model.flights.dao;

import apiServices.FlightsApiService;
import exceptions.FlightsNotFoundException;
import model.UserRequest;
import model.dto.FlightOffer;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FlightsDaoImplTest {

    @Test
    public void flightOfferListSizeShouldBeGreaterThenZero() {
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

        UserRequest userRequestInfo = new UserRequest();
        userRequestInfo.setChosenOriginAirport("KBP-sky");
        userRequestInfo.setChosenDestinationAirport("CDG-sky");

        userRequestInfo.setInboundDate(inboundDate);
        userRequestInfo.setOutboundDate(outboundDate);
        userRequestInfo.setAdultsNumber("2");
        userRequestInfo.setCabinClass("economy");
        userRequestInfo.setTwoWayTrip(true);

        FlightsDaoImpl dao = new FlightsDaoImpl(userRequestInfo);

        //when
        List<FlightOffer> flightOfferList = dao.getAll();
        //then
        assertTrue(flightOfferList.size() > 0);
    }

    @Test(expected = FlightsNotFoundException.class)
    public void shouldGetNoFlightsFoundExceptionWhenNoFlightsCouldBeFound() {
        //given
        UserRequest userRequestInfo = new UserRequest();
        userRequestInfo.setChosenOriginAirport("KBP-sky");
        userRequestInfo.setChosenDestinationAirport("CDG-sky");

        userRequestInfo.setInboundDate("2019-02-10");
        userRequestInfo.setOutboundDate("2019-02-20");
        userRequestInfo.setAdultsNumber("2");
        userRequestInfo.setCabinClass("economy");
        userRequestInfo.setTwoWayTrip(true);

        FlightsDaoImpl dao = new FlightsDaoImpl(userRequestInfo);


        //when
        List<FlightOffer> flightOfferList = dao.getAll();
    }

    @Test
    public void getMethodShouldReturnValidFlightOffer() {
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

        UserRequest userRequestInfo = new UserRequest();
        userRequestInfo.setChosenOriginAirport("KBP-sky");
        userRequestInfo.setChosenDestinationAirport("CDG-sky");

        userRequestInfo.setInboundDate(inboundDate);
        userRequestInfo.setOutboundDate(outboundDate);
        userRequestInfo.setAdultsNumber("2");
        userRequestInfo.setCabinClass("economy");
        userRequestInfo.setTwoWayTrip(true);

        FlightsDaoImpl dao = new FlightsDaoImpl(userRequestInfo);

        //when
        FlightOffer flightOffer = dao.get(0);
        //then
        assertNotNull(flightOffer);
    }

    @Test(expected = FlightsNotFoundException.class)
    public void usingGetMethodShouldThrowNoFlightsFoundException() {
        //given
        UserRequest userRequestInfo = new UserRequest();
        userRequestInfo.setChosenOriginAirport("KBP-sky");
        userRequestInfo.setChosenDestinationAirport("CDG-sky");

        userRequestInfo.setInboundDate("2019-02-10");
        userRequestInfo.setOutboundDate("2019-02-20");
        userRequestInfo.setAdultsNumber("2");
        userRequestInfo.setCabinClass("economy");
        userRequestInfo.setTwoWayTrip(true);

        FlightsDaoImpl dao = new FlightsDaoImpl(userRequestInfo);


        //when
        FlightOffer flightOfferList = dao.get(0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldGetIllegalStateExceptionWhenUsingAddMethod() {
        //given
        FlightsDaoImpl dao = new FlightsDaoImpl(new UserRequest());
        //when
        dao.add(new FlightOffer());

    }

    @Test(expected = IllegalStateException.class)
    public void shouldGetIllegalStateExceptionWhenUsingRemoveMethod() {
        //given
        FlightsDaoImpl dao = new FlightsDaoImpl(new UserRequest());
        //when
        dao.remove(0);
    }
}