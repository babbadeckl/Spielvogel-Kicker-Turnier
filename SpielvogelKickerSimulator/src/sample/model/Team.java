package sample.model;

public class Team {
    private static final int WIN_SCORE = 3;
    private static final int TIE_SCORE = 1;
    private static final int NAME_RANGE = 4;

    private String player1;
    private String player2;
    private String teamname;
    private int wins = 0;
    private int lose = 0;
    private int ties = 0;
    private int score = 0;

    public Team(String p1, String p2) {
        player1 = p1;
        player2 = p2;
        if (player1.length() < NAME_RANGE + 1 && player2.length() < NAME_RANGE + 1) {
            teamname = player1 + player2;
        } else if (player1.length() < NAME_RANGE + 1) {
            teamname = player1 + player2.substring(0, NAME_RANGE);
        } else if (player2.length() < NAME_RANGE + 1) {
            teamname = player1.substring(0, NAME_RANGE) + player2;
        } else {
            teamname = player1.substring(0, NAME_RANGE) + player2.substring(0, NAME_RANGE);
        }
    }

    /**
     * This is what gets displayed in the Scoreboard
     */
    @Override
    public String toString() {
        return (teamname + " (Punkte: " + score + " Gewonnen: " + wins + " Verloren: " + lose + " Unentschieden: " + ties + ")");
    }

    public void addWin() {
        this.wins++;
        calcScore();
    }

    public void addLose() {
        this.lose++;
        calcScore();
    }

    public void addTie() {
        this.ties++;
        calcScore();
    }

    public void removeWin() {
        this.wins--;
        calcScore();
    }

    public void removeLose() {
        this.lose--;
        calcScore();
    }

    public void removeTie() {
        this.ties--;
        calcScore();
    }

    private void calcScore() {
        this.score = (WIN_SCORE * wins) + (TIE_SCORE * ties);
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }
}
