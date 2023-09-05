package persistence;

import model.PlayerStat;
import model.Word;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFileWord() {
        try {
            Word wd = new Word();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInvalidFilePS() {
        try {
            PlayerStat ps = new PlayerStat();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterMinimalWord() {
        try {
            Word wd = new Word();
            wd.setWord("cook");
            wd.setCurrentStatus("____");
            JsonWriter writer = new JsonWriter("./data/testWriteMinimalWord.json");
            writer.open();
            writer.writeWD(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteMinimalWord.json");
            wd = reader.readwd();
            HashSet<Character> empty = new HashSet<>();
            checkWord("cook", "____", empty, 0, wd);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteDemoWord() {
        try {
            Word wd = new Word();
            wd.setWord("among us");
            wd.setCurrentStatus("am__g _s");
            wd.incrementTotalGuesses(6);
            JsonWriter writer = new JsonWriter("./data/testWriteDemoWord.json");

            writer.open();
            writer.writeWD(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteMinimalWord.json");
            wd = reader.readwd();
            HashSet<Character> empty = new HashSet<>();
            checkWord("cook", "____", empty, 0, wd);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWritePS() {
        try {
            PlayerStat playerStat = new PlayerStat();
            playerStat.setScore(0);
            playerStat.setHP(1);
            playerStat.setHighestPossibleScore(800);
            JsonWriter writer = new JsonWriter("./data/testWriteDemoPlayerStat.json");
            writer.open();
            writer.writePS(playerStat);
            writer.close();

            JsonReader reader = new JsonReader("./data/testDemoPlayerStat.json");
            PlayerStat ps = reader.readps();
            checkPS(1, 800, 0, ps);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}