package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

public class TicTacToeApp extends Application {
    private static Board board;

    private static GridPane tiles;

    @FXML
    public BorderPane root;
    @FXML
    public MenuItem newGameMenu;
    @FXML
    public MenuItem exitMenu;


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Paths.get("src/main/java/UI.fxml").toUri().toURL();
        root = FXMLLoader.load(url);
        generatePlayBoard();
        primaryStage.setTitle("TicTacToe Game");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();

        Object[] players = showNewGameDialog();
        System.out.println(players[0].getClass().getSimpleName());
        System.out.println(players[1].getClass().getSimpleName());

        gameLoop(players);
    }

    private void generatePlayBoard() {
        board = new Board();
        tiles = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile(i, j, "", board);
                GridPane.setConstraints(tile, j, i);
                tiles.getChildren().add(tile);
            }
        }
        root.setCenter(tiles);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public Object[] showNewGameDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            URL url = Paths.get("src/main/resources/NewGameDialog.fxml").toUri().toURL();
            fxmlLoader.setLocation(url);
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Object[] players = new Object[2];
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            players = controller.processResult();
        }

        generatePlayBoard();

        return players;
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    private void moveAI(AI ai, String symbol) {
        int[] move = ai.move(board.getGameField(), symbol);
        board.markSymbol(move[0], move[1]);
        for (Node child : tiles.getChildren()) {
                if (GridPane.getRowIndex(child) == move[0]
                    && GridPane.getColumnIndex(child) == move[1]) {
                    Tile t = (Tile) child;
                    t.setText("" + board.getSymbolAtField(move[0], move[1]));
                    return;
            }
        }
    }

    private void gameLooop() {
        AI ai = new AI();
        ai.setLevel(AI.Level.EASY);
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (board.checkStateOfTheGame()) {
                    System.out.println("End of the game!");
                } else {
                    if (board.isOpponentTurn()) {
                        moveAI(ai, "O");
                    }
                }
            }
        }.start();
    }

    public void gameLoop(Object[] players) {
        if (players[0] instanceof AI && players[1] instanceof AI) {
            AI player1 = (AI) players[0];
            AI player2 = (AI) players[1];
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (board.checkStateOfTheGame()) {
                        System.out.println("End of the game!");
                    } else {
                        if (!board.isOpponentTurn()) {
                            moveAI(player1,"X");
                        } else {
                            moveAI(player2,"O");
                        }
                    }
                }
            }.start();
        } else if (players[0] instanceof User && players[1] instanceof AI) {
            AI player2 = (AI) players[1];
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (board.checkStateOfTheGame()) {
                        System.out.println("End of the game!");
                    } else {
                        if (board.isOpponentTurn()) {
                            moveAI(player2,"O");
                        }
                    }
                }
            }.start();
        } else if (players[0] instanceof AI && players[1] instanceof User) {
            AI player1 = (AI) players[0];
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (board.checkStateOfTheGame()) {
                        System.out.println("End of the game!");
                    } else {
                        if (!board.isOpponentTurn()) {
                            moveAI(player1,"X");
                        }
                    }
                }
            }.start();
        } else if (players[0] instanceof User && players[1] instanceof User) {
            System.out.println("Both users");
        }

    }

}
