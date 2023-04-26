module app.weatherwise {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens app.weatherwise to javafx.fxml;
    exports app.weatherwise;
}