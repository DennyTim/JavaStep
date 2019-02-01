package view;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationTest {

    @Test
    public void passwordShouldBeValid() {
        //given
        String password = "pewlnfl754";
        //when
        boolean check = Validation.isValidPassword(password);
        //then
        assertTrue(check);
    }

    @Test
    public void passwordShouldBeInvalid() {
        //given
        String password = "jfg ";
        //when
        boolean check = Validation.isValidPassword(password);
        //then
        assertFalse(check);
    }

    @Test
    public void loginShouldBeValid() {
        //given
        String login = "maximus";
        //when
        boolean check = Validation.isValidLogin(login);
        //then
        assertTrue(check);
    }

    @Test
    public void loginShouldBeInvalid() {
        //given
        String login = "mfk  ./";
        //when
        boolean check = Validation.isValidLogin(login);
        //then
        assertFalse(check);
    }

    @Test
    public void ageShouldBeValid() {
        //given
        String age = "20";
        //when
        boolean check = Validation.checkAge(age);
        //then
        assertTrue(check);
    }

    @Test
    public void nameShouldBeValid() {
        //given
        String name = "kolia";
        //when
        boolean check = Validation.checkNameSurname(name);
        //then
        assertTrue(check);
    }

    @Test
    public void nameShouldBeInvalid() {
        //given
        String name = "kolia   .//././";
        //when
        boolean check = Validation.checkNameSurname(name);
        //then
        assertFalse(check);
    }


    @Test
    public void countryShouldBeValid() {
        //given
        String country = "Ukraine";
        //when
        String checkedCountry = Validation.validateCountry(country);
        //then
        assertEquals(country, checkedCountry);
    }


    @Test
    public void dateShouldBeValid() {
        //given
        String date = "2019-02-10";
        //when
        String checkedDate = Validation.validateDate(date);
        //then
        assertEquals(date, checkedDate);
    }

    @Test
    public void adultsNumberShouldBeValid() {
        //given
        String adultsNumber = "2";
        //when
        String checkedAdultsNumber = Validation.validateAdultsNumber(adultsNumber);
        //then
        assertEquals(adultsNumber, checkedAdultsNumber);
    }

    @Test
    public void bookedFlightIndexShouldBeValid() {
        //given
        String index = "2";
        int bookedFlightsListSize = 5;
        //when
        String checkedIndex = Validation.validateBookedFlightIndex(index, bookedFlightsListSize);
        //then
        assertEquals(index, checkedIndex);

    }

    @Test
    public void yesNoInputShouldBeValid() {
        //given
        String input = "yes";
        //when
        String checkedInput = Validation.validateYesNoInput(input);
        //then
        assertEquals(input, checkedInput);
    }

    @Test
    public void FlightToBookInputShouldBeValid() {
        //given
        int userInput = 2;
        int flightsToBookListSize = 5;
        //when
        int checkedUserInput = Validation.validateFlightToBookInput(userInput, flightsToBookListSize);
        //then
        assertEquals(userInput, checkedUserInput);

    }

    @Test
    public void AirportIndexShouldBeValid() {
        //given
        String index = "2";
        int airportListSize = 5;
        //when
        String checkedIndex = Validation.validateAirportIndex(index, airportListSize);
        //then
        assertEquals(index, checkedIndex);
    }

    @Test
    public void DepartureOrArrivalIndexShouldBeValid() {
        //given
        String departureOrArrival = "d";
        //when
        String checkedDepartureOrArrival = Validation.validateDepartureOrArrivalInput(departureOrArrival);
        //then
        assertEquals(departureOrArrival, checkedDepartureOrArrival);
    }

    @Test
    public void CabinClassShouldBeValid() {
        //given
        String cabinClass = "economy";
        //when
        String checkedCabinClass = Validation.validateCabinClass(cabinClass);
        //then
        assertEquals(cabinClass, checkedCabinClass);
    }

    @Test
    public void MenuItemInputShouldBeValid() {
        //given
        String menuItemInput = "6";
        //when
        String checkedMenuItemInput = Validation.validateMenuItemInput(menuItemInput);
        //then
        assertEquals(menuItemInput, checkedMenuItemInput);
    }
}