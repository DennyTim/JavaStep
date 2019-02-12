package model.bookings.service;

import auth.UserData;
import contracts.DAO;
import model.bookings.dao.BookingsDaoImpl;
import model.dto.FlightOffer;

import java.util.List;
import java.util.Map;

public class BookingsService {
    private static DAO<FlightOffer> bookingsData;
    private static BookingsService service;

    private BookingsService() {}

    public static BookingsService instance() {
        if (service == null) {
            service = new BookingsService();
        }
        return service;
    }

    public void displayBookedFlights(){
        int[] counter = {1};
        String[] price = {""};
        String[] buy = {""};

        bookingsData.getAll().forEach(e -> {
            Map<String, String> outbound = e.getOutboundFlight();
            System.out.println();
            System.out.println();
            System.out.println(counter[0] + ".");
            System.out.println("Outbound: ");
            System.out.println("Carrier: " + outbound.get("carrier"));
            System.out.println("Departure: " + outbound.get("departure"));
            System.out.println("Arrival: " + outbound.get("arrival"));
            System.out.println("Flight number: " + outbound.get("carrierCode") + outbound.get("flightNumber"));
            System.out.println("Trip duration: " + outbound.get("duration"));

            price[0] = outbound.get("price");
            buy[0] = outbound.get("buy");
            counter[0]++;

            if (e.getInboundFlight() == null) {
                System.out.println();
                System.out.println();
                System.out.println("Price: " + "$" + price[0]);
                System.out.println("Buy ticket: " + buy[0]);
            }

            if (e.getInboundFlight() != null) {
                Map<String, String> inbound = e.getInboundFlight();
                System.out.println();
                System.out.println("Inbound: ");
                System.out.println("Carrier: " + inbound.get("carrier"));
                System.out.println("Departure: " + inbound.get("departure"));
                System.out.println("Arrival: " + inbound.get("arrival"));
                System.out.println("Flight number: " + inbound.get("carrierCode") + inbound.get("flightNumber"));
                System.out.println("Trip duration: " + inbound.get("duration"));
                System.out.println();
                System.out.println();
                System.out.println("Price: " + "$" + price[0]);
                System.out.println("Buy ticket: " + buy[0]);
            }

        });
    }

    public void delete(int index) {
        bookingsData.remove(index);
    }

    public void add(FlightOffer flight) {
        bookingsData.add(flight);
    }

    public List<FlightOffer> getAll() {
        return bookingsData.getAll();
    }

    public FlightOffer get(int index) {
        return bookingsData.get(index);
    }

    public static BookingsService setBookingsData(UserData actualUser) {
        bookingsData = BookingsDaoImpl.instance().setActualUser(actualUser);
        return BookingsService.instance();
    }

}