package model.bookings.service;

import auth.User;
import auth.UserData;
import org.junit.After;
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



    HashMap<String, Map<String, String>> inbound = new HashMap<String, Map<String, String>>() {{
        put("Inbound", new HashMap<String, String>() {{
            put("inb-key", "inb-value");
        }});
    }};

    HashMap<String, Map<String, String>> outbound = new HashMap<String, Map<String, String>>() {{
        put("Outbound", new HashMap<String, String>() {{
            put("Outb-key", "Outb-value");
        }});
    }};



    @Test
    public void getAll_Positive() {
    //        given
    //        when
        List<Map<String, Map<String, String>>> bookedFlightsExpected = testUser.getBookedFlights();
//        then
        assertEquals(bookedFlightsExpected,bookingsService.getAll());
    }


    @Test
    public void getAll_Negative() {
//        given
        List<Map<String, Map<String, String>>> bookedFlightsNotExpected = new ArrayList<>();
//        when
        bookedFlightsNotExpected.add(new HashMap<String, Map<String, String>>(){{
            put("str",new HashMap<String, String>(){{
                put("test","test");
            }});
        }});
//        then
        assertNotEquals(bookedFlightsNotExpected,bookingsService.getAll());
    }



    @Test
    public void add() {
//    given
//    when
        bookingsService.add(inbound);
        bookingsService.add(outbound);

        List<Map<String, Map<String, String>>> bookedFlightsExpected = new ArrayList<>();

        bookedFlightsExpected.add(inbound);

        bookedFlightsExpected.add(outbound);

//      then
        assertEquals(bookedFlightsExpected,bookingsService.getAll());
        assertTrue(bookingsService.getAll().size() == 2);
        assertEquals(bookingsService.get(0), inbound);
        assertEquals(bookingsService.get(1), outbound);
    }


    @Test
    public void delete() {
//    given
        bookingsService.add(inbound);
        bookingsService.add(outbound);
//    when
        bookingsService.delete(0);

//      then
        assertTrue(bookingsService.getAll().size() == 1);
        assertEquals(bookingsService.get(0), outbound);
        bookingsService.delete(0);
    }




    @Test
    public void get() {
//    given
        bookingsService.add(inbound);
//    when
//    then
        HashMap<String, Map<String, String>> expected = inbound;

        assertEquals(expected,bookingsService.get(0));
        assertEquals(bookingsService.getAll().get(0),bookingsService.get(0));

    }

    @AfterClass
    public static void unset() {
        bookingsService.delete(0);
        bookingsService.delete(0);
        bookingsService.delete(0);
    }

}
