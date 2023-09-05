# Hangman Game
## A simple game made exciting again

## What will the application do?

Hangman is a popular game where players have to guess a word letter by letter with limited tries until the entire word is solved.

### Classic game runthrough

For example, *suppose the word is "among us sus", then in the beginning, the following will be displayed*:
```    
_ _ _ _ _  _ _  _ _ _
```

Players are initially given the length of the word, then with each guess:

- If the letter guessed is in the word, the positions of ALL occurrances of that letter will be displayed. *Suppose the word "S" is guessed, then the following would be displayed*:
```
_ _ _ _ _  _ S  S _ S
```

- If the letter is not in the word, with each wrong guess, a drawing of a man getting hung is usually then progressively drawn next to the guess. If the drawing is completed, the player loses. If the word is succesfully guessed, the player wins.

### Project idea

This project aims to augment this popular game by adding additional game modes, game styles, leaderboards, tournament features to the game. Including

- Using a more modern "HP" bar to indicate player health instead of the legacy man drawing
- Adding a scoring system that includes parameters like how fast the player was able to solve the puzzle
- Introduce multi-player system, where different players can battle
- Introduce a tournament system, where players can compete over a long period of time to determine a final winner
- Introduce a leaderboard system
- Introduce game mechanics such as hints, which players can use with reduced points granted
- Allow words to be randomly chosen from the dictionary, with abilities to choose words that are shorter, longer, or more frequent or less frequently used
- Allow for custom words to be inputted into the game


# Instructions

- You can generate the first required action adding a character to word by typing a character into the text box
-  You can generate the second required action adding a string to word by typing a string into the text box
- You can locate my visual component by the display of unguessed word ata the center AND the sad face when you die
- You can save the state of my application by clicking the save button
- You can reload the state of my application by choosing to load the save
