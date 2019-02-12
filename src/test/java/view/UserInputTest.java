package view;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserInputTest {

    private UserInput ui = new UserInput();




    @Test
    public void countryShouldBeValid() {
        //given
        String country = "Ukraine";
        //when
        String checkedCountry = ui.validateCountry(country);
        //then
        assertEquals(country, checkedCountry);
    }


    @Test
    public void dateShouldBeValid() {
        //given
        String date = "2019-02-10";
        //when
        String checkedDate = ui.validateDate(date);
        //then
        assertEquals(date, checkedDate);
    }

    @Test
    public void adultsNumberShouldBeValid() {
        //given
        String adultsNumber = "2";
        //when
        String checkedAdultsNumber = ui.validateAdultsNumber(adultsNumber);
        //then
        assertEquals(adultsNumber, checkedAdultsNumber);
    }

    @Test
    public void bookedFlightIndexShouldBeValid() {
        //given
        String index = "2";
        int bookedFlightsListSize = 5;
        //when
        int checkedIndex = ui.validateBookedFlightIndex(index, bookedFlightsListSize);
        //then
        assertEquals(Integer.parseInt(index) - 1, checkedIndex);

    }

    @Test
    public void yesNoInputShouldBeValid() {
        //given
        String input = "yes";
        //when
        String checkedInput = ui.validateYesNoInput(input);
        //then
        assertEquals(input, checkedInput);
    }

    @Test
    public void FlightToBookInputShouldBeValid() {
        //given
        String userInput = "2";
        int flightsToBookListSize = 5;
        //when
        int checkedUserInput = ui.validateFlightToBookInput(userInput, flightsToBookListSize);
        //then
        assertEquals(Integer.parseInt(userInput) - 1, checkedUserInput);

    }

    @Test
    public void AirportIndexShouldBeValid() {
        //given
        String index = "2";
        int airportListSize = 5;
        //when
        int checkedIndex = ui.validateAirportIndex(index, airportListSize);
        //then
        assertEquals(Integer.parseInt(index) - 1, checkedIndex);
    }

    @Test
    public void DepartureOrArrivalIndexShouldBeValid() {
        //given
        String departureOrArrival = "d";
        //when
        String checkedDepartureOrArrival = ui.validateDepartureOrArrivalInput(departureOrArrival);
        //then
        assertEquals("departures", checkedDepartureOrArrival);
    }

    @Test
    public void CabinClassShouldBeValid() {
        //given
        String cabinClass = "economy";
        //when
        String checkedCabinClass = ui.validateCabinClass(cabinClass);
        //then
        assertEquals(cabinClass, checkedCabinClass);
    }

    @Test
    public void MenuItemInputShouldBeValid() {
        //given
        String menuItemInput = "6";
        //when
        String checkedMenuItemInput = ui.validateMenuItemInput(menuItemInput, true);
        //then
        assertEquals(menuItemInput, checkedMenuItemInput);
    }
}