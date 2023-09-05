package ui;

import model.HangmanCharacter;
import model.PlayerStat;
import model.Word;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import java.util.ArrayList;
import java.util.Random;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;


// an instance of the game played (currently singleplayer)

public class GameInstance {
    private static final String JSON_W = "./data/word.json";
    private static final String JSON_PS = "./data/playerStat.json";
    private static final String JSON_D = "./data/dictionary.json";
    private Word word;

    private int difficulty;
    //private int totalGuesses;
    private PlayerStat playerStat;

    private JsonWriter wordWriter;
    private JsonReader wordReader;
    private JsonWriter psWriter;
    private JsonReader psReader;
    private JsonReader dictReader;
    private Boolean isLoadSave;

    //MODIFIES: this
    //EFFECTS: initializes fields and creates object
    public GameInstance() {

        this.word = new Word();
        this.playerStat = new PlayerStat();
        this.difficulty = 0;
        //this.totalGuesses = 0;
        this.wordReader = new JsonReader(JSON_W);
        this.wordWriter = new JsonWriter(JSON_W);
        this.psReader = new JsonReader(JSON_PS);
        this.psWriter = new JsonWriter(JSON_PS);
        this.dictReader = new JsonReader(JSON_D);
        this.isLoadSave = false;
    }


    //REQUIRES: a scanner to be passed
    //MODIFIES: this, playerStat
    //EFFECTS: starts a singleplayer game
    public void startSinglePlayerGame(Scanner scanner) {

        System.out.println("Welcome to Hangman!");
        initializeUserCustomWord();
        if (!(isLoadSave)) {
            difficulty = selectDifficulty(scanner);
            playerStat.calculateHP(numUniqueChars(),difficulty);
        }
        clearScreen();
        actualGame(scanner);

        endGame();
    }

    //EFFECTS: computes initial calculations for playerstats
    public void initialCalculations() {
        playerStat.calculateHP(numUniqueChars(),difficulty);
        playerStat.calculateHighestPossibleScore(numUniqueChars(),word.getTotalGuesses());
    }

    //REQUIRES: a scanner to be passed
    //MODIFIES: this
    //EFFECTS: allows user to select difficulty
    private int selectDifficulty(Scanner scanner) {
        int input;
        while (true) {
            System.out.println("Select difficulty: ");
            System.out.println("1 - Easy");
            System.out.println("2 - Moderate");
            System.out.println("3 - Difficult");
            System.out.println("4 - Borderline Impossible");
            input = scanner.nextInt();
            if (input >= 1 && input <= 4) {
                break;
            }
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
        return input;
    }



    //EFFECTS: returns the number of unique characters in the word to be guessed.
    public int numUniqueChars() {
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : word.getWord().toCharArray()) {
            uniqueChars.add(c);
        }
        return uniqueChars.size();

    }



