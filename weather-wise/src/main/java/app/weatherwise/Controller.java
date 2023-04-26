package app.weatherwise;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Controller {

    private final String weatherAPI = "https://api.openweathermap.org/data/2.5/weather?"; // API call url
    private final String latParam = "lat="; // parameter to specify latitude
    private final String lonParam = "lon="; // parameter to specify longitude
    private final String keyParam = "appid="; // parameter to specify API key
    private final String and = "&"; // syntax for adding parameters
    private final String noData = "NOT FOUND!"; // null replacement

    private final String apiKey = "3d3d6e9a8b385dea9f19a7323938d980"; // API key to authenticate call

    @FXML
    public TextField latitudeInput;
    @FXML
    public TextField longitudeInput;
    @FXML
    public Label latitudeText;
    @FXML
    public Label longitudeText;
    @FXML
    private Label output;

    @FXML
    protected void onGetDataClick() {
        APIService service = APIService.getInstance(); // create or get the APIService instance
        String latitude = Util.removeIllegalCharacters(latitudeInput.getText());
        String longitude = Util.removeIllegalCharacters(longitudeInput.getText());
        String url = weatherAPI + latParam + latitude + and + lonParam + longitude + and + keyParam + apiKey; // construct URL with input
        try {
            JSONObject weatherData = service.getJSONObject(url);
            String cityName = (String) weatherData.get("name"); // get city name
            if(cityName.isEmpty() || cityName.isBlank()) cityName = noData;

            JSONArray weatherArray = (JSONArray) weatherData.get("weather");
            JSONObject weatherObject = (JSONObject) weatherArray.get(0);
            String description = weatherObject.get("description") == null ? noData : (String) weatherObject.get("description"); // get description from weather field

            JSONObject mainObject = (JSONObject) weatherData.get("main");
            double rawTemp = mainObject.get("temp") == null ? 404 : ((Double) mainObject.get("temp")); // get temperature from main field

            JSONObject sysObject = (JSONObject) weatherData.get("sys");
            String country = sysObject.get("country") == null ? noData : (String) sysObject.get("country"); // get country from sys field

            mainObject = (JSONObject) weatherData.get("wind");
            double windSpeed = mainObject.get("speed") == null ? 404 : (Double) mainObject.get("speed"); // get wind speed from wind field

            country = Util.formatCC(country); // convert country code to country name
            String temp = Util.formatTemp(rawTemp); // convert Kelvin to Celsius
            description = Util.formatDesc(description); // capitalize first letter of each word

            output.setText("Country: " + country + "\n"
                    + "City: " + cityName + "\n"
                    + "Forecast: " + description + "\n"
                    + "Temperature: " + temp + "\n"
                    + "Wind Speed: " + windSpeed + " m/s");
        } catch(Exception e){
            output.setText("Unable to retrieve weather data for the specified location. Please try again or check your input.");
        }
    }
}