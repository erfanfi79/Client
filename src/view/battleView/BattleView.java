package view.battleView;

import controller.Controller;
import controller.GraveYardController;
import controller.MediaController.MatchPacketQueue;
import controller.Popup;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerPacket;
import packet.serverPacket.serverMatchPacket.*;

import static view.Constants.STAGE_HEIGHT;
import static view.Constants.STAGE_WIDTH;

public class BattleView {

    private HeaderView headerView = new HeaderView();
    private TableUnitsView tableUnitsView = new TableUnitsView();
    private EndTurnButton endTurnButton = new EndTurnButton();
    public VirtualCard[][] table;
    private GraveYardController graveYardController;

    private MatchPacketQueue matchPacketQueue = MatchPacketQueue.getInstance();
    private ConstantViews constantViews = new ConstantViews();
    private TableInputHandler tableInputHandler = new TableInputHandler();

    boolean isMyTurn = false;
    boolean isReadyForInsert = false;
    int whichHandCardForInsert = -1;
    private boolean isMatchFinished = false;
    private double x, y;


    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();

        pane.getChildren().addAll(constantViews.get(), headerView.get(), tableInputHandler.get(this),
                endTurnButton.get(this), tableUnitsView.get(this));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../cardBackground/GraveYard.fxml"));
            Node node = fxmlLoader.load();
            node.relocate(-240, 0);
            graveYardController = fxmlLoader.getController();
            pane.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //GameMusicPlayer.getInstance().playBattleMusic();

        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        dragScene(scene);
        scene.getStylesheets().add(getClass().getResource("/resources/style/BattleStyle.css").toExternalForm());
        scene.setCursor(new ImageCursor(view.ImageLibrary.CursorImage.getImage()));
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void dragScene(Scene scene) {

        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {

            Controller.stage.setX(event.getScreenX() - x);
            Controller.stage.setY(event.getScreenY() - y);

        });
    }
    public void inputHandler() {

        while (!isMatchFinished) {

            ServerPacket packet = matchPacketQueue.poll();
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
        graveYardController.loadCards(packet.getDeadCards());
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
