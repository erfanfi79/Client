package view;

public enum Constants {

    STAGE_WIDTH(1280),
    STAGE_HEIGHT(720),

    GRID_PANE_LAYOUT_X(340),
    GRID_PANE_LAYOUT_Y(230),
    GRID_PANE_V_SPACE(5),
    GRID_PANE_H_SPACE(8),
    POLYGON_HEIGHT(50),
    POLYGON_WIDTH(60);


    private int number;

    public int get() {
        return number;
    }

    Constants(int number) {
        this.number = number;
    }
}
