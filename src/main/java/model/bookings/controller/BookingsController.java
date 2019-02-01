package model.bookings.controller;

import auth.UserData;
import logging.Logger;
import model.bookings.service.BookingsService;

import java.util.List;
import java.util.Map;

public class BookingsController {
    private BookingsService bookingsService;

    public void displayBookedFlights(){
        bookingsService.displayBookedFlights();
        Logger.info("Booking: Display booked flights");
    }

    public void delete(int index) {
        bookingsService.delete(index);
        Logger.info("Booking: Delete booking ");
    }

    public void add(Map<String, Map<String, String>> flight) {
        bookingsService.add(flight);
        Logger.info("Booking: Add new flight");
    }

    public List<Map<String, Map<String, String>>> getAll() {
        return bookingsService.getAll();
    }

    public Map<String, Map<String, String>> get(int index) {
        return bookingsService.get(index);
    }

    public void setBookingsData (UserData actualUser) {
        bookingsService = BookingsService.setBookingsData(actualUser);
        Logger.info("Booking: New actual user");
    }
}
