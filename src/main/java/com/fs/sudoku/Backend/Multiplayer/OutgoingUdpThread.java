package com.fs.sudoku.Backend.Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;

public class OutgoingUdpThread implements Runnable{

    private final DatagramSocket dgSocket;
    private final String otherIp;
    private final String otherPort;
    public static  Queue<byte[]> MessageQueue;
    public OutgoingUdpThread(DatagramSocket dgSocket, String otherIp, String otherPort) {
        this.dgSocket = dgSocket;
        this.otherIp = otherIp;
        this.otherPort = otherPort;
    }
    @Override
    public void run() {
        byte[] sendingBytes = "ping".getBytes();
        MessageQueue.add(sendingBytes);
        while(Client.isConnected) {
            try {
                DatagramPacket dgPacket = new DatagramPacket(MessageQueue.remove(), MessageQueue.remove().length, InetAddress.getByName(otherIp), Integer.parseInt(otherPort));
                dgSocket.send(dgPacket);
                Thread.sleep(1000);
                MessageQueue.add(sendingBytes);
            } catch (IOException | InterruptedException e) {
                Client.isConnected = false;
                throw new RuntimeException(e);
            }
        }
    }
}
