package org.cloudexel;

import java.util.Scanner;

public class Main {
    static String dataCells = "_________";
    static char user = 'X';
    static String gameStatus = "Game not finished";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            showGameBoard();
            do {
                System.out.print("Enter the coordinates: ");
            } while (userInputNotValid(scanner.nextLine()));
            setGameStatus();
        } while ("Game not finished".equals(gameStatus));
        showGameBoard();
        System.out.println(gameStatus);
    }

    private static boolean userInputNotValid(String userInput) {
        String withoutDigits = userInput.replaceAll("[\\d.]", " ").trim();
        if (!withoutDigits.isBlank() || !withoutDigits.isEmpty()) {
            System.out.println("You should enter numbers!");
            return true;
        }
        int row;
        int column;
        String[] onlyDigits = userInput.replace("\r", "").replace("\n", "")
                .replaceAll("[^\\d.\\s]", " ").trim().split("\\s+");
        if (onlyDigits.length > 1) {
            row = Integer.parseInt(onlyDigits[0]);
            column = Integer.parseInt(onlyDigits[1]);
            if (row < 1 || row > 3 || column < 1 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                return true;
            } else {
                int idx = (row - 1) * 3 + column - 1;
                if (dataCells.charAt(idx) == '_') {
                    dataCells = dataCells.substring(0, idx)
                            + user + dataCells.substring(idx + 1);
                    return false;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                    return true;
                }
            }
        } else {
            System.out.println("You should enter numbers!");
            return true;
        }
    }

    public static void showGameBoard() {
        StringBuilder grid = new StringBuilder();
        grid.append("---------\n");
        for (int i = 0; i < 9; i += 3) {
            grid.append("| ")
                    .append(dataCells.charAt(i)).append(" ")
                    .append(dataCells.charAt(i + 1)).append(" ")
                    .append(dataCells.charAt(i + 2)).append(" ")
                    .append("|\n");
        }
        grid.append("---------\n");
        System.out.print(grid);
    }

    private static void setGameStatus() {
        int xs = dataCells.replaceAll("[^X.]", "").length();
        int os = dataCells.replaceAll("[^O.]", "").length();
        if (Math.abs(xs - os) > 1) {
            gameStatus = "Impossible";
        } else {
            user = xs > os ? 'O' : 'X';
        }
        char winner = '_';
        int numberOfWinners = 0;
        // check on rows
        for (int i = 0; i < 7; i += 3) {
            if (dataCells.charAt(i) != '_'
                    && dataCells.charAt(i) == dataCells.charAt(i + 1)
                    && dataCells.charAt(i + 1) == dataCells.charAt(i + 2)) {
                if (numberOfWinners == 0) {
                    winner = dataCells.charAt(i);
                }
                numberOfWinners++;
            }
        }
        // check on columns
        for (int i = 0; i < 3; i++) {
            if (dataCells.charAt(i) != '_'
                    && dataCells.charAt(i) == dataCells.charAt(i + 3)
                    && dataCells.charAt(i + 3) == dataCells.charAt(i + 6)) {
                if (numberOfWinners == 0) {
                    winner = dataCells.charAt(i);
                }
                numberOfWinners++;
            }
        }
        // check on diagonals
        if (dataCells.charAt(4) != '_'
                && (dataCells.charAt(0) == dataCells.charAt(4) && dataCells.charAt(4) == dataCells.charAt(8)
                || (dataCells.charAt(2) == dataCells.charAt(4) && dataCells.charAt(4) == dataCells.charAt(6)))) {
            if (numberOfWinners == 0) {
                winner = dataCells.charAt(4);
            }
            numberOfWinners++;
        }

        if (numberOfWinners > 1) {
            gameStatus = "Impossible";
        } else if (numberOfWinners == 0) {
            if (dataCells.contains("_")) {
                gameStatus = "Game not finished";
            } else {
                gameStatus = "Draw";
            }
        } else {
            gameStatus = winner + " wins";
        }
    }
}
