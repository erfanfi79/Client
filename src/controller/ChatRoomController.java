package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable {
    double x,y;
    @FXML
    private TextField txtMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label label=new Label();
        label.setText("salam");
        Label label1=new Label();
        label1.setText("bye bisdgjal;lgk");
        vBoxChat.getChildren().addAll(label,label1);
    }

    @FXML
    private VBox vBoxChat;
    @FXML
    void gotoStartMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/StartMenuView.fxml"));
            Scene scene = new Scene(root);
            scene.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {

                Controller.stage.setX(event.getScreenX() - x);
                Controller.stage.setY(event.getScreenY() - y);

            });
            Controller.stage.setScene(scene);
        } catch (IOException e) {
        }

    }

    @FXML
    void sendMessage(ActionEvent event) {

    }
}
