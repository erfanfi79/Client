package view.battleView;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Coordination;
import packet.clientPacket.clientMatchPacket.ClientAttackPacket;
import packet.clientPacket.clientMatchPacket.ClientInsertCardPacket;
import packet.clientPacket.clientMatchPacket.ClientMovePacket;
import packet.serverPacket.serverMatchPacket.VirtualCard;
import view.ImageLibrary;


import static view.Constants.*;

public class TableInputHandler {

    private BattleView battleView;
    private GridPane rectangles = new GridPane();
    private Coordination selectedCell;
    private Pane mainPane;
    private AnchorPane cardInfo = new AnchorPane();


    public GridPane get(BattleView battleView, Pane pane) {

        this.battleView = battleView;
        this.mainPane = pane;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {

                Rectangle rectangle = new Rectangle(POLYGON_WIDTH.get(), POLYGON_HEIGHT.get());
                rectangle.getStyleClass().addAll("unSelectRectangle", "enterMouseOnRectangle");
                rectangles.add(rectangle, j, i);
            }
        }

        showUnitsInTableInfo();

        rectangles.setVgap(GRID_PANE_V_SPACE.get());
        rectangles.setHgap(GRID_PANE_H_SPACE.get());
        rectangles.setLayoutX(GRID_PANE_LAYOUT_X.get());
        rectangles.setLayoutY(GRID_PANE_LAYOUT_Y.get());
        return rectangles;
    }

    private void eventHandler() {

        for (Node node : rectangles.getChildren()) {
            node.setOnMouseClicked(event -> {

                if (battleView.isReadyForInsert)
                    insertHandler(node);

                else {
                    if (selectedCell == null && battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] != null)
                        selectCardHandler(node);

                    else if (selectedCell != null && battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] != null)
                        attackHandler(node);

                    else if (selectedCell != null)
                        moveHandler(node);
                }
            });
        }
    }

    private void insertHandler(Node node) {

        battleView.isReadyForInsert = false;
        Controller.getInstance().clientListener.sendPacketToServer(
                new ClientInsertCardPacket(
                        GridPane.getRowIndex(node),
                        GridPane.getColumnIndex(node),
                        battleView.whichHandCardForInsert));
    }

    private void selectCardHandler(Node node) {

        selectedCell = Coordination.getNewCoordination(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
        node.getStyleClass().remove("unSelectRectangle");
        node.getStyleClass().add("selectRectangle");
    }

    private void attackHandler(Node node) {

        ClientAttackPacket packet = new ClientAttackPacket();
        packet.setAttacker(selectedCell);
        packet.setDefender(Coordination.getNewCoordination(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)));
        Controller.getInstance().clientListener.sendPacketToServer(packet);
        selectedCell = null;
    }

    private void moveHandler(Node node) {

        ClientMovePacket packet = new ClientMovePacket();
        packet.setStart(selectedCell);
        packet.setDestination(Coordination.getNewCoordination(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)));
        Controller.getInstance().clientListener.sendPacketToServer(packet);
        selectedCell = null;
    }


    private void showUnitsInTableInfo() {

        for (Node node : rectangles.getChildren()) {
            node.setOnMouseEntered(event -> {
                if (battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] != null) {

                    AnchorPane anchorPane = cardInfo(battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]);
                    anchorPane.relocate(battleView.xOfCursor, battleView.yOfCursor - 300);
                    mainPane.getChildren().
                            add(cardInfo(battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]));

                }
            });

            node.setOnMouseExited(event -> {
                if (battleView.table[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] != null)
                    mainPane.getChildren().remove(cardInfo);
            });
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

        name.relocate(50, 80);
        HP.relocate(165, 176);
        AP.relocate(48, 176);
        MP.relocate(110, 233);

        cardInfo.getChildren().addAll(background, name, HP, AP, MP);
        return cardInfo;
    }
}
