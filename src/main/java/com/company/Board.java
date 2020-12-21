package com.company;

public class Board {

    private final Field[][] gameField;
    private boolean opponentTurn = false;

    public Board() {
        this.gameField = new Field[3][3];
        generateGameField();
    }

    private void generateGameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = new Field(" ");
            }
        }
    }

    public Field[][] getGameField() {
        return gameField;
    }

    public String getSymbolAtField(int rowIndex, int columnIndex) {
        return gameField[rowIndex][columnIndex].getSymbol();
    }

    public void setSymbolAtField(int rowIndex, int columnIndex, String symbol) {
        this.gameField[rowIndex][columnIndex].setSymbol(symbol);
    }

    public boolean isFieldMarked(int rowIndex, int columnIndex) {
        return this.gameField[rowIndex][columnIndex].isMarked();
    }

    public boolean markSymbol(int firstCoordinate, int secondCoordinate) {
        if (firstCoordinate > 3 || firstCoordinate < 0
                || secondCoordinate > 3 || secondCoordinate < 0 || !gameField[firstCoordinate][secondCoordinate].getSymbol().equals(" ")){
            return false;
        }

        int xCounter = 0;
        int yCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals("X")) {
                    xCounter++;
                } else if (gameField[i][j].getSymbol().equals("O")) {
                    yCounter++;
                }
            }
        }

        String symbol;
        if (xCounter == yCounter) {
            symbol = "X";
        } else {
            symbol = "O";
        }

        gameField[firstCoordinate][secondCoordinate].setSymbol(symbol);
        opponentTurn = !opponentTurn;

        return true;
    }

    public boolean isOpponentTurn() {
        return opponentTurn;
    }

    public boolean checkStateOfTheGame() {
        int xInRowCounter = 0;
        int oInRowCounter = 0;

        /* In row check */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals("X")) {
                    xInRowCounter++;
                } else if (gameField[i][j].getSymbol().equals("O")) {
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                System.out.println("X wins\n");
                return true;
            }
            if (oInRowCounter == 3) {
                System.out.println("O wins\n");
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
                if (gameField[i][j].getSymbol().equals("X")) {
                    xInRowCounter++;
                } else if (gameField[i][j].getSymbol().equals("O")) {
                    oInRowCounter++;
                }
            }
            if (xInRowCounter == 3) {
                System.out.println("X wins\n");
                return true;
            }
            if (oInRowCounter == 3) {
                System.out.println("O wins\n");
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
                if (j == i && gameField[i][j].getSymbol().equals("X")) {
                    xInRowCounter++;
                } else if (j == 2 && gameField[i][j].getSymbol().equals("O")) {
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            System.out.println("X wins\n");
            return true;
        }
        if (oInRowCounter == 3) {
            System.out.println("O wins\n");
            return true;
        }

        xInRowCounter = 0;
        oInRowCounter = 0;
        /* Cross check 2nd diagonal */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == -i + 2 && gameField[i][j].getSymbol().equals("X")) {
                    xInRowCounter++;
                } else if (j == - i + 2 && gameField[i][j].getSymbol().equals("O")) {
                    oInRowCounter++;
                }
            }
        }
        if (xInRowCounter == 3) {
            System.out.println("X wins\n");
            return true;
        }
        if (oInRowCounter == 3) {
            System.out.println("O wins\n");
            return true;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].getSymbol().equals(" ")) {
                    return false;
                }
            }
        }

        System.out.println("Draw\n");
        return true;
    }

}
