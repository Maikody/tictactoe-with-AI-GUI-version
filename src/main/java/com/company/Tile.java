package com.company;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tile extends Button {
    private final int rowIndex;
    private final int columnIndex;

    public Tile(int rowIndex, int columnIndex, String symbol, Board board) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;

        setTileProperties(symbol);

        setOnMouseClicked(event -> {
            board.markSymbol(this.rowIndex, this.columnIndex);
            setText(board.getSymbolAtField(this.rowIndex, this.columnIndex));
        });
    }

    void setTileProperties(String symbol) {
        setPrefSize(200.0, 200.0);
        setAlignment(Pos.CENTER);
        setFont(Font.font(80));
        setTextAlignment(TextAlignment.CENTER);
        setText(symbol);
    }

}