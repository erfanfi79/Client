package controller;

import packet.serverPacket.ServerPacket;

import java.util.LinkedList;
import java.util.Queue;

public class InputFromServerGetter {

    private static InputFromServerGetter instance;
    private Queue<ServerPacket> packets = new LinkedList<>();

    private InputFromServerGetter() {
    }

    public static InputFromServerGetter getInstance() {

        if (instance == null) instance = new InputFromServerGetter();
        return instance;
    }

    public synchronized ServerPacket get() {
        return packets.poll();
    }

    public void startToGetInput(ServerPacket packet) {
        packets.add(packet);
    }

}
