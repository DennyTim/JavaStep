package model.bookings.service;

import auth.User;
import auth.UserData;
import model.dto.FlightOffer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BookingsServiceTest {

    static User test = new User("Test", "Test", 33);
    static UserData testUser = new UserData(test, "test", "test");
    static BookingsService bookingsService = BookingsService.setBookingsData(testUser);


    FlightOffer fo = new FlightOffer();
    FlightOffer fo2 = new FlightOffer();



    Map<String, String> inbound = new HashMap<>();
    Map<String, String> outbound = new HashMap<>();


    Map<String, String> inbound2 = new HashMap<>();
    Map<String, String> outbound2 = new HashMap<>();




//    HashMap<String, Map<String, String>> outbound = new HashMap<String, Map<String, String>>() {{
//        put("Outbound", new HashMap<String, String>() {{
//            put("Outb-key", "Outb-value");
//        }});
//    }};

    @Before
    public void setUp(){
        inbound.put("inb-key", "inb-value");
        inbound2.put("inb-key2", "inb-value2");
        outbound.put("inb-key3", "inb-value3");
        outbound2.put("inb-key4", "inb-value4");

        fo.setInboundFlight(inbound);
        fo.setOutboundFlight(outbound);

        fo2.setInboundFlight(inbound2);
        fo2.setOutboundFlight(outbound2);


    }


    @Test
    public void getAll_Positive() {
        //        given
        //        when
        List<FlightOffer> bookedFlightsExpected = testUser.getBookedFlights();
//        then
        assertEquals(bookedFlightsExpected,bookingsService.getAll());
    }


    @Test
    public void getAll_Negative() {
//        given
        List<FlightOffer> bookedFlightsNotExpected = new ArrayList<>();
//        when
        FlightOffer fo = new FlightOffer();
        HashMap<String, String> map = new HashMap<>();
        map.put("test","test");
        map.put("test2","test2");
        map.put("test3","test3");
        map.put("test4","test4");
        fo.setInboundFlight(map);
        fo.setOutboundFlight(map);
        bookedFlightsNotExpected.add(fo);

//        then
        assertNotEquals(bookedFlightsNotExpected,bookingsService.getAll());
    }



    @Test
    public void add() {
//    given
//    when
        bookingsService.add(fo);
        bookingsService.add(fo2);

        List<FlightOffer> bookedFlightsExpected = new ArrayList<>();

        bookedFlightsExpected.add(fo);
        bookedFlightsExpected.add(fo2);

//      then
        assertEquals(bookedFlightsExpected,bookingsService.getAll());
        assertTrue(bookingsService.getAll().size() == 2);
        assertEquals(bookingsService.get(0), fo);
        assertEquals(bookingsService.get(1), fo2);
    }


    @Test
    public void delete() {
//    given
        bookingsService.add(fo);
        bookingsService.add(fo2);
//    when
        bookingsService.delete(0);

//      then
        assertTrue(bookingsService.getAll().size() == 1);
        assertEquals(bookingsService.get(0), fo2);
        bookingsService.delete(0);
    }




    @Test
    public void get() {
//    given
//    when
        bookingsService.add(fo);
//    then
        assertEquals(fo,bookingsService.get(0));
        assertEquals(bookingsService.getAll().get(0),bookingsService.get(0));

    }

    @AfterClass
    public static void unset() {
        bookingsService.delete(0);
        bookingsService.delete(0);
        bookingsService.delete(0);
    }

}