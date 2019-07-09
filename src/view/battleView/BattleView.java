package view.battleView;

import controller.ClientListener;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerPacket;
import packet.serverPacket.serverMatchPacket.*;

import static view.Constants.*;

public class BattleView extends Application {

    private ConstantViews constantViews = new ConstantViews();
    private HeaderView headerView = new HeaderView();
    private TableInputHandler tableInputHandler = new TableInputHandler();
    private TableUnitsView tableUnitsView = new TableUnitsView();
    private EndTurnButton endTurnButton = new EndTurnButton();

    private boolean isMatchFinished = false;
    private static boolean isMyTurn = false;   //todo in first of game server must send enum start your turn for player 1


    public static boolean isMyTurn() {
        return isMyTurn;
    }

    @Override
    public void start(Stage primaryStage) {
        showBattle(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();

        pane.getChildren().addAll(constantViews.get(), headerView.get(), tableInputHandler.get(), endTurnButton.get());

        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        scene.getStylesheets().add(getClass().getResource("/resources/style/BattleStyle.css").toExternalForm());
        scene.setCursor(new ImageCursor(view.ImageLibrary.CursorImage.getImage()));
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    private void inputHandler() {

        while (!isMatchFinished) {

            ServerPacket packet = ClientListener.getPacketFromServer();

            if (packet instanceof ServerMatchInfoPacket)
                matchInfoPacketHandler((ServerMatchInfoPacket) packet);

            else if (packet instanceof ServerMovePacket)
                movePacketHandler((ServerMovePacket) packet);

            else if (packet instanceof ServerAttackPacket)
                attackPacketHandler((ServerAttackPacket) packet);

            else if (packet instanceof ServerLogPacket)
                showLogPopUp((ServerLogPacket) packet);

            else if (packet instanceof ServerGraveYardPacket)
                graveYardPacketHandler((ServerGraveYardPacket) packet);

            else if (packet instanceof ServerMatchEnumPacket)
                matchEnumPacketHandler((ServerMatchEnumPacket) packet);

            else if (packet instanceof ServerPlayersUserNamePacket)
                playersNamePacketHandler((ServerPlayersUserNamePacket) packet);
        }
    }

    private void matchInfoPacketHandler(ServerMatchInfoPacket packet) {

        headerView.setManas(packet.getPlayer1Mana(), packet.getPlayer2Mana());
        tableUnitsView.setUnitsImage(packet.getTable());
    }

    private void movePacketHandler(ServerMovePacket packet) {

    }

    private void attackPacketHandler(ServerAttackPacket packet) {

    }

    private void showLogPopUp(ServerLogPacket packet) {

        //todo erfan
    }

    private void graveYardPacketHandler(ServerGraveYardPacket packet) {

        //todo erfan
    }

    private void matchEnumPacketHandler(ServerMatchEnumPacket packet) {

        switch (packet.getPacket()) {

            case START_YOUR_TURN:
                isMyTurn = true;

            case MATCH_FINISHED:
                isMatchFinished = true;
        }
    }

    private void playersNamePacketHandler(ServerPlayersUserNamePacket packet) {
        headerView.setPlayersName(packet.getPlayer1UserName(), packet.getPlayer2UserName());
    }
}
