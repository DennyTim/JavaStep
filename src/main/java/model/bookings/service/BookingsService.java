package model.bookings.service;

import auth.UserData;
import model.bookings.dao.BookingsDao;
import model.bookings.dao.BookingsDaoImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class BookingsService {
    private static BookingsDao bookingsData;
    private static BookingsService service;

    private BookingsService() {}

    public static BookingsService instance() {
        if (service == null) {
            service = new BookingsService();
        }
        return service;
    }

    public void displayBookedFlights(){
        List<Map<String, Map<String, String>>> list = bookingsData.getAll();
        IntStream.range(0,list.size())
                .forEach(i -> {
                    String outbound = "";
                    String outboundFlightNumber = "flightNumber: " + list.get(i).get("outbound").get("carrierCode") + list.get(i).get("outbound").get("flightNumber");
                    for(String key : list.get(i).get("outbound").keySet()){
                        outbound += key.equals("price") || key.equals("buy") || key.equals("carrierCode") || key.equals("flightNumber") ? "" : key + ": " + list.get(i).get("outbound").get(key) + "\n";
                    }
                    outbound += outboundFlightNumber;
                    String price = list.get(i).get("outbound").get("price");
                    String buy = list.get(i).get("outbound").get("buy");
                    String inbound = "";
                    String inboundFlightNumber = "";
                    if(list.get(i).get("inbound") != null){
                        inboundFlightNumber = "flightNumber: " +  list.get(i).get("inbound").get("carrierCode") + list.get(i).get("outbound").get("flightNumber");
                        for(String key : list.get(i).get("inbound").keySet()){
                            inbound += key.equals("price") || key.equals("buy") || key.equals("carrierCode") || key.equals("flightNumber") ? "" : key + ": " + list.get(i).get("inbound").get(key) + "\n";
                        }
                        inbound += inboundFlightNumber;
                    }
                    System.out.printf("%d.\nOutbound:\n%s\n\n",i+1,outbound);
                    System.out.println(inbound.equals("") ? String.format("buy: %s\nprice: $%s\n\n",buy,price)  : String.format("Inbound:\n%s\n\nbuy: %s\nprice: $%s\n\n",inbound,buy,price));

                });
    }

    public void delete(int index) {
        bookingsData.delete(index);
    }

    public void add(Map<String, Map<String, String>> flight) {
        bookingsData.add(flight);
    }

    public List<Map<String, Map<String, String>>> getAll() {
        return bookingsData.getAll();
    }

    public Map<String, Map<String, String>> get(int index) {
        return bookingsData.get(index);
    }

    public static BookingsService setBookingsData(UserData actualUser) {
        bookingsData = BookingsDaoImpl.instance().setActualUser(actualUser);
        return BookingsService.instance();
    }




}
