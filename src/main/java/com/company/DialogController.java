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
        return choosePlayersOfGame(player1Data, player2Data);
    }

    public Object[] choosePlayersOfGame(String player1, String player2){
        if(player1.equals("USER") && player2.equals("USER")) {
            return new Object[]{new User("User 1"), new User("User 2")};
        }
        if (player1.equals("USER") && player2.equals("AI - LEVEL EASY")) {
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.EASY);
            return new Object[]{new User("User 1"), opponentAI};
        }
        if (player1.equals("USER") && player2.equals("AI - LEVEL MEDIUM")) {
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.MEDIUM);
            return new Object[]{new User("User 1"), opponentAI};
        }
        if (player1.equals("USER") && player2.equals("AI - LEVEL HARD")) {
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.HARD);
            return new Object[]{new User("User 1"), opponentAI};
        }
        if (player1.equals("AI - LEVEL EASY") && player2.equals("USER")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.EASY);
            return new Object[]{playerAI, new User("User 2")};
        }
        if (player1.equals("AI - LEVEL MEDIUM") && player2.equals("AI - LEVEL USER")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.MEDIUM);
            return new Object[]{playerAI, new User("User 2")};
        }
        if (player1.equals("AI - LEVEL HARD") && player2.equals("USER")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.HARD);
            return new Object[]{playerAI, new User("User 2")};
        }
        if (player1.equals("AI - LEVEL EASY") && player2.equals("AI - LEVEL EASY")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.EASY);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.EASY);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL MEDIUM") && player2.equals("AI - LEVEL MEDIUM")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.MEDIUM);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.MEDIUM);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL HARD") && player2.equals("AI - LEVEL HARD")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.HARD);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.HARD);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL EASY") && player2.equals("AI - LEVEL MEDIUM")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.EASY);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.MEDIUM);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL MEDIUM") && player2.equals("AI - LEVEL EASY")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.MEDIUM);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.EASY);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL EASY") && player2.equals("AI - LEVEL HARD")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.EASY);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.HARD);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL HARD") && player2.equals("AI - LEVEL EASY")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.HARD);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.EASY);
            return new Object[]{playerAI, opponentAI};
        }
        if (player1.equals("AI - LEVEL MEDIUM") && player2.equals("AI - LEVEL HARD")) {
            AI playerAI = new AI();
            playerAI.setLevel(AI.Level.MEDIUM);
            AI opponentAI = new AI();
            opponentAI.setLevel(AI.Level.HARD);
            return new Object[]{playerAI, opponentAI};
        }

        AI playerAI = new AI();
        playerAI.setLevel(AI.Level.HARD);
        AI opponentAI = new AI();
        opponentAI.setLevel(AI.Level.MEDIUM);
        return new Object[]{playerAI, opponentAI};
    }

}
