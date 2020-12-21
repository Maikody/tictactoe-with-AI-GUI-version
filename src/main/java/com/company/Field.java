package com.company;

public class Field {

    private String symbol;

    public Field(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isMarked() {
        return !this.symbol.isBlank();
    }
}
