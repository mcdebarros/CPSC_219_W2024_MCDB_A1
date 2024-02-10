//Import useful packages and classes
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;

//Define wordGuesser class
public class WordGuesser {

    //Define maximum allowable guesses and some dialogue strings to reduce downstream clutter
    static final int MAXGUESSES = 6;
    static final String HELPMESSAGE = """
            Wordle is a word guessing game! A secret word has been chosen, and it's your job to guess it.
            The secret word will always have exactly six letters, and you have exactly six guesses to figure it out.
            After each guess, a "game board" will appear in the console. For each guess you input, a new row will appear on the board.
            Each row will contain six numbers. Each number in that row represents a letter in your guess.
            "0" means that the letter in this position in your guess is not in the secret word.
            "1" means that the letter in this position in your guess is in the secret word, but not in the correct position.
            "2" means that the letter in this position exactly matches the secret word; don't change it!
            Your score is calculated as the sum of the numbers in the final row of the game board
            plus the amount of guesses remaining when the word was correctly guessed.
            Remember, you can always access these instructions by entering the command "help".
            If at any point you wish to stop playing, you can quit the game by entering the command "exit".
            This implementation of Wordle allows for the user to set a secret word to solve for.
            """;
    static final String INTRO = """
        Welcome to Wordle, the word guessing game!
        If you'd like to learn how to play, just enter the command "help" at any point during gameplay.
        If at any point you wish to quit the game, just enter the command "exit".
        """;
    static final String BADMENUINPUT = """
        I'm sorry, I didn't understand your input.
        To set a word, please enter the command "Y".
        To use the default word, please enter the command "N".
        For instructions on how to play, please enter the command "help".
        To quit the game, please enter the command "exit".
        """;
    static final String CUSTOMWORD = "Would you like to set your own secret word? (Y/N)";
    static final String NOTLETTER = "Oops! Words must only contain letters!";
    static final String BADLENGTH = "Oops! Words must be EXACTLY six letters long!";
    static final String GOODBYE = "Play again soon!";
    static final String YES = "Y";
    static final String NO = "N";
    static final String EXIT = "EXIT";
    static final String HELP = "HELP";
    static final String DEFAULT = "RANDOM";
    static final String WINNER = "Congratulations, you've won! Would you like to play again? (Y/N)";
    static final String LOSER = "Better luck next time! Would you like to play again? (Y/N)";
    static final String YESNOERROR = "I'm sorry, I didn't understand your input. Please choose one of 'Y' or 'N'";

    /**
     * Retrieve guess or secret word from user via scanner
     * @return word String; secret word or current guess
     */
    public static String getWord() {
        Scanner word = new Scanner(System.in); //Input scanner to collect "word" string for guesses and secret words
        System.out.println("Type a six letter word:"); //Prompts the user to input their choice of word
        return word.nextLine(); //Returns user word input
    }

    /**
     * Solves case-dependency by setting all characters to capital letters
     * @param word String; string object containing guess or secret word
     * @return guess or secret word string object converted to uppercase
     */
    public static String fixCase(String word) {
        return word.toUpperCase();
    } //Converts all string characters to uppercase, removing case dependency

    /**
     * Checks the guess for non-alphabetic characters
     * @param word String; string object containing guess or secret word
     * @return Boolean; true if string contains non-alphabetic characters; true otherwise
     */
    public static boolean isNotLetter(String word) {

        boolean allAlpha = true; //Initialize truth of all alphabetical characters

        for (int j = 0; j < word.length(); j++) { //Check each character in the provided string
            if (!Character.isLetter(word.charAt(j))) {
                allAlpha = false; //set allAlpha to false and break the loop if any letter is non-alphabetic
                break;
            }
        }
        return !allAlpha; //Return truth of alphabetical content
    }

    /**
     * Checks if the guess or secret word is exactly six characters long
     * @param word String; string object containing guess or secret word
     * @return Boolean; true if string is not exactly six characters; false otherwise
     */
    public static boolean wordLength(String word) {

        return word.length() != 6; //Check if word is exactly six characters and return truth of check
    }

    /**
     * Creates a set of characters in the secret word
     * @param word String; string object containing guess or secret word
     * @return HashSet of characters in the secret word
     */
    public static HashSet<Character> wordContent(String word) {

        HashSet<Character> content = new HashSet<>(); //Create empty hashset to populate with secret word or guess characters

        for (int j = 0; j < word.length(); j++) {
            content.add(word.charAt(j)); //Indiscriminately add each character in the word string to the hash set. Characters already in the set are not duplicated by default.
            }
        return content; //Return the hashset of word content
    }

