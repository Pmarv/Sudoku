package com.fs.sudoku.Backend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service
public class SudokuService implements CommandLineRunner {


    SudokuGrid sudokuGrid = new SudokuGrid();
    SudokuGrid sudoGrid = new SudokuGrid();
    @Autowired
    RandomPuzzleGenerator randomPuzzleGenerator;
    @Autowired
    Client client;

    Exact_Cover_solver exactCoverSolver = new Exact_Cover_solver();
//    SudokuValidator sudokuValidator = new SudokuValidator();

    @Override
    public void run(String... args)  {
//        exactCoverSolver = new Exact_Cover_solver();
//        long startTime = System.nanoTime();
//        sudokuGrid.setSudokuGrid(randomPuzzleGenerator.generateRandomPuzzle("Easy"));
//        sudoGrid.deserializeToSudoku(sudokuGrid.serialize());
//        sudokuGrid.getValue(new Pair<>(0,0));
//        for (long i = 0L; i < 9L; i++) {
//            for (long j = 0L; j < 9L; j++) {
//                System.out.println(sudoGrid.getValueLong(new Pair<>(i,j)));
//            }
//        }
//        long entTime = System.nanoTime();
//        System.out.println((entTime-startTime));
//        Scanner input = new Scanner(System.in);
//        System.out.println("Here is your Sudoku");
//        sudokuGrid.printGrid();
//
//        while (!sudokuGrid.isComplete()) {
//            System.out.println("Enter the Row you want to edit: ");
//            int row = input.nextInt();
//            System.out.println("Enter the Column you want to edit: ");
//            int column = input.nextInt();
//            System.out.println("Enter the number you want: ");
//            int num = input.nextInt();
//            sudokuGrid.setValue(new Pair<>(row-1,column-1),num);
//            sudokuGrid.printGrid();
//        }
//        System.out.println("Congrats");
//        try {
//            client.connectToOtherClient("1234");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }
}
