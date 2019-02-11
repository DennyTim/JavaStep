package model.onlineTable.dao;

import exceptions.AirportsNotFoundException;
import model.dto.AirportTable;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OnlineTableDaoTest {


    @Test
    public void airportTableListSizeShouldBeGreaterThanZero() {
        //given
        OnlineTableDao ot = new OnlineTableDao("KBP", "departures");
        //when
        List<AirportTable> airportTable = ot.getAll();
        //then
        assertTrue(airportTable.size() > 0);
    }

    @Test(expected = AirportsNotFoundException.class)
    public void shouldGetAirportsNotFoundExceptionDueToIllegalArguments() {
        //given
        OnlineTableDao ot = new OnlineTableDao("KP", "epartues");
        //when
        List<AirportTable> airportTable = ot.getAll();

    }


    @Test(expected = IllegalStateException.class)
    public void shouldGetIllegalStateExceptionOnCallingGetMethod() {
        //given
        OnlineTableDao ot = new OnlineTableDao("KP", "epartues");
        int index = 0;
        //when
        AirportTable airportTable = ot.get(0);

    }

    @Test(expected = IllegalStateException.class)
    public void shouldGetIllegalStateExceptionOnCallingAddMethod() {
        //given
        OnlineTableDao ot = new OnlineTableDao("KP", "epartues");
        //when
        ot.add(new AirportTable());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldGetIllegalStateExceptionOnCallingRemoveMethod() {
        //given
        OnlineTableDao ot = new OnlineTableDao("KP", "epartues");
        int index = 0;
        //when
        ot.remove(index);
    }
}