package model;


import ui.GameInstance;
import org.json.JSONArray;
import org.json.JSONObject;

// stats of player
public class PlayerStat {

    int hp;
    int highestPossibleScore;
    int score;

    public PlayerStat() {

        this.hp = 0;
        EventLog.getInstance().logEvent(new Event("PlayerStat created"));
    }



    //REQUIRES: difficulty to be passed in to be of range 1 to 4 (inclusive) and an integer
    //EFFECTS: returns the initial HP of the game.
    public void calculateHP(int numUniqueChars, int difficulty) {
        hp = numUniqueChars * (5 - difficulty) / 6 + 1;
    }


    // REQUIRES: totalGuesses > 0
    // MODIFIES: this
    // EFFECTS: calculates the player's score
    public void calculateScore(int numUniqueChars, int totalGuesses) {
        score = ((numUniqueChars * 100) / totalGuesses);

    }

    // REQUIRES: totalGuesses >= 0
    // MODIFIES: this
    // EFFECTS: calculates the player's score
    public void calculateHighestPossibleScore(int numUniqueChars, int totalGuesses) {
        highestPossibleScore = ((numUniqueChars * 100) / (totalGuesses + 1));

    }

    // EFFECTS: converts the player stats to a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hp", hp);
        json.put("highestPossibleScore", highestPossibleScore);
        json.put("score", score);
        return json;
    }

    public int getScore() {
        return score;
    }

    public int getHighestPossibleScore() {
        return highestPossibleScore;
    }

    public int getHP() {
        return hp;

    }

    // MODIFIES: this
    // EFFECTS: increments the player's HP by delta
    public void deltaHP(int delta) {
        hp += delta;
        EventLog.getInstance().logEvent(new Event("Player HP changed by: " + delta + ", new HP: " + hp));
    }

    public void setHP(int hp) {
        this.hp = hp;

    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHighestPossibleScore(int highestPossibleScore) {
        this.highestPossibleScore = highestPossibleScore;
    }
}
