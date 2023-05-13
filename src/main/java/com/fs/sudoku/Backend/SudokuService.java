package com.fs.sudoku.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SudokuService implements CommandLineRunner {


    SudokuGrid sudokuGrid = new SudokuGrid();


    @Autowired
    Exact_Cover_solver exactCoverSolver;

    @Override
    public void run(String... args) {
//        for(int i = 1; i < 10; i++) {
//            for(int j = 1; j < 10; j++) {
//                Pair<Integer,Integer> Key = new Pair<>(i,j);
//                System.out.print(Key);
//                sudokuGrid.setValue(Key,1);
//                System.out.println(sudokuGrid.getValue(Key));
//            }
//        }
//        int[][] test = {
//                {0,0,1,0,1,1,0,0},
//                {1,0,0,1,0,0,1,0},
//                {0,1,1,0,0,1,0,0},
//                {1,0,0,1,0,0,0,0},
//                {0,1,0,0,0,0,1,0},
//                {0,0,0,1,1,0,1,0},
//                {0,0,0,0,0,0,0,0}
//        };
//        exactCoverSolver.solve(test);
//        System.out.println("test");
        int[][]test2 = {
                {0,0,0,0,7,4,0,0,5},
                {0,0,0,0,0,0,2,0,0},
                {0,0,0,3,0,5,0,7,6},
                {0,0,9,4,0,2,0,8,0},
                {1,8,0,0,6,0,0,0,0},
                {0,3,0,0,0,0,0,0,0},
                {2,0,0,5,0,0,8,0,0},
                {3,5,0,0,0,1,0,0,0},
                {0,0,8,0,0,0,9,0,0}
        };
        sudokuGrid.intArrayToSudoku(test2);
        exactCoverSolver.printGrid(sudokuGrid.getSudokuGrid());
//        System.out.println("test");
        try {
            int[][] matrix = exactCoverSolver.sudokuToCover(sudokuGrid.getSudokuGrid());
            System.out.println("test");
            exactCoverSolver.solve(matrix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
