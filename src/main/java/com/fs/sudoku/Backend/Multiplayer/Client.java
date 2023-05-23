package com.fs.sudoku.Backend.Multiplayer;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.UUID;
@Component
@NoArgsConstructor
public class Client {
    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private DatagramPacket udpPacket;
    private BufferedReader tcpIn;
//    private BufferedOutputStream tcpOut;
    private String uuidString;
    InetAddress serverIp;
    boolean gotResponse;
    String otherClientIP;
    String otherClientPort;

    private void sendInitialUDPPacket(String uuid,String code) {
        String sendingString = uuid + "&&" + code;
        byte[] sendingBytes = sendingString.getBytes();
        udpPacket = new DatagramPacket(sendingBytes,sendingBytes.length,serverIp,5001);
        try {
            for(int i = 0;i < 10; i++) {
            udpSocket.send(udpPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void connectToOtherClient(String code) throws IOException {
        try {
            serverIp = InetAddress.getByName("206.189.251.215");
            tcpSocket = new Socket(serverIp,5000);
            tcpIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            uuidString = tcpIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        udpSocket = new DatagramSocket();
        System.out.println(uuidString);
        sendInitialUDPPacket(uuidString,code);
        while(!gotResponse) {
            String response = tcpIn.readLine();
            String[] responseParts = response.split("&&");
            otherClientIP = responseParts[0];
            otherClientPort = responseParts[1];
            String otherClientUUID = responseParts[2];
            System.out.println(otherClientIP + " "+ otherClientPort);
            System.out.println(otherClientUUID);
            gotResponse = true;
        }
        UdpHandlingThread u = new UdpHandlingThread(udpSocket,otherClientIP,otherClientPort);
        new Thread(u).start();
        tcpSocket.close();

    }
}
