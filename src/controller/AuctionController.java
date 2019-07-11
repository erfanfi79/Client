package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import packet.clientPacket.ClientAuctionPacket;

import java.io.IOException;

public class AuctionController {
    private double x, y;
    @FXML
    private AnchorPane givenPricetxt;

    @FXML
    private Label labelhighestPrice;

    @FXML
    private Label labelRemainingTime;

    @FXML
    private Pane cardPane;
    @FXML
    private TextField txtPrice;

    @FXML
    void sendPrice() {
        ClientAuctionPacket clientAuctionPacket = new ClientAuctionPacket();
        clientAuctionPacket.setInAuctionMenu(true);
        try {
            int price = Integer.parseInt(txtPrice.getText());
            clientAuctionPacket.setPrice(price);
            Controller.getInstance().clientListener.sendPacketToServer(clientAuctionPacket);
        } catch (Exception e) {
        }
    }

    @FXML
    void goBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/shopMenuView/ShopMenuView.fxml"));
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

    public void initializeAuction() {

    }
}
