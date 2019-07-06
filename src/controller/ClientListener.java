package controller;


import javafx.application.Platform;
import packet.clientPacket.ClientPacket;
import packet.serverPacket.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener extends Thread {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    public ClientListener(Socket socket) {

        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerPacket packet = (ServerPacket) objectInputStream.readObject();
                if (packet instanceof ServerLogPacket)
                    handleLogs((ServerLogPacket) packet);
                if (packet instanceof ServerMoneyPacket)
                    Platform.runLater(() -> Controller.getInstance().showMoney(((ServerMoneyPacket) packet).getMoney()));
                if (packet instanceof ServerMatchHistory)
                    ((MatchHistoryController) Controller.getInstance().currentController).initializeHistorys(((ServerMatchHistory) packet).getHistories());
                if (packet instanceof ServerLeaderBoardPacket)
                    if (Controller.getInstance().currentController instanceof LeaderBoardController) {
                        LeaderBoardController controller = (LeaderBoardController) Controller.getInstance().currentController;
                        controller.initializeLeaderboard(((ServerLeaderBoardPacket) packet).getUsernames()
                                , ((ServerLeaderBoardPacket) packet).getWinNumber());
                    }
                if (packet instanceof ServerCollection)
                    handleCollection((ServerCollection) packet);
            } catch (IOException e) {
                break;
            } catch (ClassNotFoundException e2) {
                System.out.println(e2.getMessage());
            }
        }

    }

    public void sendPacket(ClientPacket packet) {
        try {
            objectOutputStream.writeObject(packet);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

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


    }

}

