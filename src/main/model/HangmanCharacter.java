package model;

import java.util.Objects;

// character class implementation for hangman
public class HangmanCharacter {
    private char character;

    public HangmanCharacter(char character) {

        this.character = character;
        EventLog.getInstance().logEvent(new Event("HangmanCharacter created: " + character));
    }

    //EFFECTS: returns the lowercase of the character
    public char toLowerCase() {
        return Character.toLowerCase(this.character);
    }


    // REQUIRES: obj is an instance of HangmanCharacter
    // EFFECTS: returns true if the HangmanCharacter objects are equal based on their character values, false otherwise
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HangmanCharacter other = (HangmanCharacter) obj;
        return character == other.character;
    }


   // EFFECTS: returns a hash code value for the HangmanCharacter object based on its character value
    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }


}

