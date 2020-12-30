package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DialogController {
    @FXML
    private ToggleGroup opponent;
    @FXML
    private ToggleGroup userSymbol;

    private String userSymbolString;

    public Object processResult() {
        RadioButton userSymbolButton = (RadioButton) userSymbol.getSelectedToggle();
        userSymbolString = userSymbolButton.getText();

        RadioButton opponentButton = (RadioButton) opponent.getSelectedToggle();
        String opponentData = opponentButton.getText().toUpperCase();

        return choosePlayer(opponentData);
    }

    public Object choosePlayer(String player){
        String aiSymbol = userSymbolString.equals("X") ? "O" : "X";
        User user = null;
        AI ai = null;
        switch (player) {
            case ("USER"):
                user = new User("User");
                break;
            case ("AI - LEVEL EASY"):
                ai = new AI(AI.Level.EASY);
                ai.setAiSymbol(aiSymbol);
                break;
            case ("AI - LEVEL MEDIUM"):
                ai = new AI(AI.Level.MEDIUM);
                ai.setAiSymbol(aiSymbol);
                break;
            case ("AI - LEVEL HARD"):
                ai = new AI(AI.Level.HARD);
                ai.setAiSymbol(aiSymbol);
                break;
        }
        Object playerObject;
        if (ai != null) {
            playerObject = ai;
        } else {
            playerObject = user;
        }

        return playerObject;
    }

}
