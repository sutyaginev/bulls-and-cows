package bullscows;

import java.util.Scanner;

public class BullsAndCows {
    private String secretNumber;
    private int bulls;
    private int cows;
    private int turnNumber;
    private int secretCodeLength;
    private int possibleSymbolsNumber;
    private final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private boolean isContinue = true;

    public static final Scanner scanner = new Scanner(System.in);

    void processGame() {
        inputTwoParameters();

        if (isContinue) {
            generateSecretNumber();
            inputGreeting();

            System.out.println(secretNumber);
            while (bulls != secretCodeLength) {
                turnNumber++;
                System.out.printf("Turn %d:\n", turnNumber);
                String inputNumber = scanner.next();
                countBullsAndCows(inputNumber);
                printResult();
            }
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    void inputTwoParameters() {
        String input;

        System.out.println("Input the length of the secret code:");
        input = scanner.nextLine();
        if (input.matches("\\d+")) {
            secretCodeLength = Integer.parseInt(input);
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            isContinue = false;
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        input = scanner.nextLine();
        if (input.matches("\\d+")) {
            possibleSymbolsNumber = Integer.parseInt(input);
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            isContinue = false;
            return;
        }

        if (secretCodeLength <= 0 || possibleSymbolsNumber <= 0) {
            System.out.println("Error: length of the secret code and number of possible symbols can't be less or equal 0");
            isContinue = false;
        } else if (possibleSymbolsNumber < secretCodeLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", secretCodeLength, possibleSymbolsNumber);
            isContinue = false;
        } else if (possibleSymbolsNumber > POSSIBLE_CHARS.length()) {
            System.out.printf("Error: maximum number of possible symbols in the code is POSSIBLE_CHARS.length() (0-9, a-z).");
            isContinue = false;
        }
    }

    void inputGreeting() {
        StringBuilder sb = new StringBuilder();
        sb.append("The secret is prepared: ");
        for (int i = 0; i < secretCodeLength; i++) {
            sb.append("*");
        }

        if (possibleSymbolsNumber > 0 && possibleSymbolsNumber <= 1) {
            sb.append(" (0)");
        } else if (possibleSymbolsNumber <= 10) {
            sb.append(String.format(" (0-%d).", possibleSymbolsNumber - 1));
        } else if (possibleSymbolsNumber <= 11) {
            sb.append(String.format(" (0-9, a)."));
        } else  {
            sb.append(String.format(" (0-9, a-%s).", POSSIBLE_CHARS.charAt(possibleSymbolsNumber - 1)));
        }

        sb.append("\nOkay, let's start a game!");

        System.out.println(sb.toString());
    }

    void countBullsAndCows(String inputNumber) {
        bulls = 0;
        cows = 0;
        for (int i = 0; i < secretNumber.length(); i++) {
            if (secretNumber.charAt(i) == inputNumber.charAt(i)) {
                bulls++;
            } else if (inputNumber.contains(String.valueOf(secretNumber.charAt(i)))) {
                cows++;
            }
        }
    }

    void printResult() {
        StringBuilder sb = new StringBuilder();
        if (bulls > 0 && cows > 0) {
            sb.append(bulls).append(bulls > 1 ? " bulls and " : " bull and ")
                    .append(cows).append(cows > 1 ? " cows" : " cow");
        } else if (bulls > 0) {
            sb.append(bulls).append(bulls > 1 ? " bulls" : " bull");
        } else if (cows > 0) {
            sb.append(cows).append(cows > 1 ? " cows" : " cow");
        } else {
            sb.append("0 bulls and 0 cows");
        }
        System.out.println(sb.toString());
    }

    void generateSecretNumber() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < secretCodeLength; i++) {
            char tempChar = POSSIBLE_CHARS.charAt((int) (Math.random() * possibleSymbolsNumber));
            if (!sb.toString().contains(String.valueOf(tempChar))) {
                sb.append(tempChar);
            } else {
                i--;
            }
        }
        secretNumber = sb.toString();
    }
}