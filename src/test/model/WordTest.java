package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    private Word wordToTest;

    @BeforeEach
    void runBefore() {
        wordToTest = new Word();
    }

    @Test
    void testGuessCorrect() {
        wordToTest.setWord("amogus");
        wordToTest.setCurrentStatus("______");
        assertTrue(wordToTest.guess(new HangmanCharacter('g')));
        assertEquals("___g__", wordToTest.getCurrentStatus());
    }

    @Test
    void testGuessIncorrect() {
        wordToTest.setWord("sussy baka");
        wordToTest.setCurrentStatus("_____ ____");
        assertFalse(wordToTest.guess(new HangmanCharacter('z')));
        assertEquals("_____ ____", wordToTest.getCurrentStatus());
    }

    @Test
    void testGuessMultipleCorrect() {
        wordToTest.setWord("sussy baka");
        wordToTest.setCurrentStatus("_____ ____");
        assertTrue(wordToTest.guess(new HangmanCharacter('s')));
        assertEquals("s_ss_ ____", wordToTest.getCurrentStatus());
    }



    @Test
    void testIsWordGuessedCorrect() {
        wordToTest.setWord("trustnaturalrecursion");
        wordToTest.setCurrentStatus("trustnaturalrecursion");
        assertTrue(wordToTest.isWordGuessed());
    }

    @Test
    void testIsWordGuessedWrong() {
        wordToTest.setWord("trustnaturalrecursion");
        wordToTest.setCurrentStatus("trustnaturalrecursi_n");
        assertFalse(wordToTest.isWordGuessed());
    }

    @Test
    void testGetEmptyWord() {
        assertEquals("",wordToTest.getWord());
    }

    @Test
    void testGetWord() {
        wordToTest.setWord("sus");
        assertEquals("sus",wordToTest.getWord());
    }

    @Test
    void testGuessedLetters() {
        HashSet<HangmanCharacter> guessed = new HashSet<>();
        guessed.add(new HangmanCharacter('a'));
        wordToTest.setWord("sus");
        wordToTest.addGuessedLetters(new HangmanCharacter('a'));
        assertEquals(guessed,wordToTest.getGuessedLetters());

    }

    @Test
    void testIncrementTotalGuesses() {
        assertEquals(wordToTest.getTotalGuesses(),0);
        wordToTest.incrementTotalGuesses(5);
        assertEquals(wordToTest.getTotalGuesses(), 5);
    }


}