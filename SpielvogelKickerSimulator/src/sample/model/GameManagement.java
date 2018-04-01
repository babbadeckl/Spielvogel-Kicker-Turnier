package sample.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class GameManagement {
    private ArrayList<String> players;
    private ArrayList<Team> teams;
    private ArrayList<Pair<Team, Team>> matches;


    /**
     * Constructor for the gamemanagement
     *
     * @param players Shuffles the players and initializes the teams
     */
    public GameManagement(ArrayList<String> players) {
        this.players = players;
        teams = new ArrayList<>();
        matches = new ArrayList<>();
        Collections.shuffle(this.players);
        createTeams();
        createMatches();
    }

    /**
     * Creates the teams. Called within constructor
     */
    private void createTeams() {
        for (int i = 0; i < players.size(); i = i + 2) {
            String player1 = players.get(i);
            String player2 = players.get(i + 1);
            Team team = new Team(player1, player2);
            teams.add(team);
        }
    }

    /**
     * Fill the matches list with all possible matches derived from the teams.
     */
    private void createMatches() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i; j < teams.size(); j++) {
                if (i != j) {
                    matches.add(new Pair<>(teams.get(i), teams.get(j)));
                }
            }
        }
        Collections.shuffle(matches);
    }

    /**
     * Calculates the winner and loser if the scores get entered
     * @param namet1 team1 name
     * @param team1score team1 score
     * @param namet2 team 2 name
     * @param team2score team2 score
     */
    public void calcWinner(String namet1, int team1score, String namet2, int team2score) {
        Team team1 = calcTeam(namet1);
        Team team2 = calcTeam(namet2);
        if (team1score < team2score) {
            team2.addWin();
            team1.addLose();
        } else if (team2score < team1score) {
            team1.addWin();
            team2.addLose();
        } else {
            team1.addTie();
            team2.addTie();
        }
    }

    /**
     * Removes the winner and loser if a wrong score was entered and the correct button is triggered
     * @param namet1 team1 name
     * @param team1score team1 score
     * @param namet2 team 2 name
     * @param team2score team2 score
     */
    public void removeWinner(String namet1, int team1score, String namet2, int team2score){
        Team team1 = calcTeam(namet1);
        Team team2 = calcTeam(namet2);
        if(team1 != null || team2 != null) {
            if (team1score < team2score) {
                team2.removeWin();
                team1.removeLose();
            } else if (team2score < team1score) {
                team1.removeWin();
                team2.removeLose();
            } else {
                team1.removeTie();
                team2.removeTie();
            }
        }
    }

    private Team calcTeam(String teamname) {
        for (Team team : teams) {
            if (team.getTeamname().equals(teamname)) {
                return team;
            }
        }
        return new Team("huan", "son");  //this cannot be a case.
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public int getAmountOfTeams() {
        return this.teams.size();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public ArrayList<Pair<Team, Team>> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Pair<Team, Team>> matches) {
        this.matches = matches;
    }


    public void shuffleTeams() {
        Collections.shuffle(matches)
        ;
    }
}
