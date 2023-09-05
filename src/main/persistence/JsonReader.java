package persistence;

import model.HangmanCharacter;
import model.Word;
import model.PlayerStat;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads both playerstat and word from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads word from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Word readwd() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWord(jsonObject);
    }

    // EFFECTS: reads playerStat from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerStat readps() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerStat(jsonObject);
    }

    // EFFECTS: reads dictionary from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<String> readdict() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDictionary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses word from JSON object and returns it
    private Word parseWord(JSONObject jsonObject) {
        Word wd = new Word();
        wd.setWord(jsonObject.getString("word"));
        wd.setCurrentStatus(jsonObject.getString("currentStatus"));

        JSONArray guessedLettersArray = jsonObject.getJSONArray("guessedLetters");
        Set<HangmanCharacter> guessedLettersSet = new HashSet<>();
        for (int i = 0; i < guessedLettersArray.length(); i++) {
            guessedLettersSet.add(new HangmanCharacter(asciiToChar(guessedLettersArray.getInt(i))));
        }
        wd.setGuessedLetters(guessedLettersSet);

        wd.incrementTotalGuesses(jsonObject.getInt("totalGuesses"));
        return wd;
    }

    //EFFECTS: ascii to char
    public static char asciiToChar(int asciiCode) {
        return (char) asciiCode;
    }

    // EFFECTS: parses playerstat from JSON object and returns it
    private PlayerStat parsePlayerStat(JSONObject jsonObject) {
        PlayerStat ps = new PlayerStat();
        ps.setHP(jsonObject.getInt("hp"));
        ps.setHighestPossibleScore(jsonObject.getInt("highestPossibleScore"));
        ps.setScore(jsonObject.getInt("score"));
        return ps;
    }

    // EFFECTS: parses dictionary from JSON object and returns it
    private ArrayList<String> parseDictionary(JSONObject jsonObject) {
        String chosenWord;


        JSONArray dictArray = jsonObject.getJSONArray("words");
        ArrayList<String> ramArray = new ArrayList<>();
        for (int i = 0; i < dictArray.length(); i++) {
            ramArray.add(dictArray.getString(i));
        }

        return ramArray;
    }

}
