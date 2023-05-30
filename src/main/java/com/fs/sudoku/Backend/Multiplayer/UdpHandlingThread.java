package com.fs.sudoku.Backend.Multiplayer;

import com.fs.sudoku.Backend.RandomPuzzleGenerator;
import com.fs.sudoku.Backend.SudokuGrid;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class UdpHandlingThread implements Runnable{

    private DatagramSocket dgSocket;
    private String otherIp;
    private String otherPort;
    private SudokuGrid sudokuGrid = new SudokuGrid();
    private RandomPuzzleGenerator randomPuzzleGenerator;
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
            DatagramPacket dgPacketReceivePing = new DatagramPacket(new byte[1024],1024);
            DatagramPacket dgPacketReceiveOther = new DatagramPacket(new byte[1024],1024);
            DatagramPacket dgPacketReceiveMap = new DatagramPacket(new byte[1024],1024);
            dgSocket.send(dgPacketSend);
                dgSocket.receive(dgPacketReceivePing);
                String message = new String(dgPacketReceivePing.getData());
                System.out.println("got Message: " + message.trim());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double sendingDoubleShake = Math.random()*100;
                byte[] sendingBytesShake = Double.toString(sendingDoubleShake).getBytes();
                DatagramPacket dgPacketSendShake = new DatagramPacket(sendingBytesShake,sendingBytesShake.length, InetAddress.getByName(otherIp),Integer.parseInt(otherPort));
                dgSocket.send(dgPacketSendShake);
                dgSocket.receive(dgPacketReceiveOther);
                if (sendingDoubleShake > Double.parseDouble(new String(dgPacketReceiveOther.getData()))) {
                    sudokuGrid.setSudokuGrid(randomPuzzleGenerator.generateRandomPuzzle());
                    byte[] sendingMapBytes = sudokuGrid.serialize().getBytes();
                    DatagramPacket dgPacketSendMap = new DatagramPacket(sendingMapBytes,sendingMapBytes.length, InetAddress.getByName(otherIp),Integer.parseInt(otherPort));
                    dgSocket.send(dgPacketSendMap);
                    sudokuGrid.printGrid();
                } else {
                    dgSocket.receive(dgPacketReceiveMap);
                    sudokuGrid.deserializeToSudoku(new String(dgPacketReceiveMap.getData()).trim());
                    sudokuGrid.printGrid();
                }
                while(true) {
                    dgSocket.receive(dgPacketReceivePing);
                    message = new String(dgPacketReceivePing.getData());
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
