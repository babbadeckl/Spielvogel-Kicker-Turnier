package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static final int WIDTH = 1100;
    public static int HEIGHT = 700;

    /**
     * Main function of the whole application. This method gets executed when the program starts.
     * Opens a window containing the mainmenu.
     * @param primaryStage window.
     * @throws Exception launcher module throws an error (kinda unlikely)
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getClassLoader().getResource("fxml/menu.fxml");
        if(url == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Resource not found");
            alert.setHeaderText(null);
            alert.setContentText("Something went terribly wrong");
            alert.showAndWait();
            Platform.exit();
        } else {
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Spielvogel Kicker Turnier");
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();
            root.requestFocus();
        }
    }
}
