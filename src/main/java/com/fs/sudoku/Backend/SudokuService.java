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


    }
}
