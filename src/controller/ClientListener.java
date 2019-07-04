package controller;



import javafx.application.Platform;
import packet.clientPacket.ClientPacket;
import packet.serverPacket.ServerLogPacket;
import packet.serverPacket.ServerPacket;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener extends Thread{
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

        public void handleLogs(ServerLogPacket logPacket){
            if (Controller.getInstance().currentController instanceof AccountMenuController)
                Platform.runLater(()->{
                    ((AccountMenuController) Controller.getInstance().currentController).showError(logPacket);
                });

        }

    }

