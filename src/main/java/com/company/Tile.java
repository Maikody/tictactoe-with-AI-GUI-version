package com.company;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tile extends Button {
    private final int rowIndex;
    private final int columnIndex;
    private String symbol;

    public Tile(int rowIndex, int columnIndex, String symbol, Board board) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.symbol = symbol;

        setPrefSize(200.0, 200.0);
        setAlignment(Pos.CENTER);
        setFont(Font.font(80));
        setTextAlignment(TextAlignment.CENTER);
        setText("" + this.symbol);

        setOnMouseClicked(event ->{
//            if(!board.isOpponentTurn()) {
                board.markSymbol(this.rowIndex, this.columnIndex);
                setText("" + board.getSymbolAtField(this.rowIndex, this.columnIndex));
//            }
        });
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}