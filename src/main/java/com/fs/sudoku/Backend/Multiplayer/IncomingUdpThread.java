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
            while(Client.isConnected) {
                DatagramPacket dgPacket = new DatagramPacket(new byte[1024], 1024);
                dgSocket.receive(dgPacket);
                String Message = dgPacket.getData().toString().trim();
                if (Message.equals("Ping")) {
                    continue;
                }
                if (Message.contains("\"val0\":")) {
                    if (Client.multiplayerGrid.getSudokuGrid() == null) {
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.multiplayerGridSet = true;
                        Client.first = false;
                    }
                    else {
                        Client.first = true;
                        Client.multiplayerGridSet = true;
                    }
                    if(Client.coop && Client.lastPlayer) {
                        Client.multiplayerGrid.deserializeToSudoku(Message.trim());
                        Client.lastPlayer = false;
                    }
                }
                if(!Pattern.matches("[a-zA-Z]+", Message)) {
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
