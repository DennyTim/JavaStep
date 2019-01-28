import auth.UserAuth;
import auth.UserData;
import model.bookings.dao.BookingsDao;
import model.bookings.dao.BookingsDaoImpl;
import model.bookings.service.BookingsService;
import view.ConsoleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UserData actualUser = UserAuth.returnActualUser();
//
        BookingsDao bookingsDao = new BookingsDaoImpl(actualUser);
        BookingsService bookingService = new BookingsService(bookingsDao);
//        System.out.println(bookingService.getAll());
        bookingService.displayBookedFlights();

//
//        HashMap testData = new HashMap(){{
//            put("Inbound",new HashMap<String,String>(){{
//                put("valera","loh");
//            }
//            });
//        }};
//
//        test.add(testData);

//        Online table
//
//        ConsoleView consoleView = new ConsoleView();
//        consoleView.printOnlineTableData();

        //Flight search
//        ConsoleView consoleView = new ConsoleView();
//       Map<String, Map<String, String>> mapTest = consoleView.flightsService().get(0);
//        test.add(mapTest);
//        test.add(consoleView.flightsService().get(1));

    }
}
