package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final String[][] gameField;
    private String turn = "X";
    private String winner;
    private List<int[]> winningCombo = new ArrayList<>();
    private static int xWinningsCounter = 0;
    private static int oWinningsCounter = 0;
    private static int drawsCounter = 0;

    public Board() {
        this.gameField = new String[3][3];
        generateGameField();
    }

    public void swapTurn(){
        turn = turn.equals("X") ? "O" : "X";
    }

    public String getTurn() {
        return turn;
    }

    private void generateGameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = " ";
            }
        }
    }

    public String[][] getGameField() {
        return gameField;
    }

    public String getSymbolAtField(int rowIndex, int columnIndex) {
        return gameField[rowIndex][columnIndex];
    }

    public boolean isFieldMarked(int rowIndex, int columnIndex) {
        return !this.gameField[rowIndex][columnIndex].isBlank();
    }

    public void markSymbol(int firstCoordinate, int secondCoordinate) {
        if (firstCoordinate > 3 || firstCoordinate < 0
                || secondCoordinate > 3 || secondCoordinate < 0 || isFieldMarked(firstCoordinate, secondCoordinate)) {
            return;
        }

        gameField[firstCoordinate][secondCoordinate] = turn;
        swapTurn();
    }

    public boolean checkStateOfTheGame() {
        if (isWinnerInRows() || isWinnerInColumns() || isWinnerInFirstDiagonal() || isWinnerInSecondDiagonal())
            return true;
        else return isBoardFilled();
    }

    private boolean isWinnerInRows() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals("X")) {
                    winningCombo.add(new int[]{i, j});
                    xInRowCounter++;
                } else if (gameField[i][j].equals("O")) {
                    winningCombo.add(new int[]{i, j});
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                winner = "X";
                xWinningsCounter++;
                return true;
            }
            if (oInRowCounter == 3) {
                winner = "O";
                oWinningsCounter++;
                return true;
            }
            winningCombo.clear();
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        return false;
    }

    public boolean isWinnerInColumns() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameField[i][j].equals("X")) {
                    winningCombo.add(new int[]{i, j});
                    xInRowCounter++;
                } else if (gameField[i][j].equals("O")) {
                    winningCombo.add(new int[]{i, j});
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                winner = "X";
                xWinningsCounter++;
                return true;
            }
            if (oInRowCounter == 3) {
                winner = "O";
                oWinningsCounter++;
                return true;
            }
            winningCombo.clear();
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        return false;
    }

    public boolean isWinnerInFirstDiagonal() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i && gameField[i][j].equals("X")) {
                    winningCombo.add(new int[]{i, j});
                    xInRowCounter++;
                } else if (j == i && gameField[i][j].equals("O")) {
                    winningCombo.add(new int[]{i, j});
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            winner = "X";
            xWinningsCounter++;
            return true;
        }
        if (oInRowCounter == 3) {
            winner = "O";
            oWinningsCounter++;
            return true;
        }

        winningCombo.clear();
        return false;
    }

    public boolean isWinnerInSecondDiagonal() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].equals("X")) {
                    winningCombo.add(new int[]{i, j});
                    xInRowCounter++;
                } else if (j == - i + 2 && gameField[i][j].equals("O")) {
                    winningCombo.add(new int[]{i, j});
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            winner = "X";
            xWinningsCounter++;
            return true;
        }
        if (oInRowCounter == 3) {
            winner = "O";
            oWinningsCounter++;
            return true;
        }

        winningCombo.clear();
        return false;
    }

    public boolean isBoardFilled() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(" ")) {
                    return false;
                }
            }
        }

        winner = " ";
        drawsCounter++;

        return true;
    }

    public String getWinner() {
        return winner;
    }

    public List<int[]> getWinningCombo() {
        return winningCombo;
    }

    public int getXWinningsCounter() {
        return xWinningsCounter;
    }

    public int getOWinningsCounter() {
        return oWinningsCounter;
    }

    public int getDrawsCounter() {
        return drawsCounter;
    }
}
