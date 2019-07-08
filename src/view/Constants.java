package view;

public enum Constants {

    STAGE_WIDTH(1280),
    STAGE_HEIGHT(720);

    private int number;

    public int get() {
        return number;
    }

    Constants(int number) {
        this.number = number;
    }
}
