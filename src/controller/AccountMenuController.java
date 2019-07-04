package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packet.clientPacket.ClientLoginPacket;
import packet.serverPacket.ServerLogPacket;
import request.accountMenuRequest.AccountError;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountMenuController{
    private double x, y;
    @FXML
    private TextField txtUsername;

    @FXML
    private Label loginError;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void signIn() {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            loginError.setText(AccountError.FIELDS_CAN_NOT_BE_EMPTY.toString());
            return;
        }
        Controller.getInstance().clientListener.sendPacket(new ClientLoginPacket(userName, password, true));
    }

    @FXML
    void signUp() {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            loginError.setText(AccountError.FIELDS_CAN_NOT_BE_EMPTY.toString());
            return;
        }
        Controller.getInstance().clientListener.sendPacket(new ClientLoginPacket(userName, password, false));
    }

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void showError(ServerLogPacket serverLogPacket){
        if (serverLogPacket.isSuccessful())
            gotoStartMenu();
        else loginError.setText(serverLogPacket.getLog());
    }
    public void gotoStartMenu() {
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
}
