package apiServices;

import exceptions.AirportsNotFoundException;
import model.dto.AirportTable;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OnlineTableApiServiceTest {

    @Test
    public void airportTableListSizeShouldBeGreaterThenZero() {
        //given
        OnlineTableApiService ots = new OnlineTableApiService("KBP", "arrivals");
        //when
        List<AirportTable> list = ots.getData();
        //then
        assertTrue(list.size() > 0);
    }

    @Test(expected = AirportsNotFoundException.class)
    public void shouldGetAirportsNotFoundExceptionWhenArgumentsAreWrong() {
        //given
        OnlineTableApiService ots = new OnlineTableApiService("BP", "arvals");
        //when
        List<AirportTable> list = ots.getData();
    }
}