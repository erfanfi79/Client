package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

enum MediaPath {
    SELECT("resources/sfx/sfx_ui_select.m4a");


    private String path = "";

    MediaPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

public class GameMusicPlayer {
    public void onSelect() {
        Media m = new Media(Paths.get("src/resources/sfx/sfx_ui_select.m4a").toUri().toString());
        new MediaPlayer(m).play();
/*      Media media=new Media(MediaPath.SELECT.getPath());
        MediaPlayer mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);*/
    }

    public void onMousClickUnit() {

    }
}