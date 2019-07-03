package controller;

import javafx.animation.PauseTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Popup{
    @FXML
    Label errorLabel;

    public void showMessage(String message) {
        try {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/PopupView.fxml"));
            Parent root = fxmlLoader.load();
            Popup popup = fxmlLoader.getController();
            popup.errorLabel.setText(message);
            Scene dialogScene = new Scene(root);
            dialogScene.setFill(Color.TRANSPARENT);
            dialog.setScene(dialogScene);
            dialog.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> dialog.close());
            delay.play();
        } catch (Exception e) {
        }
    }
}
