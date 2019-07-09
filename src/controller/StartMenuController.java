package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packet.clientPacket.ClientEnum;
import packet.clientPacket.ClientEnumPacket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {
    private double x, y;
    @FXML
    private Button btnSave;

    @FXML
    private Label money;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnShop;

    @FXML
    private Button btnCollection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.getInstance().currentController=this;
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.GET_MONEY));
    }

    @FXML
    void mouseIn(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            if (button.getText().trim().isEmpty())
                button.setOpacity(1);
            else
                button.setStyle("-fx-font-size: 24 ;-fx-background-color: transparent;-fx-font-style:italic;-fx-font-weight:bold ");
        }
    }

    @FXML
    void mouseOut(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            if (button.getText().trim().isEmpty())
                button.setOpacity(0.6);
            else
                button.setStyle("-fx-font-size: 20 ;-fx-background-color: transparent;-fx-font-style:italic;-fx-font-weight:bold");
        }
    }

    @FXML
    void gotoMatchHistory() {
        openPage("../view/MatchHistoryView.fxml");
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.MATCH_HISTORY));

    }

    @FXML
    void gotoLeaderboard() {
        openPage("../view/LeaderBoardView.fxml");
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.LEADER_BOARD));


    }

    @FXML
    void gotoSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/CustomCard.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {

                Controller.stage.setX(event.getScreenX() - x);
                Controller.stage.setY(event.getScreenY() - y);

            });
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
        }
    }

    void showMoney(String money) {
        this.money.setText(money);
    }

    @FXML
    void gotoBattleMenu() {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.CHECK_DECK));

    }

    public void validDeckToEnterBattleMenu() {
        openPage("../view/battleMenuView/BattleMenuView.fxml");
    }

    @FXML
    void gotoCollectionMenu() {
        openPage("../view/collectionMenuView/CollectionMenuView.fxml");
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.COLLECTION));
    }

    @FXML
    void gotoShopMenu() {
        openPage("../view/shopMenuView/ShopMenuView.fxml");
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.SHOP));

    }

    @FXML
    void gotoChatroom() {
        openPage("../view/ChatRoom.fxml");
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.CHAT_ROOM));
    }
    @FXML
    void save() {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.SAVE));
    }

    @FXML
    void exit(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void openPage(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            Parent root = fxmlLoader.load();
            Controller.getInstance().currentController = fxmlLoader.getController();
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
