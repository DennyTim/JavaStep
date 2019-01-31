package view;

import java.util.*;

public final class Validation {

    private static final Scanner read = new Scanner(System.in);
    public final static String validateAge = "([1-9]){1}[0-9]{1,2}";
    public final static String validateNameSurname = "^[a-zA-Z]+";
    public final static String validateLogin = "[a-zA-Z0-9]{3,}";
    public final static String validatePassword = "[a-zA-Z0-9?(-.@$%)]{6,}";


    private Validation() {}

    public static boolean isValidatePassword(String login){
        return login.matches(validatePassword);
    }

    public static boolean isValidateLogin(String login){
        return login.matches(validateLogin);
    }

    public static boolean checkAge(String age){
        return age.matches(validateAge);
    }

    public static boolean checkNameSurname(String input){
        return input.matches(validateNameSurname);
    }

    public static String validateCountry(String country) {

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


    public static String validateCity(String city) {
        boolean check = city.matches("^[A-Za-z]+$");

        if (!check) {
            System.out.println("City name must be one word containing word charachters only. Tip: for city like New York you can use NY. Try again:");
        } else {
            return city;
        }

        String nextTry = read.nextLine();

        return validateCity(nextTry);
    }


    public static String validateDate(String date) {
        if (date.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            return date;
        } else {
            System.out.println("Invalid date format, must be yyyy-dd-mm. Try again:");
            return validateDate(read.nextLine());
        }
    }

    public static String validateAdultsNumber(String adultsNumber) {
        if (adultsNumber.matches("^[1-9]$|^0[1-9]$|^1[0-9]$|^20$")) {
            return adultsNumber;
        } else {
            System.out.println("Adults number must be in range 1-20. Try again:");
            String input = read.nextLine();
            return validateAdultsNumber(input);
        }
    }

    public static String validateBookedFlightIndex(String index, int size) {
        int integerIndex = Integer.parseInt(index);
        if (!index.matches("d+") && integerIndex <= size && integerIndex > 0) {
            return index;
        } else {
            System.out.println("Please, choose correct index:");
            String input = read.nextLine();
            return validateBookedFlightIndex(input, size);
        }
    }

    public static String validateYesNoInput(String input) {
        if (input.equals("yes") || input.equals("no")) {
            return input;
        } else {
            System.out.println("You have to enter yes or no, try again:");
            input = read.nextLine();
            return validateYesNoInput(input);
        }
    }


    public static int validateFlightToBookInput(int userInput, int listSize) {
        if (String.valueOf(userInput).matches("\\d+") && userInput >= 0 && userInput < listSize) return userInput;
        System.out.println("Input must be a valid flight index, try again:");
        String newInput = read.nextLine();
        return validateFlightToBookInput(Integer.parseInt(newInput) - 1, listSize);
    }


    public static String validateAirportIndex(String input, int listSize) {
        if (input.matches("\\d+") && Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= listSize) return input;
        System.out.println("Input must be valid airport index, try again:");
        String newInput = read.nextLine();
        return validateAirportIndex(newInput, listSize);
    }

    public static String validateDepartureOrArrivalInput(String userInput) {
        if (userInput.equals("d") || userInput.equals("a")) return userInput;
        System.out.println("You have to enter d (departure) or a (arrival), try again:");
        String newInput = read.nextLine();
        return validateDepartureOrArrivalInput(newInput);
    }

    public static String validateCabinClass(String userInput) {
        if (userInput.equals("economy") || userInput.equals("premiumeconomy") || userInput.equals("business") || userInput.equals("first")) {
            return userInput;
        }
        System.out.println("You have to choose valid cabin class (economy, premiumeconomy, business, first). Try again:");
        String newInput = read.nextLine();
        return validateCabinClass(newInput);
    }

    public static String validateMenuItemInput(String input) {
        while (!input.matches("[1-6]")) {
            System.out.println("Wrong menu index, try again:");
            input = read.nextLine();
        }

        return input;
    }

}
