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

    private static final String USER = "USER";
    private static final String AIEASY = "AI - LEVEL EASY";
    private static final String AIMEDIUM = "AI - LEVEL MEDIUM";
    private static final String AIHARD = "AI - LEVEL HARD";

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
            case (USER):
                user = new User("User");
                break;
            case (AIEASY):
                ai = new AI(AI.Level.EASY);
                ai.setAiSymbol(aiSymbol);
                break;
            case (AIMEDIUM):
                ai = new AI(AI.Level.MEDIUM);
                ai.setAiSymbol(aiSymbol);
                break;
            case (AIHARD):
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
