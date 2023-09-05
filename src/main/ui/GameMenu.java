package ui;

import java.io.FileNotFoundException;
import java.util.Scanner;
import ui.GameInstance;


// DEPRECATED RUN HGUI INSTEAD
// a menu for the game
public class GameMenu {

    GameInstance game;

    //MODIFIES:this
    //EFFECTS: initializes fields, creates object
    public GameMenu() throws FileNotFoundException {
        this.game = new GameInstance();
    }


    //MODIFIES: GameInstance
    //EFFECTS: starts the game menu
    public void launchMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN - THE GAME");
        int input;
        while (true) {
            System.out.println("Select Game Mode: ");
            System.out.println("1 - Single Player");
            System.out.println("2 - Leaderboard (Coming soon)");
            input = scanner.nextInt();
            if (input == 1) {
                game.startSinglePlayerGame(scanner);
                break;
            }
            System.out.println("Invalid input. Please enter a number between 1 and 1.");
        }

    }
}