    /**
     * Execution of the wordle game
     * @param secretWord String; string object containing guess or secret word
     * @return Boolean; true if user wishes to play another game; false otherwise
     */
    public static boolean game(String secretWord) {

        if (secretWord.isEmpty()) {
            secretWord = DEFAULT;} //Set the secret word to some default if empty string passed
        String guess; //Initialize the guess
        HashSet<Character> secretContent = wordContent(secretWord); //Creates a set containing each letter of the secret word
        String playAgain; //Whether the user would like to play again after game completes
        char[] guessArr = new char[6]; //Character array of the guess (unpopulated)
        char[] secretArr = new char[6]; //Character array of the secret word (unpopulated)
        int finalScore = 0;
        int numGuesses = 0; //Guesses used
        int correctPos = 0; //Letters in correct position (6 when game is won)
        int[][] board = new int[6][6]; //Game board to store information about correct letters and positions; each row represents a guess. Each entry in that row represents overall correctness of guess content in the same position
        Scanner newGame = new Scanner(System.in); //Scanner for"new game" input
        System.out.println("Cool! Let's play! \n"); //Begin game message

        for (int i = 0; i < 6; i++) {
            secretArr[i] = secretWord.charAt(i); //Distribute characters of secret word string into secret word array
        }

        while ((numGuesses < MAXGUESSES) && (correctPos < 6)) { //Play the game while fewer than 6 guesses have been made AND less than 6 letters are in the right position. End the game when one or both criteria not met.

            correctPos = 0; //Zero the position counter for every guess
            guess = fixCase(getWord()); //Use the scanner to collect a guess string and use fixCase to remove case dependency.

            if (guess.equals(HELP)) {
                System.out.println(HELPMESSAGE); //Print the help message if user inputs "help"
                continue; //Restart the while loop without increasing the guess counter
            } else if (isNotLetter(guess)) {
                System.out.println(NOTLETTER); //Inform the user that the guess contains non-alphabetic characters.
                continue; //Restart the while loop without increasing the guess counter
            } else if (guess.equals(EXIT)) {
                System.out.println(GOODBYE);
                return false; //Terminate the game function and return false, indicating the user does not wish to play again.
            } else if (wordLength(guess)) {
                System.out.println(BADLENGTH); //Inform the user that the guess is not exactly six characters long
                continue; //Restart the while loop without increasing the guess counter
            } else {

                for (int i = 0 ; i < 6; i++) {
                    guessArr[i] = guess.charAt(i); //Populate the guess array with characters from the guess string
                }
                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretContent.contains(guessArr[i])) {
                        board[numGuesses][i]++; //Increment by one the value of the ith entry of the (numGuesses)th row of the board if the secret word contains the character in the ith index of the guess array
                        if (secretArr[i] == (guessArr[i])) {
                            board[numGuesses][i]++; //Increment by one the value of the ith entry of the (numGuesses)th row of the board if the ith entry of the secret word array and the ith entry of the character array are exactly the same
                            correctPos++; //Increment by one the correct position counter if the above criteria is met
                        }
                    }
                }
            }
            for (int i = 0; i < numGuesses + 1; i++) {
                System.out.println(Arrays.toString(board[i])); //After each guess, print a string of the entire game board so far. A value of 0 indicates the letter in this position of the guess is not in the secret word.
            } //A value of 1 indicates the guess character in this position is in the secret word, but in the wrong place. A value of two indicates the correct letter in the correct location.
            numGuesses++; //Increment by one the number of guesses used
            if (correctPos != 6) {
                System.out.println(STR."You have \{MAXGUESSES - numGuesses} guesses remaining!"); //Print the number of guesses remaining if the user has not one the game with this guess
            }
        }
        finalScore += (MAXGUESSES - numGuesses); //Calculate the portion of the user's score related to unused guesses
        for (int i = 0; i < 6; i++) {
            finalScore = finalScore + board[numGuesses - 1][i]; //Calculate the portion of the users score that is based on correctness of the last used guess. Score is the sum of values on the final row of the game board plus unused guesses.
        }
        if (correctPos == 6) { //Case is the user has won
            System.out.println(WINNER); //Inform the user that they have won the game
            System.out.println(STR."Your final score was \{finalScore} points!"); //Show the user their final score
            playAgain = fixCase(newGame.nextLine()); //Ask the user if they would like to play again
            while (!((playAgain.equals(NO)) || (playAgain.equals(YES)))) { //Loop while user has not selected "y" or "n" for new game prompt
                if (playAgain.equals(HELP)) {
                    System.out.println(HELPMESSAGE); //Display the help message if prompted by the reader
                    System.out.println(WINNER); //Once again display the winner message
                    System.out.println(STR."Your final score was \{finalScore} points!"); //Once again display the score
                    playAgain = fixCase(newGame.nextLine()); //Collect new game input from user
                } else if (playAgain.equals(EXIT)) {
                    System.out.println(GOODBYE); //Print the goodbye message if the user wishes to exit
                    return false; //Return false as the users selected desire to play a new game
                } else {
                    System.out.println(YESNOERROR); //Display the yes/no error if the user's input is not understood
                    System.out.println(WINNER); //Once again display the win message
                    System.out.println(STR."Your final score was \{finalScore} points!"); //Once again display the user score
                    playAgain = fixCase(newGame.nextLine()); //Collect input from the user about the will to play another game
                }
            }
        } else { //Case if the user has lost
            System.out.println(LOSER); //Inform the user of their loss
            System.out.println(STR."Your final score was \{finalScore} points!"); //Show user their score
            playAgain = fixCase(newGame.nextLine()); //Collect input from user about their will to play again
            while (!((playAgain.equals(NO)) || (playAgain.equals(YES)))) { //Loop while user has not selected "y" or "n" for new game prompt
                if (playAgain.equals(HELP)) {
                    System.out.println(HELPMESSAGE); //Display the help message if prompted by the reader
                    System.out.println(LOSER);//Once again display the loser message
                    System.out.println(STR."Your final score was \{finalScore} points!"); //Once again display the score
                    playAgain = fixCase(newGame.nextLine()); //Collect new game input from user
                } else if (playAgain.equals(EXIT)) {
                    System.out.println(GOODBYE); //Print the goodbye message if the user wishes to exit
                    return false; //Return false as the users selected desire to play a new game
                } else {
                    System.out.println(YESNOERROR); //Display the yes/no error if the user's input is not understood
                    System.out.println(LOSER); //Once again display the loser message
                    System.out.println(STR."Your final score was \{finalScore} points!"); //Once again display the score
                    playAgain = fixCase(newGame.nextLine()); //Collect input from the user about the will to play another game
                }
            }
        }
        return playAgain.equals(YES); //Return statement on the user's desire to play again
    }

    /**
     * Main body of the function; serves as main menu and welcome screen. Calls the game function and handles inputs such as new game selection.
     * @param args; Left blank by default
     */
    public static void main(String[] args) {
        Scanner mainMenu = new Scanner(System.in); //Initialize scanner for secret word collection
        String input; //Initialize string of user input
        String secretWord; //Initialize string of secret word

        System.out.println(INTRO); //Print the introductory message

        boolean play = true; //Set the desire for a new game to true by default
        label:
        while (play) { //Loop while the user wishes to play again
            System.out.println(CUSTOMWORD); //Ask the user if they wish to set a custom secret word
            input = fixCase(mainMenu.nextLine()); //Collect custom word selection info
            while (!((input.equals(YES)) || (input.equals(NO)) || (input.equals(EXIT)) || (input.equals(HELP)))) {
                System.out.println(BADMENUINPUT); //Print unrecognized input statement
                input = fixCase(mainMenu.nextLine());} //Recollect input
            switch (input) { //Switch cases for recognized input
                case HELP:
                    System.out.println(HELPMESSAGE); //Display the help message
                    break; //Break the switch loop
                case EXIT:
                    System.out.println(GOODBYE); //Print the exit message.
                    break label; //Break the switch loop
                case NO:
                    secretWord = ""; //Set the secret word string as empty
                    play = game(secretWord); //Play the game with blank secret word
                    break; //break the switch loop
                default:
                    secretWord = fixCase(getWord()); //Collect a custom secret word string from the user
                    if (secretWord.equals(HELP)) {
                        System.out.println(HELPMESSAGE); //Display the help message is user inputs "help"
                    } else if (secretWord.equals(EXIT)) {
                        System.out.println(GOODBYE); //Display the exit message
                        break label; //Break both the switch case and the while loop
                    } else if (wordLength(secretWord)) {
                        System.out.print(BADLENGTH); //Inform the user of erroneous word length
                    } else if (isNotLetter(secretWord)) {
                        System.out.println(NOTLETTER); //Inform the user that the word contains non-alphabetic characters
                    } else {
                        play = game(secretWord); //Play the game with a custom secret word
                    }
                    break; //break the switch loop
            }
        }
    }
}