package controller;



import packet.clientPacket.ClientPacket;


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
                    ClientPacket packet = (ClientPacket) objectInputStream.readObject();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void disconnect() {

        }


    }

