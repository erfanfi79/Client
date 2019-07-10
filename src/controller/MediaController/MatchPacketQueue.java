package controller.MediaController;

import packet.serverPacket.ServerPacket;

import java.util.LinkedList;
import java.util.Queue;

public class MatchPacketQueue {

    private Queue<ServerPacket> packets = new LinkedList<>();
    private static MatchPacketQueue instance = new MatchPacketQueue();

    public static MatchPacketQueue getInstance() {
        return instance;
    }

    public synchronized void add(ServerPacket serverPacket) {
        packets.add(serverPacket);
    }

    public synchronized ServerPacket poll() {
        return packets.poll();
    }
}
