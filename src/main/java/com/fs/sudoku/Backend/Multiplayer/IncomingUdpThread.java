package com.fs.sudoku.Backend.Multiplayer;

import com.fs.sudoku.Frontend.MultiplayerController;

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
                DatagramPacket dgPacket = new DatagramPacket(new byte[16384], 16384);
//                System.out.println("Waiting for a packet");
                dgSocket.setSoTimeout(60000);
                dgSocket.receive(dgPacket);
//                System.out.println("Got a packet");
                String Message = new String(dgPacket.getData()).trim();
                if(!Message.equals("ping")) {
                    System.out.println(Message);
                }
                if (Message.equals("Ping")) {
                    System.out.println("Got a ping");
                    continue;
                }
                if (Message.contains("val0")) {
                    if (!Client.multiplayerGridSet && !Client.hasGeneratedPuzzle) {
                        System.out.println("Got a puzzle, am second player");
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.multiplayerGridSet = true;
                        Client.first = false;
                        Client.lastPlayer = true;
                    } else if(Client.coop && Client.lastPlayer) {
                        System.out.println("Got a puzzle, can play now");
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.lastPlayer = false;
                        MultiplayerController.isUptoDate = false;
                    }
                    else {
                        System.out.println("Got a puzzle, am first player");
                        Client.first = true;
                        Client.multiplayerGridSet = true;
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
