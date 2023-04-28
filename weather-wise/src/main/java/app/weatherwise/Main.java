package app.weatherwise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 350);
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/app/weatherwise/images/weatherwise-icon.png")).toString());
        stage.getIcons().add(icon);
        stage.setTitle("Weather Wise");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("US"));
        launch();
    }
}