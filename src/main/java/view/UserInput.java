package view;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class UserInput {

    private Scanner read = new Scanner(System.in);

    public String get0() {
        System.out.println("\nTo show main menu enter 0");
        String input = read.nextLine();
        while (!input.equals("0")) {
            System.out.println("You have to enter 0 to get back to main menu. Try again:");
            input = read.nextLine();
        }

        return input;
    }

    public String getCountry(String type) {
        System.out.printf("\nEnter %s country:\n", type);
        String country = read.nextLine();
        return validateCountry(country);
    }

    public String getCity(String type) {
        System.out.printf("\nEnter %s city:\n", type);
        String city = read.nextLine();
        return validateCity(city);
    }

    public String getDate(String type) {
        System.out.printf("\nEnter %s date:\n", type);
        String date = read.nextLine();
        return validateDate(date);
    }

    public String getAdultsNumber() {
        System.out.println("\nEnter a number of adult passengers:");
        String adultsNumber = read.nextLine();
        return validateAdultsNumber(adultsNumber);
    }

    public int getBookedFlightIndex(int listSize) {
        System.out.println("\nEnter index of flight to cancel booking or type 0 to get back to main menu:");
        String index = read.nextLine();
        return validateBookedFlightIndex(index, listSize);
    }

    public String getYesOrNo(String message) {
        System.out.println("\n" + message);
        String input = read.nextLine();
        return validateYesNoInput(input);
    }

    public int getFlightToBookIndex(int listSize) {
        System.out.println("\nEnter index of flight to book or type 0 to get back to main menu:");
        String input = read.nextLine();
        return validateFlightToBookInput(input, listSize);
    }

    public int getAirportIndex(int listSize) {
        System.out.println("\n\nEnter airport index:");
        String input = read.nextLine();
        return validateAirportIndex(input, listSize);
    }

    public String getDepartureOrArrival() {
        System.out.println("\nChoose departure(d) or arrival(a):");
        String input = read.nextLine();
        return validateDepartureOrArrivalInput(input);
    }

    public String getCabinClass() {
        System.out.println("\nChoose cabin class(economy, premiumeconomy, business, first)");
        String cabinClass = read.nextLine();
        return validateCabinClass(cabinClass);
    }

    public String getMenuItemIndex(boolean user) {
        System.out.println("\nChoose menu item by index:");
        String itemIndex = read.nextLine();
        return validateMenuItemInput(itemIndex, user);
    }


    public String validateCountry(String country) {

        String countryFinal = country.length() > 1 ? country.substring(0, 1).toUpperCase() + country.substring(1) : "";


        Map<String, String> countries = new HashMap<String, String>();

        for (String iso : Locale.getISOCountries()) {
            countries.put(new Locale("en", iso).getDisplayCountry(new Locale("en", iso)), iso);
        }

        boolean check = countries.keySet().stream().anyMatch(e  -> e.equals(countryFinal));

        if (!check) {
            System.out.println("No country found, try again:");
        } else {
            return countryFinal;
        }

        String nextTry = read.nextLine();
        return validateCountry(nextTry);
    }


    public String validateCity(String city) {
        boolean check = city.matches("^[A-Za-z]+$");

        if (!check) {
            System.out.println("City name must be one word containing word charachters only. Tip: for city like New York you can use NY. Try again:");
        } else {
            return city;
        }

        String nextTry = read.nextLine();

        return validateCity(nextTry);
    }


    public String validateDate(String date) {
        if (date.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            return date;
        } else {
            System.out.println("Invalid date format, must be yyyy-dd-mm. Try again:");
            return validateDate(read.nextLine());
        }
    }

    public String validateAdultsNumber(String adultsNumber) {
        if (adultsNumber.matches("^[1-9]$|^0[1-9]$|^1[0-9]$|^20$")) {
            return adultsNumber;
        } else {
            System.out.println("Adults number must be in range 1-20. Try again:");
            String input = read.nextLine();
            return validateAdultsNumber(input);
        }
    }

    public int validateBookedFlightIndex(String index, int size) {
        if (index.matches("\\d+") && Integer.parseInt(index) <= size && Integer.parseInt(index) > 0) {
            return Integer.parseInt(index) - 1;
        } else {
            if (index.equals("0")) return 0;
            System.out.println("Please, choose correct index:");
            String input = read.nextLine();
            return validateBookedFlightIndex(input, size);
        }
    }

    public String validateYesNoInput(String input) {
        if (input.equals("yes") || input.equals("no")) {
            return input;
        } else {
            System.out.println("You have to enter yes or no, try again:");
            input = read.nextLine();
            return validateYesNoInput(input);
        }
    }


    public int validateFlightToBookInput(String userInput, int listSize) {
        if (userInput.matches("\\d+") && Integer.parseInt(userInput) >= 1 && Integer.parseInt(userInput) <= listSize) return Integer.parseInt(userInput) - 1;
        if (userInput.equals("0")) return 0;
        System.out.println("Input must be a valid flight index, try again:");
        String newInput = read.nextLine();
        return validateFlightToBookInput(newInput, listSize);
    }


    public int validateAirportIndex(String input, int listSize) {
        if (input.matches("\\d+") && Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= listSize) return Integer.parseInt(input) - 1;
        System.out.println("Input must be valid airport index, try again:");
        String newInput = read.nextLine();
        return validateAirportIndex(newInput, listSize);
    }

    public String validateDepartureOrArrivalInput(String userInput) {
        if (userInput.equals("d")) return "departures";
        if (userInput.equals("a")) return "arrivals";
        System.out.println("You have to enter d (departure) or a (arrival), try again:");
        String newInput = read.nextLine();
        return validateDepartureOrArrivalInput(newInput);
    }

    public String validateCabinClass(String userInput) {
        if (userInput.equals("economy") || userInput.equals("premiumeconomy") || userInput.equals("business") || userInput.equals("first")) {
            return userInput;
        }
        System.out.println("You have to choose valid cabin class (economy, premiumeconomy, business, first). Try again:");
        String newInput = read.nextLine();
        return validateCabinClass(newInput);
    }

    public String validateMenuItemInput(String input, boolean user) {
        String regexChecker = user ? "[1-6]" : "[1-3]";
        while (!input.matches(regexChecker)) {
            System.out.println("Wrong menu index, try again:");
            input = read.nextLine();
        }

        return input;
    }
}
