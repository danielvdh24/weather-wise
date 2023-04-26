package app.weatherwise;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class Controller {

    private final String weatherAPI = "https://api.openweathermap.org/data/2.5/weather?"; // API call url
    private final String latParam = "lat="; // parameter to specify latitude
    private final String lonParam = "lon="; // parameter to specify longitude
    private final String keyParam = "appid="; // parameter to specify API key
    private final String and = "&"; // syntax for adding parameters

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
        APIService service = new APIService();
        String url = weatherAPI + latParam + latitudeInput.getText() + and + lonParam + longitudeInput.getText() + and + keyParam + apiKey;
        JSONObject weatherData = service.getJSONObject(url);
        output.setText(weatherData.toJSONString());
    }
}