    //MODIFIES: this, word
    //EFFECTS: prompts the pre-game prompt to enter the secret word.
    @SuppressWarnings("methodlength")
    public void initializeUserCustomWord() {

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Choose below: ");
            System.out.println("E - Load previous save and continue.");
            System.out.println("R - Choose random word from dictionary.");
            System.out.println("Or enter in a custom word to use that word in the game.");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("e")) {
                loadSave();
                validInput = true;
                isLoadSave = true;

            } else if (input.equals("r")) {
                try {
                    ArrayList<String> dictionary = dictReader.readdict();
                    Random random = new Random();
                    String randomWord = dictionary.get(random.nextInt(dictionary.size()));
                    word.setWord(randomWord);
                    word.setCurrentStatus(word.getWord().replaceAll("[a-zA-Z0-9\\-]", "_"));
                } catch (IOException e) {
                    System.out.print("Application files corrupted, dictionary missing.");
                } finally {
                    validInput = true;
                }

            } else if (input.matches("^[a-zA-Z0-9\\- ]+$")) {
                word.setWord(input.toLowerCase());
                word.setCurrentStatus(word.getWord().replaceAll("[a-zA-Z0-9\\-]", "_"));
                validInput = true;
            } else {
                System.out.println("The word can only contain alphanumeric characters, hyphens, or spaces.");
            }
        }
    }
    //REQUIRES: a scanner to be passed in
    //MODIFIES: this
    //EFFECTS: allows user to play the game, either by guessing a letter or a word.

    private void actualGame(Scanner scanner) {
        while (playerStat.getHP() > 0 && !word.isWordGuessed()) {

            displayGameStat();

            String input = getInput(scanner);
            if (input.isEmpty()) {
                clearScreen();
                System.out.println("Hint: Make sure to input at least one letter. Input cannot be empty.");
            } else  if (input.equals("^")) {
                save();

            } else if (input.length() > 1) {
                clearScreen();
                guessWord(input);
            } else {
                clearScreen();
                guessLetter(input.charAt(0));
            }
        }
    }

    //EFFECTS: produces the current stats of the game
    //MODIFIES: playerStat

    private void displayGameStat() {
        System.out.println("Current word: " + word.getCurrentStatus());
        System.out.println("HP: " + playerStat.getHP());
        playerStat.calculateHighestPossibleScore(numUniqueChars(),word.getTotalGuesses());
        System.out.println("Points if you get it right this turn: "
                + playerStat.getHighestPossibleScore());
        System.out.println("Guess a letter or the entire word: ");
        System.out.println("Input ^ at any time to save the game.");
    }

    //EFFECTS: gets user input
    private String getInput(Scanner scanner) {
        return scanner.nextLine().toLowerCase();
    }


    //REQUIRES: input must be a string
    //MODIFIES: word, this, playerStat
    //EFFECTS: allows user to guess an entire word at once
    private void guessWord(String input) {
        if (input.equals(word.getWord())) {
            word.setCurrentStatus(word.getWord());
        } else {
            playerStat.deltaHP(-1);
            System.out.println("Wrong guess!");
        }

        word.incrementTotalGuesses(1);
    }


    //REQUIRES: input must be a character
    //MODIFIES: word, this, playerStat
    //EFFECTS: allows user to guess a letter
    private void guessLetter(char c) {
        HangmanCharacter hc = new HangmanCharacter(c);
        if (word.getGuessedLetters().contains(hc)) {
            System.out.println("You have already guessed the letter " + hc.getCharacter() + ". Please try again.");

        } else {
            boolean letterFound = word.guess(hc);
            word.addGuessedLetters(hc);

            if (letterFound) {
                System.out.println(hc.getCharacter() + " is in the word!");
            } else {
                playerStat.deltaHP(-1);
                System.out.println("Wrong guess!");
            }
        }
        word.incrementTotalGuesses(1);

    }


    //EFFECTS: prints the game end screen
    //MODIFIES: playerStat
    private void endGame() {
        if (playerStat.getHP() > 0) {
            System.out.println("You win! The word was: " + word.getWord());
            playerStat.calculateScore(numUniqueChars(),word.getTotalGuesses());
            System.out.println("Your total score is "
                    + playerStat.getScore());

        } else {
            System.out.println("You lost! The word was: " + word.getWord());
        }
    }

    //EFFECT: loads save from json
    //MODIFIES: this
    public void loadSave() {
        try {
            word = wordReader.readwd();
            playerStat = psReader.readps();
            System.out.println("Loaded save" + JSON_W + " and " + JSON_PS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_W + " or " + JSON_PS);
        }
    }

    //EFFECT: save to json
    //MODIFIES: the json file
    public void save() {
        try {
            clearScreen();
            wordWriter.open();
            wordWriter.writeWD(word);
            wordWriter.close();
            psWriter.open();
            psWriter.writePS(playerStat);
            psWriter.close();
            System.out.println("Saved to " + JSON_W + " and " + JSON_PS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_W + " or " + JSON_PS);
        }
    }


    //EFFECTS: adds 50 empty lines to block previous input in each round and when game starts
    private void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    //EFFECTS: choose random word
    public void randomWord() throws IOException {
        ArrayList<String> dictionary = dictReader.readdict();
        Random random = new Random();
        String randomWord = dictionary.get(random.nextInt(dictionary.size()));
        word.setWord(randomWord);
        word.setCurrentStatus(word.getWord().replaceAll("[a-zA-Z0-9\\-]", "_"));
    }

    public void customWord(String input) {
        word.setWord(input);
        word.setCurrentStatus(word.getWord().replaceAll("[a-zA-Z0-9\\-]", "_"));
    }

    public void setDifficulty(int number) {
        difficulty = number;
    }

    public String getCurrentStatus() {
        return word.getCurrentStatus();
    }

    public Integer getHP() {
        return playerStat.getHP();
    }

    public Integer getMaxScore() {
        return playerStat.getHighestPossibleScore();
    }


    //EFFECTS: start new game cycle every turn
    public void newCycle(String input) {
        if (input.length() == 1) {
            guessLetter(input.charAt(0));
        } else {
            guessWord(input);
        }

        playerStat.calculateHighestPossibleScore(numUniqueChars(),word.getTotalGuesses());

    }
}