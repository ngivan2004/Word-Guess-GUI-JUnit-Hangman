package model;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a word in the game of hangman.

public class Word {
    private String word;
    private String currentStatus;
    private Set<HangmanCharacter> guessedLetters;
    private Integer totalGuesses;


    //MODIFIES: this
    //EFFECTS: initializes fields and constructs the object
    public Word() {
        this.word = "";
        this.currentStatus = "";
        this.guessedLetters = new HashSet<>();
        this.totalGuesses = 0;
        EventLog.getInstance().logEvent(new Event("Word created"));
    }




    //REQUIRES: the input must be one single character
    //MODIFIES: this
    //EFFECTS: given a character, changes the currentStatus (the state of how much the word is guessed) in returns
    //         if the letter guessed was correct

    public boolean guess(HangmanCharacter letter) {
        boolean foundLetter = false;
        StringBuilder statusBuilder = new StringBuilder(this.currentStatus);
        for (int i = 0; i < this.word.length(); i++) {
            HangmanCharacter wordChar = new HangmanCharacter(this.word.charAt(i));
            if (wordChar.toLowerCase() == letter.toLowerCase()) {
                // convert to lowercase for case-insensitivity
                foundLetter = true;
                statusBuilder.setCharAt(i, this.word.charAt(i));
            }
        }
        this.currentStatus = statusBuilder.toString();
        return foundLetter;
    }



// EFFECTS: converts the word to a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("word", this.word);
        json.put("currentStatus", this.currentStatus);

        JSONArray guessedLettersArray = new JSONArray();
        for (HangmanCharacter hc : guessedLetters) {
            guessedLettersArray.put(hc.getCharacter());
        }
        json.put("guessedLetters", guessedLettersArray);

        json.put("totalGuesses", this.totalGuesses);

        return json;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    //EFFECTS: if all letters have been guessed, returns true, else false.
    public boolean isWordGuessed() {
        return this.word.equals(this.currentStatus);
    }

    public Set<HangmanCharacter> getGuessedLetters() {
        return guessedLetters;
    }


    //EFFECTS: add a guessed letter into the list
    //MODIFIES: this
    public void addGuessedLetters(HangmanCharacter c) {
        guessedLetters.add(c);
        EventLog.getInstance().logEvent(new Event("HangmanCharacter added to Word: " + c.getCharacter()));
    }

    //EFFECTS: increments total guesses by i
    //MODIFIES: this
    public void incrementTotalGuesses(int i) {
        totalGuesses += i;
    }

    public void setGuessedLetters(Set<HangmanCharacter> guessedLettersSet) {
        guessedLetters = guessedLettersSet;
    }

    public Integer getTotalGuesses() {
        return totalGuesses;
    }
}