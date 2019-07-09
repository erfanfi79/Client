package controller.MediaController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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

    private static File[] getFileList(String dirPath) {
        File dir = new File(dirPath);

        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".m4a");
            }
        });
        return fileList;
    }

    public void playBattleMusic() {
        media = new Media(getRandomBattleMusicPath().toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                playBattleMusic();
            }
        });
        mediaPlayer.play();
    }

    public Path getRandomBattleMusicPath() {
        File[] fileList = getFileList("src/resources/music/battleMusic");
        Random random = new Random();
        return fileList[random.nextInt(fileList.length)].toPath();
    }

}