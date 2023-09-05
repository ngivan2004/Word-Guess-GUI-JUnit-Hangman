package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HangmanCharacterTest {
    @Test
    void testEquals_sameObject() {
        HangmanCharacter hangmanCharacter1 = new HangmanCharacter('a');
        assertTrue(hangmanCharacter1.equals(hangmanCharacter1));
    }



    @Test
    void testEquals_differentObject() {
        HangmanCharacter hangmanCharacter1 = new HangmanCharacter('a');
        String differentObject = "This is a string";
        assertFalse(hangmanCharacter1.equals(differentObject));
    }

    @Test
    void testEquals_sameCharacter() {
        HangmanCharacter hangmanCharacter1 = new HangmanCharacter('a');
        HangmanCharacter hangmanCharacter2 = new HangmanCharacter('a');
        assertTrue(hangmanCharacter1.equals(hangmanCharacter2));
    }

    @Test
    void testEquals_differentCharacter() {
        HangmanCharacter hangmanCharacter1 = new HangmanCharacter('a');
        HangmanCharacter hangmanCharacter2 = new HangmanCharacter('b');
        assertFalse(hangmanCharacter1.equals(hangmanCharacter2));
    }
}