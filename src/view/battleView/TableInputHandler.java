package view.battleView;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import models.Coordination;

import static view.Constants.*;

public class TableInputHandler {

    private GridPane rectangles = new GridPane();
    private int selectedCellRow, selectedCellColumn;

    public GridPane get() {

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

                selectedCellRow = GridPane.getRowIndex(node);
                selectedCellColumn = GridPane.getColumnIndex(node);

                if (BattleView.getTable()[selectedCellRow][selectedCellColumn] != null) {

                    node.getStyleClass().remove("unSelectRectangle");
                    node.getStyleClass().add("selectRectangle");
                }

            });
        }
    }
}
