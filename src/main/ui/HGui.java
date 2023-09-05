package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// GUI GAME
public class HGui extends JFrame {

    private GameInstance gameInstance;
    private JLabel currentStatusLabel;
    private JLabel hpLabel;
    private JLabel maxScoreLabel;
    private JTextField inputTextField;

    //EFFECT: constructor
    public HGui() {
        setTitle("HGui");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameInstance = new GameInstance();
        showStartupOptions();
        initComponents();


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLoggedEvents();
            }
        });
    }


    //EFFECTS; initializes the UI components
    @SuppressWarnings("methodlength")
    private void initComponents() {
        currentStatusLabel = new JLabel(gameInstance.getCurrentStatus());
        currentStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentStatusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(currentStatusLabel, BorderLayout.CENTER);

        JPanel topRightPanel = new JPanel();
        hpLabel = new JLabel("HP: " + gameInstance.getHP());
        maxScoreLabel = new JLabel("Max Score: " + gameInstance.getMaxScore());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameInstance.save();
            }
        });
        topRightPanel.add(hpLabel);
        topRightPanel.add(maxScoreLabel);
        topRightPanel.add(saveButton);
        add(topRightPanel, BorderLayout.NORTH);

        inputTextField = new JTextField(20);
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputTextField.getText();
                gameInstance.newCycle(userInput);
                updateUIComponents();
                inputTextField.setText("");
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(inputTextField);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    //EFFECTS: updates the UI when neccessary.
    private void updateUIComponents() {
        currentStatusLabel.setText(gameInstance.getCurrentStatus());
        hpLabel.setText("HP: " + gameInstance.getHP());
        maxScoreLabel.setText("Max Score: " + gameInstance.getMaxScore());

        if (gameInstance.getHP() == 0) {
            showGameOverMessage();
        }
    }


    //EFFECTS: the game over message
    private void showGameOverMessage() {
        ImageIcon icon = new ImageIcon("data/Sad-Face-Emoji.png");
        JOptionPane.showMessageDialog(this,
                "Game Over!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE,
                icon);
    }


    //EFFECTS: shows a startup meny for user
    private void showStartupOptions() {
        String[] options =
                {"Load previous save and continue",
                        "Choose random word from dictionary",
                        "Input custom word to use in game"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select an option:",
                "Startup Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        handleStartupChoice(choice);


    }


    //EFFECTS: it implements the logics for choices in startup
    @SuppressWarnings("methodlength")
    private void handleStartupChoice(int choice) {

        try {
            switch (choice) {
                case 0:
                    // Load previous save and continue
                    gameInstance.loadSave();
                    break;
                case 1:
                    // Choose random word from dictionary
                    gameInstance.randomWord();
                    showDifficultyOptions();
                    gameInstance.initialCalculations();
                    break;
                case 2:
                    // Input custom word to use in game
                    String customWord = JOptionPane.showInputDialog(this, "Enter your custom word:");
                    if (customWord != null && !customWord.trim().isEmpty()) {
                        gameInstance.customWord(customWord);
                        showDifficultyOptions();
                        gameInstance.initialCalculations();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Invalid custom word. Please try again.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                default:

                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                     "An error occurred while processing your choice: " + e.getMessage(),
                     "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //EFFECTS: it shows and allows users to select the difficulty
    private void showDifficultyOptions() {
        String[] difficulties = {"Easy", "Moderate", "Difficult", "Borderline Impossible"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select a difficulty:",
                "Difficulty Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                difficulties,
                difficulties[0]);

        gameInstance.setDifficulty(choice + 1);
    }


    //EFFECTS: it prints the logged events
    private static void printLoggedEvents() {
        System.out.println("Logged events:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }


    //EFFECTS: main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HGui hgui = new HGui();
                hgui.setVisible(true);

            }

        });

    }
}