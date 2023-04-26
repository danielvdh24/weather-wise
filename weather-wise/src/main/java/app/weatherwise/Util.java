package app.weatherwise;

import java.util.Locale;

public class Util {

    // conversion factor to convert Kelvin to Celsius.
    private static final double KELVIN_TO_CELSIUS = 273.15;

    // returns the full country code name
    public static String formatCC(String input){
        Locale country = new Locale("", input);
        return country.getDisplayCountry();
    }

    // returns the conversion of Kelvin to Celsius
    public static String formatTemp(double input){
        input = input - KELVIN_TO_CELSIUS;
        return String.format("%.2f°C", input);
    }

    // returns a String with the first letter of each word capitalized
    public static String formatDesc(String input) {
        if(input.isEmpty() || input.isBlank()){
            return "not found!";
        }
        StringBuilder output = new StringBuilder();
        String[] words = input.split("\\s+");
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)));
            output.append(word.substring(1));
            output.append(" ");
        }
        return output.toString().trim();
    }

    // returns a String with all illegal characters (that is not: a number from 0-9, a "-", or a ".") removed
    public static String formatDouble(String input){
        return input.replaceAll("[^0-9\\-.]", "");
    }

    // returns a String with all spaces replaced by %20 (url encoding for empty space) and illegal characters (that is not: a letter) removed
    public static String formatCity(String input){
        input = input.trim().replaceAll("[^a-zA-Z ]+", "");
        return input.replaceAll(" ", "%20");
    }
}
