package com.company;

public class Board {

    private final String[][] gameField;
    private String turn = "X";
    private String winner;

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
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals("X")) {
                    xInRowCounter++;
                } else if (gameField[i][j].equals("O")) {
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                winner = "X";
                return true;
            }
            if (oInRowCounter == 3) {
                winner = "O";
                return true;
            }
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        xInRowCounter = 0;
        oInRowCounter = 0;
        /* In column check */
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameField[i][j].equals("X")) {
                    xInRowCounter++;
                } else if (gameField[i][j].equals("O")) {
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                winner = "X";
                return true;
            }
            if (oInRowCounter == 3) {
                winner = "O";
                return true;
            }
            xInRowCounter = 0;
            oInRowCounter = 0;
        }

        xInRowCounter = 0;
        oInRowCounter = 0;
        /* Cross check 1st diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i && gameField[i][j].equals("X")) {
                    xInRowCounter++;
                } else if (j == 2 && gameField[i][j].equals("O")) {
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            winner = "X";
            return true;
        }
        if (oInRowCounter == 3) {
            winner = "O";
            return true;
        }

        xInRowCounter = 0;
        oInRowCounter = 0;
        /* Cross check 2nd diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].equals("X")) {
                    xInRowCounter++;
                } else if (j == - i + 2 && gameField[i][j].equals("O")) {
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            winner = "X";
            return true;
        }
        if (oInRowCounter == 3) {
            winner = "O";
            return true;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(" ")) {
                    return false;
                }
            }
        }

        winner = " ";
        return true;
    }

    public String getWinner() {
        return winner;
    }
}
