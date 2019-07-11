package controller;

import controller.MediaController.GameSfxPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Card;
import packet.clientPacket.ClientAuctionPacket;
import packet.serverPacket.ServerAuctionPacket;

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
        new GameSfxPlayer().onSelect();
        ClientAuctionPacket clientAuctionPacket = new ClientAuctionPacket();
        clientAuctionPacket.setInAuctionMenu(true);
        try {
            int high = Integer.parseInt(labelhighestPrice.getText());
            int price = Integer.parseInt(txtPrice.getText());
            if (price <= high) {
                new Popup().showMessage("Your price can not be less than highest");
                return;
            }
            clientAuctionPacket.setPrice(price);
            Controller.getInstance().clientListener.sendPacketToServer(clientAuctionPacket);
        } catch (Exception e) {
        }
    }

    @FXML
    void goBack() {
        new GameSfxPlayer().onSelect();
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

    public void initializeAuction(ServerAuctionPacket packet) {
        showCard(packet.card);
        labelhighestPrice.setText(String.valueOf(packet.highestPrice));
        long time = packet.startTime;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() - time < 180000) {
                    labelRemainingTime.setText(String.valueOf((int) (System.currentTimeMillis() - time) / 1000));
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public void showCard(Card card) {
        cardPane.getChildren().removeAll(cardPane.getChildren());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            switch (card.getType()) {
                case USABLE_ITEM:
                    fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/Item.fxml"));
                    break;
                case MINION:
                    fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/MinionCard.fxml"));
                    break;
                case HERO:
                    fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/General.fxml"));
                    break;
                case SPELL:
                    fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/SpellCard.fxml"));
                    break;
            }
            Node node = fxmlLoader.load();
            CardController cardController = fxmlLoader.getController();
            cardController.setInformation(card);
            cardPane.getChildren().add(node);
        } catch (IOException e) {

        }

    }
}
