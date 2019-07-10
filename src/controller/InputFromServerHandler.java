package controller;

import javafx.application.Platform;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerPacket;
import packet.serverPacket.serverMatchPacket.*;
import view.battleView.BattleView;

public class InputFromServerHandler {

    private BattleView battleView;

    private boolean isMatchFinished = false;

    InputFromServerHandler(BattleView battleView) {
        this.battleView = battleView;
    }

    public void inputHandler() {

        while (!isMatchFinished) {

            ServerPacket packet = Controller.getInstance().clientListener.getPacketFromServer();
            System.err.println("after scanner");

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

        Platform.runLater(() -> {
            battleView.table = packet.getTable();
            battleView.headerView.setManas(packet.getPlayer1Mana(), packet.getPlayer2Mana());
            battleView.tableUnitsView.setUnitsImage();
        });
    }

    private void movePacketHandler(ServerMovePacket packet) {

    }

    private void attackPacketHandler(ServerAttackPacket packet) {

    }

    private void graveYardPacketHandler(ServerGraveYardPacket packet) {

        //todo erfan
    }

    private void matchEnumPacketHandler(ServerMatchEnumPacket packet) {

        switch (packet.getPacket()) {

            case START_YOUR_TURN:
                System.err.println("start your turn");

                Platform.runLater(() -> {
                    battleView.isMyTurn = true;
                    battleView.endTurnButton.changeColor();
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
        Platform.runLater(() -> battleView.headerView.setPlayersName(packet.getPlayer1UserName(), packet.getPlayer2UserName()));
    }
}
