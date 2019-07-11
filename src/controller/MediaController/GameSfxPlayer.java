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
        media = new Media(Paths.get(MediaPath.UNIT_ON_CLICK.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onVictory() {
        media = new Media(Paths.get(MediaPath.VICTORY.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onYourTurn() {
        media = new Media(Paths.get(MediaPath.YOUR_TURN.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onAttack() {
        media = new Media(Paths.get(MediaPath.ATTACK.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onDeath() {
        media = new Media(Paths.get(MediaPath.DEATH.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onError() {
        media = new Media(Paths.get(MediaPath.ERROR.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onHit() {
        media = new Media(Paths.get(MediaPath.HIT.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onExplosion() {
        media = new Media(Paths.get(MediaPath.EXPLOSION.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void onMove() {
        media = new Media(Paths.get(MediaPath.MOVE.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

}
