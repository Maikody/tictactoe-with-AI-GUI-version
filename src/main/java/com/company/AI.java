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

    Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    public int[] move(Field[][] gameField, String symbol) {

        System.out.println("Move by level \""+ level.name().toLowerCase() +"\":");
        int computerFirstCoordinate;
        int computerSecondCoordinate;

        String opponentSymbol;
        if(symbol.equals("X")) {
            opponentSymbol = "O";
        } else {
            opponentSymbol = "X";
        }

        Random random = new Random();

        if (this.level == Level.EASY) {
            while (true) {
                computerFirstCoordinate = random.nextInt(3);
                computerSecondCoordinate = random.nextInt(3);
                if (!gameField[computerFirstCoordinate][computerSecondCoordinate].getSymbol().equals(" ")) {
                    continue;
                }
                break;
            }
        } else if (this.level == Level.MEDIUM) {
            int[] computerMoves = chooseNextMoveLevelMedium(gameField, symbol);
            int[] opponentMoves = chooseNextMoveLevelMedium(gameField, opponentSymbol);
            if (computerMoves[0] == 0 && opponentMoves[0] == 0) {
                while(true) {
                    computerFirstCoordinate = random.nextInt(3);
                    computerSecondCoordinate = random.nextInt(3);
                    if (!gameField[computerFirstCoordinate][computerSecondCoordinate].getSymbol().equals(" ")) {
                        continue;
                    }
                    break;
                }
            } else if (computerMoves[0] != 0 && opponentMoves[0] == 0) {
                computerFirstCoordinate = computerMoves[0];
                computerSecondCoordinate = computerMoves[1];

            }
            else {
                computerFirstCoordinate = opponentMoves[0];
                computerSecondCoordinate = opponentMoves[1];
            }
        } else {
            int[] computerMoves = bestMoveLevelHard(gameField, symbol);
            computerFirstCoordinate = computerMoves[0];
            computerSecondCoordinate = computerMoves[1];
        }
        return new int[]{computerFirstCoordinate, computerSecondCoordinate};
    }

    public int[] chooseNextMoveLevelMedium(Field[][] gameField, String symbol) {
        int inRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && j == 1 && gameField[i][2].getSymbol().equals(" ")) {
                    return new int[]{i, 2};
                }
                if (inRowCounter == 2 && j == 2 && gameField[i][1].getSymbol().equals(symbol) && gameField[i][0].getSymbol().equals(" ")) {
                    return new int[]{i, 0};
                }
                if (inRowCounter == 2 && j == 2 && gameField[i][1].getSymbol().equals(" ")) {
                    return new int[]{i, 1};
                }
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* In column check */
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameField[i][j].getSymbol().equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && gameField[2][j].getSymbol().equals(" ")) {
                    return new int[]{2, j};
                }
                if (inRowCounter == 2 && i == 2 && gameField[1][j].getSymbol().equals(symbol) && gameField[0][j].getSymbol().equals(" ")) {
                    return new int[]{0, j};
                }
                if (inRowCounter == 2 && i == 2 && gameField[1][j].getSymbol().equals(" ")) {
                    return new int[]{1, j};
                }
            }
            inRowCounter = 0;
        }

        inRowCounter = 0;
        /* Cross check 1st diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i * 2 && gameField[i][j].getSymbol().equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && j == 1 && gameField[2][2].getSymbol().equals(" ")) {
                    return new int[]{2, 2};
                }
                if (inRowCounter == 2 && i == 2 && j == 2 && gameField[1][1].getSymbol().equals(symbol) && gameField[0][0].getSymbol().equals(" ")) {
                    return new int[]{0, 0};
                }
                if (inRowCounter == 2 && i == 2 && j == 2 && gameField[1][1].getSymbol().equals(" ")) {
                    return new int[]{1, 1};
                }
            }
        }

        inRowCounter = 0;
        /* Cross check 2nd diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].getSymbol().equals(symbol)) {
                    inRowCounter++;
                }
                if (inRowCounter == 2 && i == 1 && j == 1 && gameField[2][0].getSymbol().equals(" ")) {
                    return new int[]{2, 0};
                }
                if (inRowCounter == 2 && i == 2 && j == 0 && gameField[1][1].getSymbol().equals(symbol) && gameField[0][2].getSymbol().equals(" ")) {
                    return new int[]{0, 2};
                }
                if (inRowCounter == 2 && i == 2 && j == 0 && gameField[1][1].getSymbol().equals(" ")) {
                    return new int[]{1, 1};
                }
            }
        }

        return new int[]{0, 0};
    }

    public int[] bestMoveLevelHard(Field[][] gameField, String symbol){
        int[] move = new int[2];

        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(" ")) {
                    gameField[i][j].setSymbol(symbol);
                    int score;
                    if(symbol.equals("X")) {
                        score = minimaxX(gameField, false);
                    } else {
                        score = minimaxO(gameField, false);
                    }
                    gameField[i][j].setSymbol(" ");
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }

    public int minimaxX(Field[][] gameField, boolean isMaximizing){

        List<int[]> availableSpots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(" ")) {
                    availableSpots.add(new int[]{i, j});
                }
            }
        }

        if (checkIfWinning(gameField, "X")) {
            return 10;
        }
        else if (checkIfWinning(gameField, "O")) {
            return -10;
        }
        else if (availableSpots.size() == 0) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].getSymbol().equals(" ")) {
                        gameField[i][j].setSymbol("X");
                        int score = minimaxX(gameField, false);
                        gameField[i][j].setSymbol(" ");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].getSymbol().equals(" ")) {
                        gameField[i][j].setSymbol("O");
                        int score = minimaxX(gameField, true);
                        gameField[i][j].setSymbol(" ");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }


    public int minimaxO(Field[][] gameField, boolean isMaximizing){

        List<int[]> availableSpots = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(" ")) {
                    availableSpots.add(new int[]{i, j});
                }
            }
        }

        if (checkIfWinning(gameField,"O")) {
            return 10;
        }
        else if (checkIfWinning(gameField,"X")) {
            return -10;
        }
        else if (availableSpots.size() == 0) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].getSymbol().equals(" ")) {
                        gameField[i][j].setSymbol("O");
                        int score = minimaxO(gameField, false);
                        gameField[i][j].setSymbol(" ");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField[i][j].getSymbol().equals(" ")) {
                        gameField[i][j].setSymbol("X");
                        int score = minimaxO(gameField, true);
                        gameField[i][j].setSymbol(" ");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    public boolean checkIfWinning(Field[][] gameField, String symbol) {
        int inRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(symbol)) {
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
                if (gameField[i][j].getSymbol().equals(symbol)) {
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
                if (j == i && gameField[i][j].getSymbol().equals(symbol)) {
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
                if (j == -i + 2 && gameField[i][j].getSymbol().equals(symbol)) {
                    inRowCounter++;
                }
            }
        }
        if (inRowCounter == 3) {
            return true;
        }

        return false;
    }

}
