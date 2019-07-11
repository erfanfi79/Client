package controller;

import controller.MediaController.GameSfxPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import models.Card;
import models.Deck;

import java.io.*;
import java.util.ArrayList;

public class DeckMenu {
    private Deck deck;
    private double x, y;
    @FXML
    private TextField txtCardId;

    @FXML
    private HBox hBox2;

    @FXML
    private HBox hBox1;

    @FXML
    private Label labelError;

    @FXML
    void gotoCollectionMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/collectionMenuView/CollectionMenuView.fxml"));
            Parent root = fxmlLoader.load();
            Controller.getInstance().currentController = fxmlLoader.getController();
            ((CollectionController) Controller.getInstance().currentController).initializeCollection(Controller.getInstance().getMyCollection());
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
    void validateDeck() {
        new GameSfxPlayer().onSelect();
        if (deck.isDeckValidate())
            labelError.setText(CollectionErrors.DECK_IS_VALID.toString());
        else
            labelError.setText(CollectionErrors.DECK_IS_NOT_VALID.toString());
    }

    @FXML
    void selectDeck() {
        new GameSfxPlayer().onSelect();
        if (deck.isDeckValidate())
            Controller.getInstance().getMyCollection().setSelectedDeck(deck);
        else
            labelError.setText(CollectionErrors.DECK_IS_NOT_VALID.toString());
    }

    @FXML
    void removeCard() {
        new GameSfxPlayer().onSelect();
        String cardID = txtCardId.getText();
        CollectionErrors collectionErrors;

        collectionErrors = Controller.getInstance().getMyCollection().removeFromDeck(cardID, deck.getDeckName());
        if (collectionErrors != null)
            labelError.setText(collectionErrors.toString());
        txtCardId.setText("");
        showCards();
    }

    @FXML
    void mouseIn(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(1);

        }
    }

    @FXML
    void exportDeck() {
        new GameSfxPlayer().onSelect();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SER FILE", "*.ser"));
            File selectedFile = fileChooser.showSaveDialog(Controller.stage);
            FileOutputStream fos = new FileOutputStream(selectedFile.getPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(deck);
            System.out.println("deck saved");
            // closing resources
            oos.close();
            fos.close();
/*            YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
            com.gilecode.yagson.YaGson yaGson = yaGsonBuilder.create();
            saveTextToFile(yaGson.toJson(deck), selectedFile);*/
        } catch (Exception e) {
            new Popup().showMessage("save is not possible,please try again with another file");
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
        }
    }

    @FXML
    void mouseOut(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(0.8);
        }
    }

    public void setInfo(Deck deck) {
        this.deck = deck;
        showCards();
    }

    public void showCards() {
        Node[] nodes;
        ArrayList<Card> cards = deck.getCards();
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
                final String ID = card.getCardID();
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
                    txtCardId.setText(ID);
                });
                hBox1.getChildren().add(nodes[i]);
            } catch (IOException e) {

            }
        }
        for (; i < nodes.length; i++) {
            try {
                Card card = cards.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                final String ID = card.getCardID();
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
                    txtCardId.setText(ID);
                });
                CardController cardController = fxmlLoader.getController();
                cardController.setInformation(card);
                hBox2.getChildren().add(nodes[i]);
            } catch (IOException e) {

            }
        }

    }
}
