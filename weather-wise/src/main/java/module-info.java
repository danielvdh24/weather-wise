module app.weatherwise {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.weatherwise to javafx.fxml;
    exports app.weatherwise;
}