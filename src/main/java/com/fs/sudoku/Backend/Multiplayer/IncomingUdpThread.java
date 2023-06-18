package com.fs.sudoku.Backend.Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Pattern;

public class IncomingUdpThread implements Runnable{

    private final DatagramSocket dgSocket;

    public IncomingUdpThread(DatagramSocket dgSocket) {
        this.dgSocket = dgSocket;
    }
    @Override
    public void run() {
        try {
            System.out.println("Entering Incoming Thread");
            while(Client.isConnected) {
                DatagramPacket dgPacket = new DatagramPacket(new byte[1024], 1024);
//                System.out.println("Waiting for a packet");
                dgSocket.receive(dgPacket);
//                System.out.println("Got a packet");
                String Message = new String(dgPacket.getData()).trim();
                System.out.println(Message);
                if (Message.equals("Ping")) {
//                    System.out.println("Got a ping");
                    continue;
                }
                if (Message.contains("val0")) {
                    if (Client.multiplayerGrid.getSudokuGrid() == null && !Client.multiplayerGridSet) {
                        System.out.println("Got a puzzle, am second player");
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.multiplayerGridSet = true;
                        Client.first = false;
                    }
                    else {
                        System.out.println("Got a puzzle, am first player");
                        Client.first = true;
                        Client.multiplayerGridSet = true;
                    }
                    if(Client.coop && Client.lastPlayer) {
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.lastPlayer = false;
                    }
                }
                else if(!Pattern.matches("[a-zA-Z]+", Message)) {
                    Client.OpponentTime = Long.parseLong(Message.trim());
                    Client.vsWinOrLose = Client.OpponentTime <= Client.timeTaken;
                }

            }

        } catch (IOException e) {
            Client.isConnected = false;
            throw new RuntimeException(e);
        }
    }
}
