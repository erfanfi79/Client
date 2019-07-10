package controller.MediaController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class GameSfxPlayer {
    Media media;
    MediaPlayer mediaPlayer;

    public void onSelect() {
        media = new Media(Paths.get(MediaPath.SELECT.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onMousClickUnit() {

    }

    public void onVictory() {

    }

    public void onYourTurn() {

    }
}
