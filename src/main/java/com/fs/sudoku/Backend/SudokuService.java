package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

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
        int[][] test = {
                {0,0,1,0,1,1,0},
                {1,0,0,1,0,0,1},
                {0,1,1,0,0,1,0},
                {1,0,0,1,0,0,0},
                {0,1,0,0,0,0,1},
                {0,0,0,1,1,0,1}
        };
        exactCoverSolver.solve(test);
        System.out.println("test");

    }
}
