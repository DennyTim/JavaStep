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


//        Online table
//
//        ConsoleView consoleView = new ConsoleView();
//        consoleView.printOnlineTableData();

        //Flight search
        ConsoleView consoleView = new ConsoleView();
        consoleView.mainMenu();

    }
}
