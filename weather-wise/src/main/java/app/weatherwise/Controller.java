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
    private final String noData = "not found!"; // null replacement

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
        String url = weatherAPI + latParam + latitudeInput.getText() + and + lonParam + longitudeInput.getText() + and + keyParam + apiKey; // construct URL with input
        try {
            JSONObject weatherData = service.getJSONObject(url);
            String cityName = (String) weatherData.get("name"); // get city name
            JSONArray weatherArray = (JSONArray) weatherData.get("weather");
            JSONObject weatherObject = (JSONObject) weatherArray.get(0);
            String description = (String) weatherObject.get("description"); // get description from weather field
            JSONObject mainObject = (JSONObject) weatherData.get("main");
            double rawTemp = ((Double) mainObject.get("temp")); // get temperature from main field
            JSONObject sysObject = (JSONObject) weatherData.get("sys");
            String country = (String) sysObject.get("country"); // get country from sys field

            country = Util.formatCC(country); // convert country code to country name
            String temp = Util.formatTemp(rawTemp); // convert Kelvin to Celsius
            description = Util.formatDesc(description); // capitalize first letter of each word
            if(cityName.isBlank() || cityName.isEmpty()){
                cityName = noData;
            }
            if(country.isBlank() || country.isEmpty()){
                cityName = noData;
            }

            output.setText("Country: " + country + "\nCity: " + cityName + "\nForecast: " + description + "\nTemperature: " + temp);
        } catch(Exception e){
            output.setText("Unable to retrieve weather data for the specified location. Please try again or check your input.");
        }
    }
}