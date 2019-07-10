package view.battleView;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import models.Coordination;
import packet.clientPacket.clientMatchPacket.ClientAttackPacket;
import packet.clientPacket.clientMatchPacket.ClientInsertCardPacket;
import packet.clientPacket.clientMatchPacket.ClientMovePacket;

import static view.Constants.*;

public class TableInputHandler {

    private BattleView battleView;
    private GridPane rectangles = new GridPane();
    private Coordination selectedCell;


    public GridPane get(BattleView battleView) {

        this.battleView = battleView;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {

                Rectangle rectangle = new Rectangle(POLYGON_WIDTH.get(), POLYGON_HEIGHT.get());
                rectangle.getStyleClass().addAll("unSelectRectangle", "enterMouseOnRectangle");
                rectangles.add(rectangle, j, i);
            }
        }

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
}
