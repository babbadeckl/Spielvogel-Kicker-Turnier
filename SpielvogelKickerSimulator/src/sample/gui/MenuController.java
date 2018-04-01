package sample.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.PlayerManagement;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    public javafx.scene.control.TextField textfield_newPlayer;  //textfield for the new player function
    public ListView<String> list_players; //ListView of all currently active players
    private ObservableList<String> items; //needed to keep the list dynamically updated
    public AnchorPane top_parent;
    PlayerManagement playerManagement; //model

    /**
     * Adds a player to the list based on the textfield in the menu.
     *
     * @param actionEvent User presses the green + button
     */
    public void addPlayer(ActionEvent actionEvent) {
        String newPlayer = textfield_newPlayer.getText();
        if (!newPlayer.equals("") && !newPlayer.equals(" ")) {
            playerManagement.addPlayer(newPlayer);
            textfield_newPlayer.setText("");
            items.add(newPlayer);
            updateList();
        }
    }

    /**
     * Removes the currently selected/highlighted player from the list.
     *
     * @param actionEvent User presses the red x button
     */
    public void removePlayer(ActionEvent actionEvent) {
        String removePlayer = list_players.getSelectionModel().getSelectedItem();
        playerManagement.removePlayer(removePlayer);
        items.remove(removePlayer);
        updateList();
    }

    /**
     * Starts the team drafting and starts the game.
     *
     * @param actionEvent User presses the play button.
     */
    public void play(ActionEvent actionEvent) throws IOException {
        int amountPlayers = playerManagement.getAmountPlayers();
        if ((amountPlayers % 2) != 0 || amountPlayers == 0) {  //Ungerade Anzahl Spieler
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Spieleranzahl passt nicht!");
            alert.setHeaderText("Zu wenig Spieler!");
            alert.showAndWait();
        } else if ((amountPlayers < 4)) { //nur 1 Team
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zu wenig Teams");
            alert.setHeaderText("Ihr braucht mehr Teams um ein Turnier starten zu können");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent gamePage = loader.load();
            GameController controller = loader.getController();
            ArrayList<String> players = playerManagement.getPlayers();
            controller.initData(players);
            Scene scene = new Scene(gamePage);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
    }

    /**
     * Updates the ListView whenever a player is added or is removed.
     */
    private void updateList() {
        list_players.setItems(items);
    }


    /**
     * initializes the controller with the most important variables + model.
     *
     * @param location  //
     * @param resources //
     */
    public void initialize(URL location, ResourceBundle resources) {
        playerManagement = new PlayerManagement();
        items = FXCollections.observableArrayList();
        top_parent.getStylesheets().add("fxml/menu.css");
    }


    /**
     * Adds or removes a player based on the checkbox
     *
     * @param actionEvent Checkbox toggled.
     */
    public void toggleCheckBox(ActionEvent actionEvent) {
        CheckBox player = (CheckBox) actionEvent.getSource();
        String name = player.getText();
        if (player.isSelected()) {
            playerManagement.addPlayer(name);
            items.add(name);
        } else {
            playerManagement.removePlayer(name);
            items.remove(name);
        }
        updateList();
    }
}
