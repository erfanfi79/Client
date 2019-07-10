package controller;

import packet.serverPacket.ServerPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class InputFromServerGetter {

    private static InputFromServerGetter instance;
    private Queue<ServerPacket> packets = new LinkedList<>();
    private BufferedReader bufferedReader;

    private InputFromServerGetter() {
    }

    public static InputFromServerGetter getInstance() {

        if (instance == null) instance = new InputFromServerGetter();
        return instance;
    }

    public synchronized ServerPacket get() {
        return packets.poll();
    }

    public void startToGetInput(BufferedReader bufferedReader) {

        this.bufferedReader = bufferedReader;

        while (true)
            packets.add(getPacketFromServer());
    }

    private ServerPacket getPacketFromServer() {

        try {
            return YaGsonChanger.readServerPocket(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
