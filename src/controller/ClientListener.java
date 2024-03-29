package controller;


import controller.MediaController.MatchPacketQueue;
import javafx.application.Platform;
import packet.clientPacket.ClientPacket;
import packet.serverPacket.*;
import packet.serverPacket.serverMatchPacket.ServerMatchPacket;
import view.battleView.BattleView;

import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Socket socket;
    private MatchPacketQueue matchPacketQueue = MatchPacketQueue.getInstance();
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;

    public ClientListener(Socket socket) {

        try {
            this.socket = socket;
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                ServerPacket packet = getPacketFromServer();

                if (packet instanceof ServerMatchPacket)
                    Platform.runLater(() -> matchPacketQueue.add(packet));

                else if (packet instanceof ServerLogPacket)
                    handleLogs((ServerLogPacket) packet);

                else if (packet instanceof ServerAuctionPacket) {
                    if (Controller.getInstance().currentController instanceof ShopController)
                        Platform.runLater(() -> ((ShopController) Controller.getInstance().currentController).auctionHandler((ServerAuctionPacket) packet));
                    else if (Controller.getInstance().currentController instanceof AuctionController)
                        Platform.runLater(() -> ((AuctionController) Controller.getInstance().currentController).initializeAuction((ServerAuctionPacket) packet));


                } else if (packet instanceof ServerMoneyPacket)
                    Platform.runLater(() -> Controller.getInstance().showMoney(((ServerMoneyPacket) packet).getMoney()));

                else if (packet instanceof ServerMatchHistory) {

                    if (Controller.getInstance().currentController instanceof MatchHistoryController)
                        ((MatchHistoryController) Controller.getInstance().currentController).initializeHistorys(((ServerMatchHistory) packet).getHistories());

                } else if (packet instanceof ServerLeaderBoardPacket) {

                    if (Controller.getInstance().currentController instanceof LeaderBoardController) {

                        LeaderBoardController controller = (LeaderBoardController) Controller.getInstance().currentController;
                        Platform.runLater(() -> controller.initializeLeaderboard(((ServerLeaderBoardPacket) packet).getUsernames()
                                , ((ServerLeaderBoardPacket) packet).getWinNumber()));

                    }
                } else if (packet instanceof ServerCollection)
                    handleCollection((ServerCollection) packet);

                else if (packet instanceof ServerChatRoomPacket)
                    Platform.runLater(() -> Controller.getInstance().updateChatRoom((ServerChatRoomPacket) packet));

                else if (packet instanceof ServerEnumPacket)
                    serverEnumPacketHandler((ServerEnumPacket) packet);
            }
        } catch (Exception e) {
            close();
        }
    }

    public ServerPacket getPacketFromServer() {
        try {
            return YaGsonChanger.readServerPocket(bufferedReader.readLine());
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
        return null;
    }

    public void sendPacketToServer(ClientPacket clientPocket) {
        try {
            bufferedWriter.write(YaGsonChanger.write(clientPocket));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (socket != null) socket.close();

        } catch (Exception e) {
        }
        try {
            if (bufferedReader != null) bufferedReader.close();
        } catch (Exception e) {
        }
        try {
            if (bufferedWriter != null) bufferedWriter.close();
        } catch (Exception e) {
        }
        try {
            if (inputStreamReader != null) inputStreamReader.close();
        } catch (Exception e) {
        }
        try {
            if (outputStreamWriter != null) outputStreamWriter.close();
        } catch (Exception e) {
        }

    }

    public void handleCollection(ServerCollection serverCollection) {
        if (serverCollection.getIsShop()) {
            Platform.runLater(() -> {
                if (Controller.getInstance().currentController instanceof ShopController)
                    ((ShopController) Controller.getInstance().currentController).initializeShopCollection(
                            serverCollection.getShopCollection(),
                            serverCollection.getCollection());
            });
        } else {
            Platform.runLater(() -> {
                if (Controller.getInstance().currentController instanceof CollectionController)
                    ((CollectionController) Controller.getInstance().currentController).initializeCollection(
                            serverCollection.getCollection());
            });
        }
    }

    public void handleLogs(ServerLogPacket logPacket) {
        if (Controller.getInstance().currentController instanceof AccountMenuController)
            if (logPacket.isSuccessful()) Platform.runLater(() -> {
                Controller.getInstance().gotoStartMenu();
            });
            else
                Platform.runLater(() -> ((AccountMenuController) Controller.getInstance().currentController).showError(logPacket));
        else {
            if (logPacket.isSuccessful()) Platform.runLater(() -> Controller.getInstance().handleSuccsesfulLogs());
            else
                Platform.runLater(() -> new Popup().showMessage(logPacket.getLog()));
        }

    }

    private void serverEnumPacketHandler(ServerEnumPacket packet) {

        switch (packet.getServerEnum()) {

            case MULTI_PLAYER_GAME_IS_READY:
                System.err.println("multi player is ready");
                BattleView battleView = new BattleView();
                new Thread(() -> Platform.runLater(() -> battleView.showBattle(Controller.stage))).start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                new Thread(battleView::inputHandler).start();
                break;

            case UPDATE_LEADER_BOARD:
                if (Controller.getInstance().currentController instanceof LeaderBoardController)
                    ((LeaderBoardController) Controller.getInstance().currentController).onlineCheckBox();
                break;

        }
    }
}

