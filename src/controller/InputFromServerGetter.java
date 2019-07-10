package controller;

import packet.serverPacket.ServerPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class InputFromServerGetter {

    private static InputFromServerGetter instance;
    private Queue<ServerPacket> packets = new LinkedList<>();
    private InputStreamReader inputStreamReader;

    private InputFromServerGetter() {
    }

    public static InputFromServerGetter getInstance() {

        if (instance == null) instance = new InputFromServerGetter();
        return instance;
    }

    public synchronized ServerPacket get() {
        return packets.poll();
    }

    public void startToGetInput(InputStreamReader inputStreamReader) {

        this.inputStreamReader = inputStreamReader;

        while (true)
            packets.add(getPacketFromServer());
    }

    private ServerPacket getPacketFromServer() {

        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return YaGsonChanger.readServerPocket(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
