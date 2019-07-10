package controller;


import javafx.application.Platform;
import models.Buff;
import packet.clientPacket.ClientPacket;
import packet.serverPacket.*;

import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public ClientListener(Socket socket) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                ServerPacket packet = getPacketFromServer();

                if (packet instanceof ServerLogPacket)
                    handleLogs((ServerLogPacket) packet);

                else if (packet instanceof ServerMoneyPacket)
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

                else sendPacketToWaitingList(packet);

            }
        } catch (Exception e) {
            close();
        }
    }

    public void sendPacketToWaitingList(ServerPacket packet) {
        Platform.runLater(() -> {
            InputFromServerGetter.getInstance().startToGetInput(packet);
        });

    }
    public ServerPacket getPacketFromServer() {
        try {
            return YaGsonChanger.readServerPocket(bufferedReader.readLine());

        } catch (IOException e) {
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
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (socket != null) socket.close();
            if (inputStreamReader != null) inputStreamReader.close();
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

}

