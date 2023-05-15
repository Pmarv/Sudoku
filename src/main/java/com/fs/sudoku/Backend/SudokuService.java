package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class SudokuService implements CommandLineRunner {


    SudokuGrid sudokuGrid = new SudokuGrid();

    @Autowired
    RandomPuzzleGenerator randomPuzzleGenerator;

    Exact_Cover_solver exactCoverSolver = new Exact_Cover_solver();
//    SudokuValidator sudokuValidator = new SudokuValidator();

    @Override
    public void run(String... args) {
        exactCoverSolver = new Exact_Cover_solver();
        sudokuGrid.setSudokuGrid(randomPuzzleGenerator.generateRandomPuzzle());
        Scanner input = new Scanner(System.in);
        System.out.println("Here is your Sudoku");
        sudokuGrid.printGrid();

        while (!sudokuGrid.isComplete()) {
            System.out.println("Enter the Row you want to edit: ");
            int row = input.nextInt();
            System.out.println("Enter the Column you want to edit: ");
            int column = input.nextInt();
            System.out.println("Enter the number you want: ");
            int num = input.nextInt();
            sudokuGrid.setValue(new Pair<>(row-1,column-1),num);
            sudokuGrid.printGrid();
        }
        System.out.println("Congrats");

    }
}
