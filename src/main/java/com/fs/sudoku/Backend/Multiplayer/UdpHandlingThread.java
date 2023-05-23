package com.fs.sudoku.Backend.Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class UdpHandlingThread implements Runnable{

    private DatagramSocket dgSocket;
    private String otherIp;
    private String otherPort;
    public UdpHandlingThread(DatagramSocket dgSocket,String otherIp,String otherPort) {
        this.dgSocket = dgSocket;
        this.otherIp = otherIp;
        this.otherPort = otherPort;
    }
    @Override
    public void run() {
        byte[] sendingBytes = "ping".getBytes();
        try {
            DatagramPacket dgPacketSend = new DatagramPacket(sendingBytes,sendingBytes.length, InetAddress.getByName(otherIp),Integer.parseInt(otherPort));
            DatagramPacket dgPacketReceive = new DatagramPacket(new byte[1024],1024);
            dgSocket.send(dgPacketSend);
            while(true) {
                dgSocket.receive(dgPacketReceive);
                String message = new String(dgPacketReceive.getData());
                System.out.println("got Message: " + message.trim());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                dgSocket.send(dgPacketSend);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
