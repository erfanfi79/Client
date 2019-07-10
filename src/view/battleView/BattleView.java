package view.battleView;

import controller.Controller;
import controller.GraveYardController;
import controller.InputFromServerGetter;
import controller.MediaController.GameMusicPlayer;
import controller.Popup;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerPacket;
import packet.serverPacket.serverMatchPacket.*;

import static view.Constants.STAGE_HEIGHT;
import static view.Constants.STAGE_WIDTH;

public class BattleView {

    private ConstantViews constantViews = new ConstantViews();
    public HeaderView headerView = new HeaderView();
    private TableInputHandler tableInputHandler = new TableInputHandler();
    public TableUnitsView tableUnitsView = new TableUnitsView();
    public EndTurnButton endTurnButton = new EndTurnButton();
    GraveYardController graveYardController;
    public VirtualCard[][] table;

    boolean isMyTurn = false;
    boolean isReadyForInsert = false;
    private boolean isMatchFinished = false;
    int whichHandCardForInsert = -1;
    private double x, y;


    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();
        pane.getChildren().addAll(constantViews.get(), headerView.get(), tableInputHandler.get(this),
                endTurnButton.get(this), tableUnitsView.get(this));
        GameMusicPlayer.getInstance().playBattleMusic();
        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {

            Controller.stage.setX(event.getScreenX() - x);
            Controller.stage.setY(event.getScreenY() - y);

        });
        scene.getStylesheets().add(getClass().getResource("/resources/style/BattleStyle.css").toExternalForm());
        scene.setCursor(new ImageCursor(view.ImageLibrary.CursorImage.getImage()));
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void inputHandler(InputFromServerGetter inputFromServerGetter) {

        while (!isMatchFinished) {

            ServerPacket packet = inputFromServerGetter.get();
            if (packet == null) continue;
            System.err.println(packet.getClass().getName());

            if (packet instanceof ServerMatchInfoPacket)
                matchInfoPacketHandler((ServerMatchInfoPacket) packet);

            else if (packet instanceof ServerMovePacket)
                movePacketHandler((ServerMovePacket) packet);

            else if (packet instanceof ServerAttackPacket)
                attackPacketHandler((ServerAttackPacket) packet);

            else if (packet instanceof ServerLogPacket)
                new Popup().showMessage(((ServerLogPacket) packet).getLog());

            else if (packet instanceof ServerGraveYardPacket)
                graveYardPacketHandler((ServerGraveYardPacket) packet);

            else if (packet instanceof ServerMatchEnumPacket)
                matchEnumPacketHandler((ServerMatchEnumPacket) packet);

            else if (packet instanceof ServerPlayersUserNamePacket)
                playersNamePacketHandler((ServerPlayersUserNamePacket) packet);
        }
    }

    private void matchInfoPacketHandler(ServerMatchInfoPacket packet) {
        System.err.println("matchInfoPacketHandler");

        table = packet.getTable();

        Platform.runLater(() -> {
            headerView.setManas(packet.getPlayer1Mana(), packet.getPlayer2Mana());
            tableUnitsView.setUnitsImage();
        });
    }

    private void movePacketHandler(ServerMovePacket packet) {

    }

    private void attackPacketHandler(ServerAttackPacket packet) {

    }

    private void graveYardPacketHandler(ServerGraveYardPacket packet) {

    }

    private void matchEnumPacketHandler(ServerMatchEnumPacket packet) {

        switch (packet.getPacket()) {

            case START_YOUR_TURN:
                System.err.println("start your turn");

                Platform.runLater(() -> {
                    isMyTurn = true;
                    endTurnButton.changeColor();
                });

            case MATCH_FINISHED:
//                Controller.getInstance().gotoStartMenu();
//                GameMusicPlayer.getInstance().menuSong();
                Platform.runLater(() -> {
                    isMatchFinished = true;
                });
        }
    }

    private void playersNamePacketHandler(ServerPlayersUserNamePacket packet) {
        System.err.println("playersNamePacketHandler");
        Platform.runLater(() -> headerView.setPlayersName(packet.getPlayer1UserName(), packet.getPlayer2UserName()));
    }
}
