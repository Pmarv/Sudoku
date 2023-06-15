package com.fs.sudoku.Backend;

import lombok.NoArgsConstructor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
@Component
public class RandomPuzzleGenerator {

    SudokuGrid sudokuGrid = new SudokuGrid();
    Exact_Cover_solver exactCoverSolver = new Exact_Cover_solver();
    public Map<Pair<Integer,Integer>,Integer> generateRandomPuzzle() {
        exactCoverSolver = new Exact_Cover_solver();
        sudokuGrid.generateEmptyGrid();
        while (exactCoverSolver.getSolutions() < 1) {
            for (int i = 0; i < 6; i++) {
                Pair<Integer, Integer> key = new Pair<>((int) (Math.random() * 10), (int) (Math.random() * 10));
                while (key.contains(9)) {
                    key = new Pair<>((int) (Math.random() * 10), (int) (Math.random() * 10));
                }
                sudokuGrid.setValue(key, (int) (Math.random() * 10));
            }
            int[][] cover = exactCoverSolver.sudokuToCover(sudokuGrid.getSudokuGrid());
            exactCoverSolver.solve(cover, true);
        }
        sudokuGrid.setSudokuGrid(exactCoverSolver.getPartialSolvedGrid().getSudokuGrid());
        exactCoverSolver.setSolutions(0);
        SudokuGrid tempGrid = sudokuGrid;
//        tempGrid.printGrid();
        while (exactCoverSolver.getSolutions() != 1) {
            exactCoverSolver.setSolutions(0);
            int[][] coverNew = exactCoverSolver.sudokuToCover(tempGrid.getSudokuGrid());
            exactCoverSolver.solve(coverNew,false);
//            System.out.println(exactCoverSolver.getSolutions());
            if(exactCoverSolver.getSolutions() != 1) {
                tempGrid.setSudokuGrid(exactCoverSolver.nodeToPartialSolution(exactCoverSolver.getCurrentSolutionCopy()));
//                tempGrid.printGrid();
            }
        }
        exactCoverSolver.getSolvedGrid().printGrid();
        return tempGrid.getSudokuGrid();
    }
}

