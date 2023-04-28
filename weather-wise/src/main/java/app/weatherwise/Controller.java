package app.weatherwise;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Controller {

    private final String weatherAPI = "https://api.openweathermap.org/data/2.5/weather?"; // API call url
    private final String iconURL = "https://openweathermap.org/img/wn/";   // url for accessing icons
    private final String imageSize = "@4x.png"; // parameter to configure icon size and image type
    private final String latParam = "lat="; // parameter to specify latitude
    private final String lonParam = "lon="; // parameter to specify longitude
    private final String keyParam = "appid="; // parameter to specify API key
    private final String query = "q="; // parameter to specify city name
    private final String and = "&"; // syntax for adding parameters
    private final String noData = "NOT FOUND!"; // null replacement

    private final String apiKey = "3d3d6e9a8b385dea9f19a7323938d980"; // API key to authenticate call
    private APIService service; // instantiate APIService object

    @FXML
    private RadioButton selectCity, selectGeolocation;
    @FXML
    private Label latitudeText, longitudeText, cityText, output;
    @FXML
    private TextField latitudeInput, longitudeInput, cityInput;
    @FXML
    private Button button;
    @FXML
    private ImageView weatherIcon;

    @FXML
    private void initialize() {
        service = APIService.getInstance(); // create or get the APIService instance
        selectCity.setSelected(true);
        latitudeText.setVisible(false);
        longitudeText.setVisible(false);
        latitudeInput.setVisible(false);
        longitudeInput.setVisible(false);
        button.setDefaultButton(true);
    }

    public void switchInputMode(){
        if(selectCity.isSelected()){
            output.setText("");
            cityText.setVisible(true);
            cityInput.setVisible(true);
            latitudeText.setVisible(false);
            longitudeText.setVisible(false);
            latitudeInput.setVisible(false);
            longitudeInput.setVisible(false);
            weatherIcon.setImage(null);
        } else {
            output.setText("");
            cityText.setVisible(false);
            cityInput.setVisible(false);
            latitudeText.setVisible(true);
            longitudeText.setVisible(true);
            latitudeInput.setVisible(true);
            longitudeInput.setVisible(true);
            weatherIcon.setImage(null);
        }
    }

    @FXML
    protected void onButtonClick() {
        String url = weatherAPI;

        if(selectCity.isSelected()){
            String city = Util.formatCity(cityInput.getText());
            if(city.equals("")){
                output.setText("Please enter your city name!");
                return;
            }
            url = url + query + city + and + keyParam + apiKey; // construct URL with city input
            // weatherAPI + q={city name}&appid={API key}
        }

        if(selectGeolocation.isSelected()){
            String latitude = Util.formatDouble(latitudeInput.getText());
            String longitude = Util.formatDouble(longitudeInput.getText());
            if(latitude.equals("") || longitude.equals("")){
                output.setText("Please enter your coordinates!");
                return;
            }
            if(Double.parseDouble(latitude) > 90 || Double.parseDouble(latitude) < -90){
                output.setText("Latitude value is invalid!" + "\n"
                        + "Please enter a value between -90 and 90");
                return;
            }
            if(Double.parseDouble(longitude) > 180 || Double.parseDouble(longitude) < -180){
                output.setText("Longitude value is invalid!" + "\n"
                        + "Please enter a value between -180 and 180");
                return;
            }
            url = url + latParam + latitude + and + lonParam + longitude + and + keyParam + apiKey; // construct URL with geolocation input
            // weatherAPI + lat={lat}&lon={lon}&appid={API key}
        }

        try {
            JSONObject weatherData = service.getJSONObject(url);

            String cityName = (String) weatherData.get("name"); // get city name
            if(cityName.isEmpty() || cityName.isBlank()) cityName = noData;

            JSONArray weatherArray = (JSONArray) weatherData.get("weather");
            JSONObject weatherObject = (JSONObject) weatherArray.get(0);
            String description = weatherObject.get("description") == null ? noData : (String) weatherObject.get("description"); // get description from weather field
            String iconID = (String) weatherObject.get("icon");
            weatherIcon.setImage(new Image(iconURL + iconID + imageSize));

            JSONObject mainObject = (JSONObject) weatherData.get("main");
            double rawTemp = (Double) mainObject.get("temp"); // get temperature from main field

            JSONObject sysObject = (JSONObject) weatherData.get("sys");
            String country = sysObject.get("country") == null ? noData : (String) sysObject.get("country"); // get country from sys field

            mainObject = (JSONObject) weatherData.get("wind");
            double windSpeed = (Double) mainObject.get("speed"); // get wind speed from wind field

            country = Util.formatCC(country); // convert country code to country name
            String temp = Util.formatTemp(rawTemp); // convert Kelvin to Celsius
            description = Util.formatDesc(description); // capitalize first letter of each word

            if(selectGeolocation.isSelected()){
                output.setText("Country: " + country + "\n"
                        + "City: " + cityName + "\n"
                        + "Forecast: " + description + "\n"
                        + "Temperature: " + temp + "\n"
                        + "Wind Speed: " + windSpeed + " m/s");
            } else {
                JSONObject coordObject = (JSONObject) weatherData.get("coord");
                String latitude = Util.parseCoords(coordObject.get("lat"));
                String longitude = Util.parseCoords(coordObject.get("lon"));
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