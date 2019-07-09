package controller.MediaController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

public class GameMusicPlayer {
    private static GameMusicPlayer gameMusicPlayer;
    Media media;
    MediaPlayer mediaPlayer;

    private GameMusicPlayer() {
    }

    public static GameMusicPlayer getInstance() {
        if (gameMusicPlayer == null)
            gameMusicPlayer = new GameMusicPlayer();
        return gameMusicPlayer;
    }

    public void menuSong() {
        media = new Media(Paths.get(MediaPath.MENU.getPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }
}