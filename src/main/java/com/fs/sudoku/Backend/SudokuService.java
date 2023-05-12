package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class SudokuService implements CommandLineRunner {


    SudokuGrid sudokuGrid = new SudokuGrid();



    @Override
    public void run(String... args) {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                Pair<Integer,Integer> Key = new Pair<>(i,j);
                System.out.print(Key);
                sudokuGrid.setValue(Key,1);
                System.out.println(sudokuGrid.getValue(Key));
            }
        }
    }
}
