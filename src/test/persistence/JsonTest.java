package persistence;


import model.Word;
import model.PlayerStat;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWord(String compWord,
                             String currentStatus,
                             HashSet<Character> guessedLetters,
                             Integer totalGuesses,
                             Word word) {
        assertEquals(compWord, word.getWord());
        assertEquals(currentStatus, word.getCurrentStatus());
        assertEquals(guessedLetters, word.getGuessedLetters());
        assertEquals(totalGuesses, word.getTotalGuesses());
    }

    protected void checkPS (Integer hp,
                            Integer highestPossibleScore,
                            Integer score,
                            PlayerStat playerStat) {
        assertEquals(hp, playerStat.getHP());
        assertEquals(highestPossibleScore, playerStat.getHighestPossibleScore());
        assertEquals(score,playerStat.getScore());
    }
}
