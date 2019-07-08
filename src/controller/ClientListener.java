package controller;


import javafx.application.Platform;
import packet.clientPacket.ClientPacket;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerMoneyPacket;
import packet.serverPacket.ServerPacket;

import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread {

    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private Socket socket;

    public ClientListener(Socket socket) {

        this.socket = socket;
        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        while (true) {
            ServerPacket packet = getPacketFromServer();

            if (packet instanceof ServerLogPacket)
                handleLogs((ServerLogPacket) packet);
            if (packet instanceof ServerMoneyPacket)
                Platform.runLater(() -> Controller.getInstance().showMoney(((ServerMoneyPacket) packet).getMoney()));
        }
    }

    public ServerPacket getPacketFromServer() {

        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return YaGsonChanger.readServerPocket(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendPacketToServer(ClientPacket clientPocket) {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(YaGsonChanger.write(clientPocket));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

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

