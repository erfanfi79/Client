package controller;

import controller.MediaController.GameSfxPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.Card;
import models.Collection;
import packet.clientPacket.ClientBuyAndSellPacket;
import view.shopMenuView.ShopError;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopController implements Initializable {
    private Collection shopCollection, myCollection;
    private double x, y;
    Node[] shopNodes;
    @FXML
    private TextField searchCArdName;

    @FXML
    private Label money;

    @FXML
    private Label labelErrorInShop;

    @FXML
    private RadioButton optSearchInShop;

    @FXML
    private HBox hBox2;

    @FXML
    private HBox hBox1;

    @FXML
    private RadioButton optSearchInCollection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.getInstance().shopController = this;
    }

    @FXML
    void showShop(ActionEvent event) {
        new GameSfxPlayer().onSelect();
        showCards(shopCollection.getCards());
    }

    @FXML
    void showCollection(ActionEvent event) {
        new GameSfxPlayer().onSelect();
        showCards(myCollection.getCards());
    }

    @FXML
    void mouseIn(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(1);
        }
    }

    @FXML
    void mouseOut(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(0.6);
        }
    }

    public void showMoney(String bill) {
        money.setText(bill);
    }

    @FXML
    void search() {
        new GameSfxPlayer().onSelect();
        if (searchCArdName.getText().trim().isEmpty()) {
            if (optSearchInCollection.isSelected())
                showCards(myCollection.getCards());
            else
                showCards(shopCollection.getCards());
        } else {
            if (optSearchInCollection.isSelected())
                searchCollection(searchCArdName.getText(), myCollection);
            else
                searchCollection(searchCArdName.getText(), shopCollection);
        }

    }

    @FXML
    void gotoStartMenu() {
        new GameSfxPlayer().onSelect();
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

    public void buy(String cardName) {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientBuyAndSellPacket(cardName, true));
    }

    public void sell(String cardName) {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientBuyAndSellPacket(cardName, false));
    }

    public void searchCollection(String cardName, Collection collection) {

        ArrayList<Card> cards = new ArrayList<>();
        for (Card card : collection.getCards()) {
            try {
                Pattern pattern = Pattern.compile(cardName);
                Matcher matcher = pattern.matcher(card.getCardName());
                if (matcher.find())
                    cards.add(card);
            } catch (Exception e) {
            }
        }
        if (cards.size() == 0)
            labelErrorInShop.setText(ShopError.CARD_NOT_FOUND.toString());
        else
            showCards(cards);
    }

    public void initializeShopCollection(Collection shopCollection, Collection collection) {
        this.myCollection = collection;
        this.shopCollection = shopCollection;

        showCards(shopCollection.getCards());
    }

    public void showCards(ArrayList<Card> cards) {
        Node[] nodes;
        nodes = new Node[cards.size()];
        for (int i = hBox1.getChildren().size() - 1; i >= 0; i--) {
            hBox1.getChildren().remove(hBox1.getChildren().get(i));
        }
        for (int i = hBox2.getChildren().size() - 1; i >= 0; i--) {
            hBox2.getChildren().remove(hBox2.getChildren().get(i));
        }
        int i = 0;
        for (; i < nodes.length / 2 + nodes.length % 2; i++) {
            try {
                Card card = cards.get(i);
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
                nodes[i] = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setInformation(card);
                nodes[i].setOnMouseClicked(event -> {
                    if (cards.equals(shopCollection.getCards()))
                        buy(card.getCardName());
                    else sell(card.getCardName());
                });
                hBox1.getChildren().add(nodes[i]);
            } catch (IOException e) {

            }
        }
        for (; i < nodes.length; i++) {
            try {
                Card card = cards.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                boolean isContinue = false;
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
                    default:
                        isContinue = true;

                }
                if (isContinue)
                    continue;
                nodes[i] = fxmlLoader.load();
                nodes[i].setOnMouseClicked(event -> {
                    if (cards.equals(shopCollection.getCards()))
                        buy(card.getCardName());
                    else sell(card.getCardName());
                });
                CardController cardController = fxmlLoader.getController();
                cardController.setInformation(card);
                hBox2.getChildren().add(nodes[i]);
            } catch (IOException e) {

            }
        }
    }
}
