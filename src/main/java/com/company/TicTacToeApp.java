package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class TicTacToeApp extends Application {
    private static Board board;
    private static GridPane tiles;
    private static AnimationTimer gameTimer;

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
        URL iconURL = Paths.get("largeIcon.png").toUri().toURL();
        primaryStage.getIcons().add(new Image(iconURL.toString()));

        primaryStage.show();
        root.requestFocus();

        showNewGameDialog();
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
    public void showNewGameDialog() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Game");
        dialog.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            URL url = Paths.get("src/main/resources/NewGameDialog.fxml").toUri().toURL();
            fxmlLoader.setLocation(url);
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Object opponent = controller.processResult();
            startNewGame(opponent);
        }
        else {
            for (Node child : tiles.getChildren()) {
                Tile t = (Tile) child;
                t.setDisable(true);
            }
        }

    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    private void moveAI(AI ai) {
        int[] move = ai.move(board.getGameField());
        board.markSymbol(move[0], move[1]);
        for (Node child : tiles.getChildren()) {
            if (GridPane.getRowIndex(child) == move[0] && GridPane.getColumnIndex(child) == move[1]) {
                Tile t = (Tile) child;
                t.setText(board.getSymbolAtField(move[0], move[1]));
                return;
            }
        }
        board.swapTurn();
    }

    public void gameLoop(Object opponent) {
        if (opponent instanceof AI) {
            AI aiPlayer = (AI) opponent;
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (board.checkStateOfTheGame()) {
                        endGame();
                    } else {
                        if(board.getTurn().equals("X") && aiPlayer.getAiSymbol().equals("X"))
                            moveAI(aiPlayer);
                        else if(board.getTurn().equals("O") && aiPlayer.getAiSymbol().equals("O"))
                            moveAI(aiPlayer);
                    }
                }
            };
            gameTimer.start();
        } else if (opponent instanceof User) {
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (board.checkStateOfTheGame()) {
                        endGame();
                    }
                }
            };
            gameTimer.start();
        }
    }

    public void paintWinningCombo(List<int[]> winningCombo){
        for (Node child : tiles.getChildren()) {
            for(int[] coords: winningCombo) {
                if (GridPane.getRowIndex(child) == coords[0]
                        && GridPane.getColumnIndex(child) == coords[1]) {
                    Tile t = (Tile) child;
                    t.setStyle("-fx-text-fill: green; -fx-base: #b6e7c9;");
                }
            }
        }
    }

    private void startNewGame(Object opponent){
        generatePlayBoard();
        root.requestFocus();
        gameLoop(opponent);
    }

    private void endGame() {
        gameTimer.stop();

        Label scores = (Label) root.getBottom();
        scores.setText("X wins: " + board.getxWinningsCounter()
                + "\tO wins: " + board.getoWinningsCounter() + "\tDraws: " + board.getDrawsCounter() );

        paintWinningCombo(board.getWinningCombo());
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, "", new ButtonType("New Game"));
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText(null);

        String winner = board.getWinner();
        if (winner.equals(" ")) {
            gameOverAlert.setContentText("Draw!");
        } else {
            gameOverAlert.setContentText(winner + " wins!");
        }

        gameOverAlert.setOnHidden(e -> {
            gameOverAlert.close();
            showNewGameDialog();
        });

        gameOverAlert.show();
    }

}
