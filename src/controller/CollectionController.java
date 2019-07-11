package controller;

import controller.MediaController.GameSfxPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import models.Card;
import models.Collection;
import models.Deck;
import packet.clientPacket.ClientCollectionPacket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CollectionController {
    Collection myCollection;
    private double x, y;
    @FXML
    private HBox hBox2;

    @FXML
    private HBox hBox1;

    @FXML
    private TextField txtToDeckName;

    @FXML
    private VBox deckVBox;

    @FXML
    private TextField txtCardId;

    @FXML
    private TextField txtDeckName;

    @FXML
    private Label labelError;

    @FXML
    private Label labelErrorInAdd;

    public void initializeCollection(Collection collection) {
        Controller.getInstance().collectionController = this;
        myCollection = collection;
        Controller.getInstance().setMyCollection(myCollection);
        showCards();
        showDecks();
    }

    @FXML
    void btnNewDeck(ActionEvent event) {
        new GameSfxPlayer().onSelect();
        labelError.setText("");
        for (Deck deck : myCollection.getDecks())
            if (deck.getDeckName().equals(txtDeckName.getText())) {
                createDeck(deck, deck.getDeckName());
                return;
            }
        createDeck(null, txtDeckName.getText());
    }

    @FXML
    void importDeck() {
        new GameSfxPlayer().onSelect();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SER FILE", "*.ser"));
        File selectedFile = fileChooser.showOpenDialog(Controller.stage);
        try {
            FileInputStream fos = new FileInputStream(selectedFile.getPath());
            ObjectInputStream ois = new ObjectInputStream(fos);
            // write object to file
            Deck deck = (Deck) ois.readObject();
            if (deck != null)
                myCollection.getDecks().add(deck);
            showDecks();
            System.out.println("deck added");
            // closing resources
            ois.close();
            fos.close();
        } catch (Exception e) {
            new Popup().showMessage("file is not compatible,please try again with another file");
        }
/*        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile.getPath()));
            YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
            com.gilecode.yagson.YaGson yaGson = yaGsonBuilder.create();
            Deck deck = yaGson.fromJson(bufferedReader, Deck.class);
            if (deck != null)
                myCollection.getDecks().add(deck);
            showDecks();
        } catch (Exception e) {
            new Popup().showMessage("file is not compatible,please try again with another file");
        }*/
    }

    @FXML
    void gotoStartMenu() {
        new GameSfxPlayer().onSelect();
        Controller.getInstance().clientListener.sendPacketToServer(new ClientCollectionPacket(myCollection));
        Controller.getInstance().gotoStartMenu();
    }


    public void add() {
        new GameSfxPlayer().onSelect();
        String cardID = txtCardId.getText(), deckName = txtToDeckName.getText();
        if (cardID.isEmpty() || deckName.isEmpty())
            labelErrorInAdd.setText("No field can be empty");
        CollectionErrors collectionErrors = myCollection.addToDeck(cardID, deckName);
        if (collectionErrors != null)
            labelErrorInAdd.setText(collectionErrors.toString());
        txtCardId.setText("");
        showCards();
        showDecks();
    }

    public void createDeck(Deck deck, String deckName) {
        if (deckName.isEmpty()) {
            labelError.setText("Name cant be empty");
            return;
        }
        if (deck != null) {
            labelError.setText(CollectionErrors.ALREADY_A_DECK_WITH_THIS_USERNAME.toString());
            return;
        }
        deck = new Deck();
        deck.setDeckName(deckName);
        myCollection.getDecks().add(deck);
        showDecks();
    }


    public void showCards() {
        Node[] nodes;
        ArrayList<Card> cards = myCollection.getCards();
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

    public void showDecks() {
        Node[] nodes;
        ArrayList<Deck> decks = myCollection.getDecks();
        nodes = new Node[decks.size()];
        for (int i = deckVBox.getChildren().size() - 1; i >= 0; i--)
            deckVBox.getChildren().remove(deckVBox.getChildren().get(i));
        for (int i = 0; i < nodes.length; i++) {
            try {
                final String deckName = decks.get(i).getDeckName();
                Deck deck = decks.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/cardBackground/DeckView.fxml"));
                nodes[i] = fxmlLoader.load();
                nodes[i].setOnMouseClicked(event -> {
                    txtToDeckName.setText(deckName);
                });
                DeckController deckController = fxmlLoader.getController();
                deckController.setInformation(deck);
                deckVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
