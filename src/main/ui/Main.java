package ui;

import java.io.FileNotFoundException;


//DEPRECATED!!! RUN HGUI INSTEAD
//main class, runnable.
public class Main {

    // main method, runnable
    public static void main(String[] args) {
        try {
            GameMenu menu = new GameMenu();
            menu.launchMenu();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}


