package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class Validation {

    private static final Scanner read = new Scanner(System.in);

    private Validation() {}

    public static String validateCountry(String country) {

        String countryFinal = country.substring(0, 1).toUpperCase() + country.substring(1);


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


}
