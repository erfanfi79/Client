package controller.MediaController;

public enum MediaPath {
    MENU("src/resources/music/music_menu.mp3"),
    SELECT("src/resources/sfx/sfx_ui_select.m4a");


    private String path = "";

    MediaPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
