package com.fs.sudoku.Backend.Multiplayer;


import com.fs.sudoku.Backend.RandomPuzzleGenerator;
import com.fs.sudoku.Backend.SudokuGrid;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Component
@NoArgsConstructor
public class Client {
    private DatagramSocket udpSocket;

    InetAddress serverIp;
    boolean gotResponse;
    String otherClientIP;
    String otherClientPort;
    public static boolean isConnected;
    public static SudokuGrid multiplayerGrid = new SudokuGrid();
    public static boolean multiplayerGridSet;
    private final RandomPuzzleGenerator randomPuzzleGenerator = new RandomPuzzleGenerator();
    private long startTime;
    public static boolean vsWinOrLose;
    public static long timeTaken;
    public static boolean coop;
    public static boolean lastPlayer;
    public static boolean first;
    public static boolean hasGeneratedPuzzle;
    public static long OpponentTime = 0;
    private void sendInitialUDPPacket(String uuid,String code) {
        String sendingString = uuid + "&&" + code;
        byte[] sendingBytes = sendingString.getBytes();
        DatagramPacket udpPacket = new DatagramPacket(sendingBytes, sendingBytes.length, serverIp, 5001);
        try {
            for(int i = 0;i < 10; i++) {
            udpSocket.send(udpPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Connects to another client
     * @param code The code entered by the user to connect to another
     * @throws IOException
     */
    public void connectToOtherClient(String code) throws IOException {
        Socket tcpSocket;
        BufferedReader tcpIn;
        String uuidString;
        isConnected = false;
        try {
            serverIp = InetAddress.getByName("159.223.248.222");
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
            System.out.println(otherClientIP + ":" + otherClientPort);
            System.out.println(otherClientUUID);
            gotResponse = true;
        }
        isConnected = true;
        multiplayerGrid = new SudokuGrid();
        IncomingUdpThread u = new IncomingUdpThread(udpSocket);
        new Thread(u).start();
        OutgoingUdpThread ou = new OutgoingUdpThread(udpSocket,otherClientIP,otherClientPort);
        new Thread(ou).start();
        tcpSocket.close();
    }

    /**
     * Starts the multiplayer game
     * @param mode VS or Co-op
     */
    public void startMultiplayer(String mode) {
        if(!Client.multiplayerGridSet) {
            multiplayerGrid.setSudokuGrid(randomPuzzleGenerator.generateRandomPuzzle("Medium"));
            Client.hasGeneratedPuzzle = true;
        }
        OutgoingUdpThread.MessageQueue.add(multiplayerGrid.serialize().getBytes());
        switch (mode) {
            case "VS" -> startVS();
            case "Co-op" -> startCoOp();
        }
    }
    private void startVS() {
         startTime = System.nanoTime();

    }

    /**
     * Sends the time taken to complete the puzzle to the other player
     */
    public void stopVs() {
        long endTime = System.nanoTime();
         timeTaken = endTime - startTime;
        OutgoingUdpThread.MessageQueue.add(String.valueOf(timeTaken).getBytes());
    }
    private void startCoOp() {
    }

    /**
     * Sends the current state of the grid to the other player
     */
    public void sendSudoku() {
        OutgoingUdpThread.MessageQueue.add(multiplayerGrid.serialize().getBytes());
    }
}
