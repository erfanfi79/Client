package view.battleView;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import static view.Constants.*;

public class TableInputHandler {

    private GridPane polygons = new GridPane();

    public GridPane get() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {

                Rectangle rectangle = new Rectangle(POLYGON_WIDTH.get(), POLYGON_HEIGHT.get());
                rectangle.setOpacity(0.2);
                rectangle.getStyleClass().add("enterMouseOnPolygon");
                polygons.add(rectangle, j, i);
            }
        }

        polygons.setVgap(GRID_PANE_V_SPACE.get());
        polygons.setHgap(GRID_PANE_H_SPACE.get());
        polygons.setLayoutX(GRID_PANE_LAYOUT_X.get());
        polygons.setLayoutY(GRID_PANE_LAYOUT_Y.get());
        return polygons;
    }
}
