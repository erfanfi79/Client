package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packet.clientPacket.ClientLoginPacket;
import packet.serverPacket.ServerLogPacket;
import request.accountMenuRequest.AccountError;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountMenuController implements Initializable {
    private double x, y;
    @FXML
    private TextField txtUsername;

    @FXML
    private Label loginError;

    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.getInstance().currentController = this;
    }

    @FXML
    void signIn() {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            loginError.setText(AccountError.FIELDS_CAN_NOT_BE_EMPTY.toString());
            return;
        }
        Controller.getInstance().clientListener.sendPacketToServer(new ClientLoginPacket(userName, password, true));
    }

    @FXML
    void signUp() {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            loginError.setText(AccountError.FIELDS_CAN_NOT_BE_EMPTY.toString());
            return;
        }
        Controller.getInstance().clientListener.sendPacketToServer(new ClientLoginPacket(userName, password, false));
    }

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void showError(ServerLogPacket serverLogPacket) {
        loginError.setText(serverLogPacket.getLog());
    }

    public void gotoStartMenu() {
        Controller.getInstance().gotoStartMenu();
    }
}
