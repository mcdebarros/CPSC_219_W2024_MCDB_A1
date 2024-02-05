import java.util.Scanner;
import java.util.Arrays;

public class WordGuesser {

    // TODO: Add javadocs

    // Maximum number of guesses; constant
    static final int MAXGUESSES = 6;

    public static String getWord() {
        Scanner guess = new Scanner(System.in);
        System.out.println("Type a six letter word:");
        return guess.nextLine();
    }

    public static String fixCase(String guess) {
        return guess.toUpperCase();
    }

    public static boolean isLetter(String guess) {

        boolean allAlpha = true;

        for (int j = 0; j < guess.length(); j++) {
            if (!Character.isLetter(guess.charAt(j))) {
                allAlpha = false;
                break;
            }
        }
        return allAlpha;
    }

    public static boolean guessLength(String guess) {

        return guess.length() == 6;
    }

    public static boolean checkExit(String guess) {

        return guess.equals("EXIT");
    }

    public static String guessContent(String guess) {

        String charToCheck;
        StringBuilder guessContent = new StringBuilder();

        for (int j = 0; j < guess.length(); j++) {
            charToCheck = String.valueOf(guess.charAt(j));
            if (!guessContent.toString().contains(charToCheck)) {
                guessContent.append(charToCheck);
            }
        }
        return guessContent.toString();
    }

    public static boolean game() {

        String secretWord = "RESUME";
        String guess;
        String secretContent = guessContent(secretWord);
        String guessContent;
        String charToCheck;
        String playAgain;
        String yes = "Y";
        String no = "N";
        int numGuesses = 0;
        int correctPos = 0;
        int correctLetter;
        int[][] board = new int[6][6];
        boolean endGame = false;
        boolean choice;

        Scanner newGame = new Scanner(System.in);

        while ((numGuesses < MAXGUESSES) && (correctPos < 6)) {

            correctLetter = 0;
            correctPos = 0;
            guess = fixCase(getWord());
            guessContent = "";
            if (!isLetter(guess)) {
                System.out.println("Guess must only contain letters!");
                continue;
            } else if (checkExit(guess)) {
                endGame = true;
                break;
            } else if (!guessLength(guess)) {
                System.out.println("Guess must be EXACTLY six letters long!");
                continue;
            } else {

                for (int i = 0; i < secretWord.length(); i++) {
                    charToCheck = String.valueOf(guess.charAt(i));
                    if ((secretContent.contains(charToCheck)) && (!guessContent.contains(charToCheck))) {
                        correctLetter++;
                        guessContent += charToCheck;
                    }
                }
                for (int i = 0; i < guess.length(); i++) {
                    charToCheck = String.valueOf(guess.charAt(i));
                    if (guessContent.contains(charToCheck)) {
                        board[numGuesses][i] += 1;
                    }
                    if (guess.charAt(i) == secretWord.charAt(i)) {
                        board[numGuesses][i] += 1;
                        correctPos +=1;
                    }
                }
            }
            for (int i = 0; i < numGuesses + 1; i++) {
                System.out.println(Arrays.toString(board[numGuesses]));
            }
            numGuesses++;
        }
        if (correctPos == 6) {
            System.out.println("Congratualtions, you've won! Would you like to play again? (Y/N)");
            playAgain = fixCase(newGame.nextLine());
            while (!((playAgain.equals(no)) || (playAgain.equals(yes)))) {
                System.out.println("I'm sorry, I didn't understand your input. Please choose one of 'Y' or 'N'");
                playAgain = fixCase(newGame.nextLine());
            }
            return playAgain.equals(yes);
        } else if (endGame) {
            System.out.println("Play again soon!");
            return false;
        } else {
            System.out.println("Better luck next time! Would you like to play again? (Y/N)");
            playAgain = fixCase(newGame.nextLine());
            while (!((playAgain.equals(no)) || (playAgain.equals(yes)))) {
                System.out.println("I'm sorry, I didn't understand your input. Please choose one of 'Y' or 'N'");
                playAgain = fixCase(newGame.nextLine());
            }
            return playAgain.equals(yes);
        }
    }

    public static void main(String[] args) {
        System.out.println();
        boolean play = true;
        while (play) {
            play = game();
        }
    }
}



    // TODO: Fully implement these checks, call them in main(), and create proper javadocs for all of them


        // do I need this? Take it out if I don't, I guess
        //boolean checkIsWord(String toCheck)