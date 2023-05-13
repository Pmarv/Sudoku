package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

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
//                {0,0,1,0,1,1,0},
//                {1,0,0,1,0,0,1},
//                {0,1,1,0,0,1,0},
//                {1,0,0,1,0,0,0},
//                {0,1,0,0,0,0,1},
//                {0,0,0,1,1,0,1},
//                {1,0,0,1,0,0,0}
//        };
//        exactCoverSolver.solve(test);
//        System.out.println("test");
        Pair<Integer,Integer> key = new Pair<>(0,0);
        sudokuGrid.setValue(key,1);
        Pair<Integer,Integer> key1 = new Pair<>(1,1);
        sudokuGrid.setValue(key,1);
        Pair<Integer,Integer> key2 = new Pair<>(1,0);
        sudokuGrid.setValue(key,2);
//        try {
//            int[][] matrix = exactCoverSolver.sudokuToCover(sudokuGrid.getSudokuGrid());
//            exactCoverSolver.solve(matrix);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
