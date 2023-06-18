package com.fs.sudoku.Backend.Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OutgoingUdpThread implements Runnable{

    private final DatagramSocket dgSocket;
    private final String otherIp;
    private final String otherPort;
    public static  Queue<byte[]> MessageQueue;
    public OutgoingUdpThread(DatagramSocket dgSocket, String otherIp, String otherPort) {
        this.dgSocket = dgSocket;
        this.otherIp = otherIp;
        this.otherPort = otherPort;
        MessageQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Sends a Message to the other player every 100 milliseconds in a thread
     */
    @Override
    public void run() {
        byte[] sendingBytes = "ping".getBytes();
        MessageQueue.add(sendingBytes);
        while(Client.isConnected) {
            try {
                byte[] Message = MessageQueue.remove();
                DatagramPacket dgPacket = new DatagramPacket(Message, Message.length, InetAddress.getByName(otherIp), Integer.parseInt(otherPort));
                dgSocket.send(dgPacket);
                Thread.sleep(100);
                MessageQueue.add(sendingBytes);
            } catch (IOException | InterruptedException e) {
                Client.isConnected = false;
                throw new RuntimeException(e);
            }
        }
    }
}
