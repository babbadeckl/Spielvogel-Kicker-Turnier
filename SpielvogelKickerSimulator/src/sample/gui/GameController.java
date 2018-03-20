package sample.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import sample.Main;
import sample.model.GameManagement;
import sample.model.Team;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public ScrollPane scroll_pane;
    public ListView<Team> listview_scoreboard;
    public VBox VBOX_matches;
    public AnchorPane top_parent;

    private GameManagement gameManagement;
    private ArrayList matches;
    private ObservableList<Team> teams = FXCollections.observableArrayList();


    private static final int LABEL_TEAM_WIDTH = 180;
    private static final int LABEL_TEAM_HEIGHT = 30;
    private static final int LABEL_SEP_WIDTH = 15;
    private static final int SCORES_HEIGHT = 35;
    private static final int SCORES_WIDTH = 50;
    private static final int PADDING = 50;
    private static final int TEAM_FONT_SIZE = 25;
    private static final int SCORE_FONT_SIZE = 20;

    private static final int AMOUNT_BALLS = 10;

    private static final int POS_FIRST_TEAM_NAME = 0;
    private static final int POS_FIRST_TEAM_SCORE = 1;
    private static final int POS_SECOND_TEAM_NAME = 4;
    private static final int POS_SECOND_TEAM_SCORE = 3;


    /**
     * This is called from the MenuController and inits all data from the menu into the game.
     * It adds a HBOX containing both teamnames and 2 textfields for the scores
     *
     * @param players active players that participate at the kicker game.
     */
    public void initData(ArrayList<String> players) {
        gameManagement = new GameManagement(players);
        this.matches = gameManagement.getMatches();
        for (int i = 0; i < matches.size(); i++) {
            addMatch(i);
        }
        initScoreboard();
    }


    /**
     * Calculates the winner / distributes scores and updates the scoreboard. Is triggered if user enters value into
     * textfield and presses enter
     *
     * @param event Enter press.
     */
    public void scoreEntered(ActionEvent event) {
        HBox parent = (HBox) ((TextField) event.getSource()).getParent();
        TextField firstTeam = (TextField) parent.getChildren().get(POS_FIRST_TEAM_SCORE);
        TextField secondTeam = (TextField) parent.getChildren().get(POS_SECOND_TEAM_SCORE);
        try {
            int scoreTeam1 = Integer.parseInt(firstTeam.getText());
            int scoreTeam2 = Integer.parseInt(secondTeam.getText());
            if (scoreTeam1 + scoreTeam2 == AMOUNT_BALLS) {
                firstTeam.setDisable(true);
                secondTeam.setDisable(true);
                Label first = (Label) parent.getChildren().get(POS_FIRST_TEAM_NAME);
                Label second = (Label) parent.getChildren().get(POS_SECOND_TEAM_NAME);

                gameManagement.calcWinner(first.getText(), scoreTeam1, second.getText(), scoreTeam2);
                updateScoreboard();
            } else {
                Alert wrongAmountBalls = new Alert(Alert.AlertType.ERROR);
                wrongAmountBalls.setTitle("Falsche Anzahl an Toren!");
                wrongAmountBalls.setHeaderText("Die Gesamtanzahl der Tore muss " + AMOUNT_BALLS + " betragen");
                wrongAmountBalls.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert wrongInput = new Alert(Alert.AlertType.ERROR);
            wrongInput.setTitle("Anzahl an Tore konnte nicht ermittelt werden");
            wrongInput.setHeaderText("Bitte nur Zahlen als Torergebnisse eintragen!!");
            wrongInput.showAndWait();
        }
    }

    /**
     * Revises/Corrects a match if values were not entered correctly.
     *
     * @param event Push on the "correct" button
     */
    private void reviseMatch(ActionEvent event) {
        HBox parent = (HBox) ((Button) event.getSource()).getParent();
        TextField firstTeam = (TextField) parent.getChildren().get(POS_FIRST_TEAM_SCORE);
        TextField secondTeam = (TextField) parent.getChildren().get(POS_SECOND_TEAM_SCORE);
        if (firstTeam.isDisabled() && secondTeam.isDisabled()) {
            firstTeam.setDisable(false);
            secondTeam.setDisable(false);
            int scoreTeam1 = Integer.parseInt(firstTeam.getText());
            int scoreTeam2 = Integer.parseInt(secondTeam.getText());
            firstTeam.setText("");
            secondTeam.setText("");
            Label first = (Label) parent.getChildren().get(POS_FIRST_TEAM_NAME);
            Label second = (Label) parent.getChildren().get(POS_SECOND_TEAM_NAME);
            gameManagement.removeWinner(first.getText(), scoreTeam1, second.getText(), scoreTeam2);
            updateScoreboard();
        }
    }

    /**
     * Updates the scoreboard when new scores were entered.
     */
    private void updateScoreboard() {
        teams.clear();
        teams.addAll(gameManagement.getTeams());

    }

    /**
     * not needed here. Everything done by the initData method.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    /**
     * Inits the scoreboard by setting up the teams and sorting them based on their score. (Ranking)
     */
    private void initScoreboard() {
        listview_scoreboard.setStyle("-fx-font-size: 14");
        teams.addAll(gameManagement.getTeams());
        SortedList<Team> sortedList = new SortedList<>(teams);
        sortedList.setComparator((Team o1, Team o2) -> {
                    if (o1.getScore() < o2.getScore()) {
                        return 1;
                    } else if (o1.getScore() > o2.getScore()) {
                        return -1;
                    } else if (o1.getScore() == o2.getScore()) {
                        return 0;
                    }
                    return 0;
                }
        );
        listview_scoreboard.setItems(sortedList);
    }

    /**
     * Adds new matches into the VBox. These matches are HBoxes that contain teamnames, scores and the correct button.
     *
     * @param i just a counter from the for loop in initData to add all possible matches.
     */
    private void addMatch(int i) {
        String team1 = ((Team) ((Pair) matches.get(i)).getKey()).getTeamname();
        String team2 = ((Team) ((Pair) matches.get(i)).getValue()).getTeamname();

        //Hbox Init
        HBox box = new HBox();
        box.setId("hbox");
        box.setSpacing(20);

        //Name of the first team.
        Label first = new Label(team1);
        first.setMinSize(LABEL_TEAM_WIDTH, LABEL_TEAM_HEIGHT);
        first.setMaxSize(LABEL_TEAM_WIDTH, LABEL_TEAM_HEIGHT);
        first.setPadding(new Insets(0, 0, 0, PADDING));
        first.setFont(Font.font("Bold", TEAM_FONT_SIZE));
        first.setTextAlignment(TextAlignment.CENTER);

        //Name of the second team.
        Label second = new Label(team2);
        second.setMinSize(LABEL_TEAM_WIDTH, LABEL_TEAM_HEIGHT);
        second.setMaxSize(LABEL_TEAM_WIDTH, LABEL_TEAM_HEIGHT);
        second.setPadding(new Insets(0, 0, 0, PADDING));
        second.setTextAlignment(TextAlignment.CENTER);
        second.setFont(Font.font("Bold", TEAM_FONT_SIZE));

        // ":" sign between the scores
        Label separator = new Label(":");
        separator.setMinSize(LABEL_SEP_WIDTH, SCORES_HEIGHT);
        separator.setMaxSize(LABEL_SEP_WIDTH, SCORES_HEIGHT);
        separator.setFont(Font.font("Bold", TEAM_FONT_SIZE));

        //Score of team1 - textfield
        TextField scoreTeam1 = new TextField();
        scoreTeam1.setId("team1");
        scoreTeam1.setMinSize(SCORES_WIDTH, SCORES_HEIGHT);
        scoreTeam1.setMaxSize(SCORES_WIDTH, SCORES_HEIGHT);
        scoreTeam1.setFont(Font.font(SCORE_FONT_SIZE));
        scoreTeam1.setAlignment(Pos.CENTER);
        scoreTeam1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoreEntered(event);
            }
        });

        //Score of team2 - textfield
        TextField scoreTeam2 = new TextField();
        scoreTeam2.setId("team2");
        scoreTeam2.setMinSize(SCORES_WIDTH, SCORES_HEIGHT);
        scoreTeam2.setMaxSize(SCORES_WIDTH, SCORES_HEIGHT);
        scoreTeam2.setFont(Font.font(SCORE_FONT_SIZE));
        scoreTeam2.setAlignment(Pos.CENTER);
        scoreTeam2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoreEntered(event);
            }
        });

        //revise/correct button
        Button revise = new Button("Korrigieren");
        revise.setPrefSize(60, 30);
        revise.setMinSize(60, 30);
        revise.setPadding(new Insets(0, 0, 0, 0));
        revise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reviseMatch(event);
            }
        });

        //hbox adds everything together
        box.getChildren().add(first);
        box.getChildren().add(scoreTeam1);
        box.getChildren().add(separator);
        box.getChildren().add(scoreTeam2);
        box.getChildren().add(second);
        box.getChildren().add(revise);

        VBOX_matches.getChildren().add(box);
    }

    /**
     * Quick n Dirty method for restarting the program. (Enabling the feature of "new tournament")
     * @param event not used
     * @throws Exception app.start() throws exception
     */
    public void restartApp(ActionEvent event) throws Exception {
        Stage main = (Stage)top_parent.getScene().getWindow();
        main.close();
        Main app = new Main();
        app.start(new Stage());
    }
}

