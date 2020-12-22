package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DialogController {
    @FXML
    private ToggleGroup player1;
    @FXML
    private ToggleGroup player2;

    public Object[] processResult() {
        RadioButton player1Button = (RadioButton) player1.getSelectedToggle();
        RadioButton player2Button = (RadioButton) player2.getSelectedToggle();
        String player1Data = player1Button.getText().toUpperCase();
        String player2Data = player2Button.getText().toUpperCase();
        System.out.println("Player 1: " + player1Data);
        System.out.println("Player 2: " + player2Data);
        Object player1 = choosePlayer(player1Data);
        Object player2 = choosePlayer(player2Data);

        return new Object[]{player1, player2};
    }

    public Object choosePlayer(String player){
        User user = null;
        AI ai = null;
        switch (player) {
            case ("USER"):
                user = new User("User");
                break;
            case ("AI - LEVEL EASY"):
                ai = new AI(AI.Level.EASY);
                break;
            case ("AI - LEVEL MEDIUM"):
                ai= new AI(AI.Level.MEDIUM);
                break;
            case ("AI - LEVEL HARD"):
                ai = new AI(AI.Level.HARD);
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
