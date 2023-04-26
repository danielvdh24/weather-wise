package app.weatherwise;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Controller {

    private final String weatherAPI = "https://api.openweathermap.org/data/2.5/weather?"; // API call url
    private final String latParam = "lat="; // parameter to specify latitude
    private final String lonParam = "lon="; // parameter to specify longitude
    private final String keyParam = "appid="; // parameter to specify API key
    private final String query = "q="; // parameter to specify city name
    private final String and = "&"; // syntax for adding parameters
    private final String noData = "NOT FOUND!"; // null replacement
    private final double noDouble = 404;

    private final String apiKey = "3d3d6e9a8b385dea9f19a7323938d980"; // API key to authenticate call

    @FXML
    private TextField latitudeInput, longitudeInput, cityInput;
    @FXML
    private Label output;
    @FXML
    private ChoiceBox<String> textInputBox;
    @FXML
    private final String[] textInputTypes = {"Geolocation", "City"};

    @FXML
    public void initialize() {
        textInputBox.getItems().addAll(textInputTypes);
        textInputBox.setValue(textInputTypes[0]);
    }

    @FXML
    protected void onGetDataClick() {
        APIService service = APIService.getInstance(); // create or get the APIService instance
        String inputType = textInputBox.getValue();
        String url = weatherAPI;

        if(inputType.equals("Geolocation")){
            String latitude = Util.formatDouble(latitudeInput.getText());
            String longitude = Util.formatDouble(longitudeInput.getText());
            url = url + latParam + latitude + and + lonParam + longitude + and + keyParam + apiKey; // construct URL with geolocation input
            // weatherAPI + lat={lat}&lon={lon}&appid={API key}
        } else {
            String city = Util.formatCity(cityInput.getText());
            url = url + query + city + and + keyParam + apiKey; // construct URL with city input
            // weatherAPI + q={city name}&appid={API key}
        }

        try {
            JSONObject weatherData = service.getJSONObject(url);

            String cityName = (String) weatherData.get("name"); // get city name
            if(cityName.isEmpty() || cityName.isBlank()) cityName = noData;

            JSONArray weatherArray = (JSONArray) weatherData.get("weather");
            JSONObject weatherObject = (JSONObject) weatherArray.get(0);
            String description = weatherObject.get("description") == null ? noData : (String) weatherObject.get("description"); // get description from weather field

            JSONObject mainObject = (JSONObject) weatherData.get("main");
            double rawTemp = mainObject.get("temp") == null ? noDouble : ((Double) mainObject.get("temp")); // get temperature from main field

            JSONObject sysObject = (JSONObject) weatherData.get("sys");
            String country = sysObject.get("country") == null ? noData : (String) sysObject.get("country"); // get country from sys field

            mainObject = (JSONObject) weatherData.get("wind");
            double windSpeed = mainObject.get("speed") == null ? noDouble : (Double) mainObject.get("speed"); // get wind speed from wind field

            country = Util.formatCC(country); // convert country code to country name
            String temp = Util.formatTemp(rawTemp); // convert Kelvin to Celsius
            description = Util.formatDesc(description); // capitalize first letter of each word

            if(inputType.equals("Geolocation")){
                output.setText("Country: " + country + "\n"
                        + "City: " + cityName + "\n"
                        + "Forecast: " + description + "\n"
                        + "Temperature: " + temp + "\n"
                        + "Wind Speed: " + windSpeed + " m/s");
            } else {
                JSONObject coordObject = (JSONObject) weatherData.get("coord");
                double latitude = coordObject.get("lat") == null ? noDouble : (Double) coordObject.get("lat");
                double longitude = coordObject.get("lon") == null ? noDouble : (Double) coordObject.get("lon");
                output.setText("Latitude: " + latitude + "\n"
                        + "Longitude: " + longitude + "\n"
                        + "Forecast: " + description + "\n"
                        + "Temperature: " + temp + "\n"
                        + "Wind Speed: " + windSpeed + " m/s");
            }
        } catch(Exception e){
            output.setText("Unable to retrieve weather data for the specified location. Please try again or check your input.");
        }
    }
}