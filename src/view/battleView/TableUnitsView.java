package view.battleView;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static view.Constants.*;

public class TableUnitsView {

    private BattleView battleView;
    private AnchorPane[][] table = new AnchorPane[5][9];

    public AnchorPane get(BattleView battleView) {

        this.battleView = battleView;

        AnchorPane anchorPane = new AnchorPane();

        for (int row = 0; row < 5; row++)
            for (int column = 0; column < 9; column++) {
                table[row][column] = new AnchorPane();
                table[row][column].relocate(getX(column), getY(row));
                anchorPane.getChildren().add(table[row][column]);
            }

        anchorPane.setMouseTransparent(true);
        return anchorPane;
    }

    public void setUnitsImage() {

        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                if (battleView.getTable()[row][column] != null) {

                    table[row][column].getChildren().removeAll(table[row][column].getChildren());
                    ImageView image;

                    try {
                        image = new ImageView("resources/battle/units/" +
                                battleView.getTable()[row][column].getCardName() + "/stand");

                    } catch (Exception e) {
                        e.printStackTrace();
                        image = new ImageView("resources/battle/units/default/stand");
                    }


                    if (column > 4) {
                        image.setScaleX(-1);
                        image.setScaleY(1);
                    } else {
                        image.setScaleX(1);
                        image.setScaleY(1);
                    }

                    table[row][column].getChildren().add(image);
                }
            }
        }
    }

    private int getX(int column) {
        return GRID_PANE_LAYOUT_X.get() + column * (GRID_PANE_H_SPACE.get() + POLYGON_WIDTH.get());
    }

    private int getY(int row) {
        return GRID_PANE_LAYOUT_Y.get() + row * (GRID_PANE_V_SPACE.get() + POLYGON_HEIGHT.get()) - 25;
        //10 because legs of units come in center of cell
    }
}

