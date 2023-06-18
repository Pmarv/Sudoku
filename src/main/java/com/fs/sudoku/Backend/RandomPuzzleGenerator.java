package com.fs.sudoku.Backend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@NoArgsConstructor
public class RandomPuzzleGenerator {

    SudokuGrid sudokuGrid = new SudokuGrid();
    Exact_Cover_solver exactCoverSolver;
    SudokuValidator sudokuValidator = new SudokuValidator();

    /**
     * @param mode Mode of the game
     * @return Map of the generated puzzle
     */
    public Map<Pair<Integer,Integer>,Integer> generateRandomPuzzle(String mode) {
        int attempts = 0;
        exactCoverSolver = new Exact_Cover_solver();
        sudokuGrid.generateEmptyGrid();
        if(Client.multiplayerGridSet) {
            return null;
        }
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
            exactCoverSolver.solve(cover, true,mode);
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
                tempGrid.setSudokuGrid(exactCoverSolver.nodeToPartialSolution(exactCoverSolver.getCurrentSolutionCopy(),mode));
//                tempGrid.printGrid();
            }
            attempts++;
        }
        if(attempts > 39) {
            return generateRandomPuzzle(mode);
        }
        if(Client.multiplayerGridSet) {
            return null;
        }
        return tempGrid.getSudokuGrid();
    }
}

