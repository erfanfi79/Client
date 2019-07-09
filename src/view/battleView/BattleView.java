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
    private MarginView marginView = new MarginView();
    private TableInputHandler tableInputHandler = new TableInputHandler();

    private boolean isMatchFinished = false;

    @Override
    public void start(Stage primaryStage) {
        showBattle(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();

        pane.getChildren().addAll(constantViews.get(), /*marginView.get(),*/ tableInputHandler.get());

        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        scene.getStylesheets().add(getClass().getResource("/resources/style/style.css").toExternalForm());
        scene.setCursor(new ImageCursor(ui.ImageLibrary.CursorImage.getImage()));
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
            case MATCH_FINISHED:
                isMatchFinished = true;
        }
    }

    private void playersNamePacketHandler(ServerPlayersUserNamePacket packet) {
        marginView.setPlayersName(packet.getPlayer1UserName(), packet.getPlayer2UserName());
    }
}
