//Import useful packages
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
    static final String intro = """
        Welcome to Wordle, the word guessing game!
        If you'd like to learn how to play, just enter the command "help" at any point during gameplay.
        If at any point you wish to quit the game, just enter the command "exit".
        """;
    static final String badMenuInput = """
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
        Scanner word = new Scanner(System.in);
        System.out.println("Type a six letter word:");
        return word.nextLine();
    }

    /**
     * Solves case-dependency by setting all characters to capital letters
     * @param word String; string object containing guess or secret word
     * @return guess or secret word string object converted to uppercase
     */
    public static String fixCase(String word) {
        return word.toUpperCase();
    }

    /**
     * Checks the guess for non-alphabetic characters
     * @param word String; string object containing guess or secret word
     * @return Boolean; true if string contains non-alphabetic characters; true otherwise
     */
    public static boolean isNotLetter(String word) {

        boolean allAlpha = true;

        for (int j = 0; j < word.length(); j++) {
            if (!Character.isLetter(word.charAt(j))) {
                allAlpha = false;
                break;
            }
        }
        return !allAlpha;
    }

    /**
     * Checks if the guess or secret word is exactly six characters long
     * @param word String; string object containing guess or secret word
     * @return Boolean; true if string is not exactly six characters; false otherwise
     */
    public static boolean wordLength(String word) {

        return word.length() != 6;
    }

    /**
     * Creates a set of characters in the secret word
     * @param word String; string object containing guess or secret word
     * @return HashSet of characters in the secret word
     */
    public static HashSet<Character> wordContent(String word) {

        HashSet<Character> content = new HashSet<>();

        for (int j = 0; j < word.length(); j++) {
            content.add(word.charAt(j));
            }
        return content;
    }

    /**
     * Execution of the wordle game
     * @param secretWord String; string object containing guess or secret word
     * @return Boolean; true if user wishes to play another game; false otherwise
     */
    public static boolean game(String secretWord) {

        if (secretWord.isEmpty()) {
            secretWord = DEFAULT;}
        String guess;
        HashSet<Character> secretContent = wordContent(secretWord);
        String playAgain;
        char[] guessArr = new char[6];
        char[] secretArr = new char[6];
        int finalScore = 0;
        int numGuesses = 0;
        int correctPos = 0;
        int[][] board = new int[6][6];

        Scanner newGame = new Scanner(System.in);
        System.out.println("Cool! Let's play! \n");

        for (int i = 0; i < 6; i++) {
            secretArr[i] = secretWord.charAt(i);
        }

        while ((numGuesses < MAXGUESSES) && (correctPos < 6)) {

            correctPos = 0;
            guess = fixCase(getWord());

            if (guess.equals(HELP)) {
                System.out.println(HELPMESSAGE);
                continue;
            } else if (isNotLetter(guess)) {
                System.out.println(NOTLETTER);
                continue;
            } else if (guess.equals(EXIT)) {
                return false;
            } else if (wordLength(guess)) {
                System.out.println(BADLENGTH);
                continue;
            } else {

                for (int i = 0 ; i < 6; i++) {
                    guessArr[i] = guess.charAt(i);
                }
                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretContent.contains(guessArr[i])) {
                        board[numGuesses][i]++;
                        if (secretArr[i] == (guessArr[i])) {
                            board[numGuesses][i]++;
                            correctPos++;
                        }
                    }
                }
            }
            for (int i = 0; i < numGuesses + 1; i++) {
                System.out.println(Arrays.toString(board[i]));
            }
            numGuesses++;
            if (correctPos != 6) {
                System.out.println(STR."You have \{MAXGUESSES - numGuesses} guesses remaining!");
            }
        }
        finalScore += (MAXGUESSES - numGuesses);
        for (int i = 0; i < 6; i++) {
            finalScore = finalScore + board[numGuesses - 1][i];
        }
        if (correctPos == 6) {
            System.out.println(WINNER);
            System.out.println(STR."Your final score was \{finalScore} points!");
            playAgain = fixCase(newGame.nextLine());
            while (!((playAgain.equals(NO)) || (playAgain.equals(YES)))) {
                if (playAgain.equals(HELP)) {
                    System.out.println(HELPMESSAGE);
                    System.out.println(WINNER);
                    System.out.println(STR."Your final score was \{finalScore} points!");
                    playAgain = fixCase(newGame.nextLine());
                } else if (playAgain.equals(EXIT)) {
                    System.out.println(GOODBYE);
                    return false;
                } else {
                    System.out.println(YESNOERROR);
                    System.out.println(WINNER);
                    System.out.println(STR."Your final score was \{finalScore} points!");
                    playAgain = fixCase(newGame.nextLine());
                }
            }
        } else {
            System.out.println(LOSER);
            System.out.println(STR."Your final score was \{finalScore} points!");
            playAgain = fixCase(newGame.nextLine());
            while (!((playAgain.equals(NO)) || (playAgain.equals(YES)))) {
                if (playAgain.equals(HELP)) {
                    System.out.println(HELPMESSAGE);
                    System.out.println(LOSER);
                    System.out.println(STR."Your final score was \{finalScore} points!");
                    playAgain = fixCase(newGame.nextLine());
                } else if (playAgain.equals(EXIT)) {
                    System.out.println(GOODBYE);
                    return false;
                } else {
                    System.out.println(YESNOERROR);
                    System.out.println(LOSER);
                    System.out.println(STR."Your final score was \{finalScore} points!");
                    playAgain = fixCase(newGame.nextLine());
                }
            }
        }
        return playAgain.equals(YES);
    }

    public static void main(String[] args) {
        Scanner mainMenu = new Scanner(System.in);
        String input;
        String secretWord;

        System.out.println(intro);

        boolean play = true;
        label:
        while (play) {
            System.out.println(CUSTOMWORD);
            input = fixCase(mainMenu.nextLine());
            while (!((input.equals(YES)) || (input.equals(NO)) || (input.equals(EXIT)) || (input.equals(HELP)))) {
                System.out.println(badMenuInput);
                input = fixCase(mainMenu.nextLine());}
            switch (input) {
                case HELP:
                    System.out.println(HELPMESSAGE);
                    break;
                case EXIT:
                    System.out.println(GOODBYE);
                    break label;
                case NO:
                    secretWord = "";
                    play = game(secretWord);
                    break;
                default:
                    secretWord = fixCase(getWord());
                    if (secretWord.equals(HELP)) {
                        System.out.println(HELPMESSAGE);
                    } else if (secretWord.equals(EXIT)) {
                        System.out.println(GOODBYE);
                        break label;
                    } else if (wordLength(secretWord)) {
                        System.out.print(BADLENGTH);
                    } else if (isNotLetter(secretWord)) {
                        System.out.println(NOTLETTER);
                    } else {
                        play = game(secretWord);
                    }
                    break;
            }
        }
    }
}