package view.battleView;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import packet.clientPacket.clientMatchPacket.ClientInsertCardPacket;
import packet.serverPacket.serverMatchPacket.VirtualCard;
import view.Constants;
import view.ImageLibrary;

public class HandView {

    private ImageView[] backgroundOfHandCards = new ImageView[6];
    private ImageView[] cardsImage = new ImageView[6];
    private BattleView battleView;
    private AnchorPane cardInfo = new AnchorPane();
    private Pane mainPane;

    private int whichHandCardSelected = -1;
    private double xOfReleasedMouse, yOfReleasedMouse;


    public AnchorPane get(BattleView battleView, Pane mainPane) {

        this.mainPane = mainPane;
        this.battleView = battleView;
        AnchorPane anchorPane = new AnchorPane();

        for (int i = 0; i < 6; i++) {
            backgroundOfHandCards[i] = new ImageView(ImageLibrary.HandCardAround.getImage());
            backgroundOfHandCards[i].setScaleX(0.7);
            backgroundOfHandCards[i].setScaleY(0.7);
            backgroundOfHandCards[i].getStyleClass().add("enterMouseOnHandCard");
            backgroundOfHandCards[i].relocate(getX(i), 0);
            anchorPane.getChildren().add(backgroundOfHandCards[i]);
        }

        for (int i = 0; i < 6; i++) {
            cardsImage[i] = new ImageView();
            cardsImage[i].relocate(getX(i) + 20, 20);
            cardsImage[i].setMouseTransparent(true);
            anchorPane.getChildren().add(cardsImage[i]);
        }

        showCardInfo();
        eventHandler();

        anchorPane.relocate(Constants.HAND_Pane_LAYOUT_X.get(), Constants.HAND_Pane_LAYOUT_Y.get());
        return anchorPane;
    }

    public void setHandCards() {

        for (int i = 0; i < 6; i++) {

            cardsImage[i].setImage(null);

            if (battleView.hand[i] == null) continue;
            try {
                cardsImage[i].setImage(new Image("resources/battle/units/" +
                        /*hand[i].getCardName()*/ "white_demon" + "/stand"));
            } catch (Exception e) {
                e.printStackTrace();
                cardsImage[i].setImage(new Image("resources/battle/units/default/stand"));
            }
        }
    }

    private void showCardInfo() {

        for (int i = 0; i < 6; i++) {
            final int which = i;

            backgroundOfHandCards[which].setOnMouseEntered(event -> {
                if (battleView.hand[which] != null) {

                    AnchorPane anchorPane = cardInfo(battleView.hand[which]);
                    anchorPane.relocate(battleView.xOfCursor, battleView.yOfCursor - 300);
                    mainPane.getChildren().add(cardInfo(battleView.hand[which]));
                }
            });

            backgroundOfHandCards[i].setOnMouseExited(event -> {
                if (battleView.hand[which] != null)
                    mainPane.getChildren().remove(cardInfo);
            });
        }
    }

    private void eventHandler() {

        for (int i = 0; i < 5; i++) {

            final int index = i;
            backgroundOfHandCards[i].setOnMousePressed(event -> {

                if (battleView.isMyTurn) {

                    whichHandCardSelected = index;

                    if (battleView.hand[whichHandCardSelected] != null) {

                        System.out.println("card " + whichHandCardSelected + " pressed");

                        backgroundOfHandCards[index].setOnMouseReleased(event1 -> {

                            System.out.println("card released");

                            xOfReleasedMouse = event1.getSceneX();
                            yOfReleasedMouse = event1.getSceneY();
                            checkInsertValidityAndSendToServer();
                        });
                    }
                }
            });
        }
    }

    private void checkInsertValidityAndSendToServer() {

        int column = (int) (xOfReleasedMouse - Constants.GRID_PANE_LAYOUT_X.get()) /
                (Constants.POLYGON_WIDTH.get() + Constants.GRID_PANE_H_SPACE.get());

        int row = (int) (yOfReleasedMouse - Constants.GRID_PANE_LAYOUT_Y.get()) /
                (Constants.POLYGON_HEIGHT.get() + Constants.GRID_PANE_V_SPACE.get());

        System.out.println("target of insert " + row + " " + column);
        System.out.println("x : " + xOfReleasedMouse + " y: " + yOfReleasedMouse);

        System.out.println("parameter : " + row + " " + column + " " + whichHandCardSelected);

        if (row >= 0 && row < 5 && column >= 0 && column < 9 && whichHandCardSelected >= 0) {
            System.out.println("rad shod");
            ClientInsertCardPacket packet = new ClientInsertCardPacket(row, column, whichHandCardSelected);
            Controller.getInstance().clientListener.sendPacketToServer(packet);
            System.out.println("insert successfully");
        }
    }

    private AnchorPane cardInfo(VirtualCard card) {

        cardInfo.getChildren().removeAll(cardInfo.getChildren());

        ImageView background = new ImageView(ImageLibrary.CardInfo.getImage());
        Label name = new Label(card.getCardName() + "");
        Label HP = new Label(card.getHealthPoint() + "");
        Label AP = new Label(card.getAttackPoint() + "");
        Label MP = new Label(card.getManaPoint() + "");

        name.setTextFill(Color.WHITE);
        HP.setTextFill(Color.WHITE);
        AP.setTextFill(Color.WHITE);

        name.setFont(Font.font("Dyuthi", 15));
        HP.setFont(Font.font("Dyuthi", 20));
        AP.setFont(Font.font("Dyuthi", 20));
        MP.setFont(Font.font("Dyuthi", 20));

        name.setAlignment(Pos.CENTER);

        name.relocate(50, 80);
        HP.relocate(162, 165);
        AP.relocate(45, 165);
        MP.relocate(107, 225);

        cardInfo.getChildren().addAll(background, name, HP, AP, MP);
        return cardInfo;
    }

    private int getX(int index) {
        return Constants.HAND_H_DISTANCE_BETWEEN.get() * index;
    }
}
