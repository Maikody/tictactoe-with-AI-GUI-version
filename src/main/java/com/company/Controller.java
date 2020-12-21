package com.company;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class Controller {
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public MenuItem newGameMenu;
    @FXML
    public MenuItem exitMenu;

    @FXML
    public void showNewGameDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
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

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
          DialogController controller = fxmlLoader.getController();
          controller.processResult();
        }
    }

    @FXML
    public void exit() {

    }


}
