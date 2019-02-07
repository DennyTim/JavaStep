package model.bookings.controller;

import auth.UserData;
import logging.Logger;
import model.bookings.service.BookingsService;
import model.dto.FlightOffer;

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

    public void add(FlightOffer flight) {
        bookingsService.add(flight);
        Logger.info("Booking: Add new flight");
    }

    public List<FlightOffer> getAll() {
        return bookingsService.getAll();
    }

    public FlightOffer get(int index) {
        return bookingsService.get(index);
    }

    public void setBookingsData (UserData actualUser) {
        bookingsService = BookingsService.setBookingsData(actualUser);
        Logger.info("Booking: New actual user");
    }
}