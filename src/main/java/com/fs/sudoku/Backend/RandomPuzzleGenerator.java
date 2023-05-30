package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class RandomPuzzleGenerator {

    SudokuGrid sudokuGrid = new SudokuGrid();
    Exact_Cover_solver exactCoverSolver;
    SudokuValidator sudokuValidator = new SudokuValidator();
    public Map<Pair<Integer,Integer>,Integer> generateRandomPuzzle() {
        int attempts = 0;
        exactCoverSolver = new Exact_Cover_solver();
        sudokuGrid.generateEmptyGrid();
        while (exactCoverSolver.getSolutions() < 1) {
            for (int i = 0; i < 6; i++) {
                Pair<Integer, Integer> key = new Pair<>((int) (Math.random() * 10), (int) (Math.random() * 10));
                while (key.contains(9)) {
                    key = new Pair<>((int) (Math.random() * 10), (int) (Math.random() * 10));
                }
                int value = (int) (Math.random() * 10);
                if(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),key,value)) {
                sudokuGrid.setValue(key,value);
                }
            }
            int[][] cover = exactCoverSolver.sudokuToCover(sudokuGrid.getSudokuGrid());
            exactCoverSolver.solve(cover, true);
        }
        sudokuGrid.setSudokuGrid(exactCoverSolver.getPartialSolvedGrid().getSudokuGrid());
        exactCoverSolver.setSolutions(0);
        SudokuGrid tempGrid = sudokuGrid;
//        tempGrid.printGrid();

        while (exactCoverSolver.getSolutions() != 1 && attempts < 40) {
            exactCoverSolver.setSolutions(0);
            int[][] coverNew = exactCoverSolver.sudokuToCover(tempGrid.getSudokuGrid());
            exactCoverSolver.solve(coverNew,false);
//            System.out.println(exactCoverSolver.getSolutions());
            if(exactCoverSolver.getSolutions() != 1) {
                tempGrid.setSudokuGrid(exactCoverSolver.nodeToPartialSolution(exactCoverSolver.getCurrentSolutionCopy()));
//                tempGrid.printGrid();
            }
            attempts++;
        }
        if(attempts > 39) {
            return generateRandomPuzzle();
        }
        exactCoverSolver.getSolvedGrid().printGrid();
        return tempGrid.getSudokuGrid();
    }
}

