package sample.model;

import java.util.ArrayList;

public class PlayerManagement {
    ArrayList<String> players;

    public PlayerManagement() {
        players = new ArrayList<>();
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }


    public void addPlayer(String newPlayer) {
        players.add(newPlayer);
    }

    public void removePlayer(String removePlayer) {
        players.remove(removePlayer);
    }

    public int getAmountPlayers() {
        return players.size();
    }
}
