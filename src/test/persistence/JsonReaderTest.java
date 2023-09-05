package persistence;

import model.Word;
import model.PlayerStat;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFileWord() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Word wd = reader.readwd();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNonExistentFilePS() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayerStat ps = reader.readps();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNonExistentFileDict() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<String> dict = reader.readdict();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderMinimalWord() {
        JsonReader reader = new JsonReader("./data/testMinimalWord.json");
        try {
            Word wd = reader.readwd();
            HashSet<Character> empty = new HashSet<>();
            checkWord("cook", "____",empty,0,wd);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWord() {
        JsonReader reader = new JsonReader("./data/testDemoWord.json");
        try {
            Word wd = reader.readwd();
            HashSet<Character> set = new HashSet<>();
            set.add("a".charAt(0));
            set.add("r".charAt(0));
            set.add("s".charAt(0));
            set.add("t".charAt(0));
            set.add("g".charAt(0));
            set.add("m".charAt(0));
            checkWord("among us", "am__g _s",set,6,wd);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPS() {
        JsonReader reader = new JsonReader("./data/testDemoPlayerStat.json");
        try {
            PlayerStat ps = reader.readps();
            checkPS(1, 800,0,ps);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testDict() {
        JsonReader reader = new JsonReader("./data/dictionary.json");
        try {
            ArrayList<String> dictArray = reader.readdict();
            assertFalse(dictArray.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testASCII() {assertEquals(JsonReader.asciiToChar(70),70);}
}