package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {

    public enum Level{
        EASY,
        MEDIUM,
        HARD
    }

    private String aiSymbol;
    private final Level level;

    public AI(Level level) {
        this.level = level;
    }

    public String getAiSymbol() {
        return aiSymbol;
    }

    public void setAiSymbol(String aiSymbol) {
        this.aiSymbol = aiSymbol;
    }

    public int[] move(String[][] gameField) {
        int computerFirstCoordinate = 0;
        int computerSecondCoordinate = 0;

        String opponentSymbol = aiSymbol.toUpperCase().equals("X") ? "O" : "X";

        Random random = new Random();
        List<int[]> freeSpots = getAvailableSpots(gameField);

        switch (this.level) {
            case EASY:
                int[] easyMove = freeSpots.get(random.nextInt(freeSpots.size()));
                computerFirstCoordinate = easyMove[0];
                computerSecondCoordinate = easyMove[1];
                break;
            case MEDIUM:
                int[] computerMediumMoves = chooseNextMoveLevelMedium(gameField, aiSymbol);
                int[] opponentMediumMoves = chooseNextMoveLevelMedium(gameField, opponentSymbol);
                if (computerMediumMoves[0] == -1 && opponentMediumMoves[0] == -1) {
                    int[] mediumMove = freeSpots.get(random.nextInt(freeSpots.size()));
                    computerFirstCoordinate = mediumMove[0];
                    computerSecondCoordinate = mediumMove[1];
                } else if (computerMediumMoves[0] != -1 && opponentMediumMoves[0] == -1) {
                    computerFirstCoordinate = computerMediumMoves[0];
                    computerSecondCoordinate = computerMediumMoves[1];
                } else {
                    computerFirstCoordinate = opponentMediumMoves[0];
                    computerSecondCoordinate = opponentMediumMoves[1];
                }
                break;
            case HARD:
                if (freeSpots.size() == 9) {
                    int[] hardFirstMove = freeSpots.get(random.nextInt(freeSpots.size()));
                    computerFirstCoordinate = hardFirstMove[0];
                    computerSecondCoordinate = hardFirstMove[1];
                } else {
                    int[] computerHardMoves = bestMoveLevelHard(gameField);
                    computerFirstCoordinate = computerHardMoves[0];
                    computerSecondCoordinate = computerHardMoves[1];
                }
                break;
        }
        return new int[]{computerFirstCoordinate, computerSecondCoordinate};
    }

    public int[] chooseNextMoveLevelMedium(String[][] gameField, String symbol) {
        int inRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && j == 1 && gameField[i][2].equals(" ")) {
                    return new int[]{i, 2};
                }
                if (inRowCounter == 2 && j == 2 && gameField[i][1].equals(symbol) && gameField[i][0].equals(" ")) {
                    return new int[]{i, 0};
                }
                if (inRowCounter == 2 && j == 2 && gameField[i][1].equals(" ")) {
                    return new int[]{i, 1};
                }
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* In column check */
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && gameField[2][j].equals(" ")) {
                    return new int[]{2, j};
                }
                if (inRowCounter == 2 && i == 2 && gameField[1][j].equals(symbol) && gameField[0][j].equals(" ")) {
                    return new int[]{0, j};
                }
                if (inRowCounter == 2 && i == 2 && gameField[1][j].equals(" ")) {
                    return new int[]{1, j};
                }
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* Cross check 1st diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i && gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && j == 1 && gameField[2][2].equals(" ")) {
                    return new int[]{2, 2};
                }
                if (inRowCounter == 2 && i == 2 && j == 2 && gameField[1][1].equals(symbol) && gameField[0][0].equals(" ")) {
                    return new int[]{0, 0};
                }
                if (inRowCounter == 2 && i == 2 && j == 2 && gameField[1][1].equals(" ")) {
                    return new int[]{1, 1};
                }
            }
        }

        inRowCounter = 0;
        /* Cross check 2nd diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && j == 1 && gameField[2][0].equals(" ")) {
                    return new int[]{2, 0};
                }
                if (inRowCounter == 2 && i == 2 && j == 0 && gameField[1][1].equals(symbol) && gameField[0][2].equals(" ")) {
                    return new int[]{0, 2};
                }
                if (inRowCounter == 2 && i == 2 && j == 0 && gameField[1][1].equals(" ")) {
                    return new int[]{1, 1};
                }
            }
        }

        return new int[]{-1, -1};
    }

    public int[] bestMoveLevelHard(String[][] gameField) {
        int[] bestMove = new int[2];

        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(" ")) {
                    gameField[i][j] = aiSymbol;
                    int score = miniMax(gameField, false);
                    gameField[i][j] = " ";
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    public int miniMax(String[][] gameField, boolean isMaximizing){

        String opponentSymbol = aiSymbol.toUpperCase().equals("X") ? "O" : "X";

        if (checkIfWinning(gameField, aiSymbol)) {
            return 10;
        }
        else if (checkIfWinning(gameField, opponentSymbol)) {
            return -10;
        }
        else if (getAvailableSpots(gameField).size() == 0) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].equals(" ")) {
                        gameField[i][j] = aiSymbol;
                        int score = miniMax(gameField, false);
                        gameField[i][j] = " ";
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].equals(" ")) {
                        gameField[i][j] = opponentSymbol;
                        int score = miniMax(gameField, true);
                        gameField[i][j] = " ";
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public boolean checkIfWinning(String[][] gameField, String symbol) {
        int inRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
            }
            if (inRowCounter == 3) {
                return true;
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* In column check */
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
            }
            if (inRowCounter == 3) {
                return true;
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* Cross check 1st diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i && gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
            }
        }
        if (inRowCounter == 3) {
            return true;
        }

        inRowCounter = 0;
        /* Cross check 2nd diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].equals(symbol)) {
                    inRowCounter++;
                }
            }
        }
        return inRowCounter == 3;
    }

    public List<int[]> getAvailableSpots(String[][] gameField) {
        List<int[]> availableSpots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(" ")) {
                    availableSpots.add(new int[]{i, j});
                }
            }
        }
        return availableSpots;
    }

}
