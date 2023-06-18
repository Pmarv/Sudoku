package com.fs.sudoku.Backend;

import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.*;

@NoArgsConstructor
public class SudokuValidator {
    SudokuGrid sudokuGrid = new SudokuGrid();
    private boolean validateList(List<Integer> toValidate) {
        List<Integer> validatedNumbers = new ArrayList<>();
        for(Integer value : toValidate) {
            if(value == 0) {
                continue;
            }
            if(!validatedNumbers.contains(value)) {
                validatedNumbers.add(value);
            } else {
                return false;
            }
        }
        return true;
    }
    private boolean validateSubGrid(Pair<Integer,Integer> key) {
        Set<Integer> subGridValues = new HashSet<>();
        Set<Pair<Integer,Integer>> subGridKeys = new HashSet<>();
        subGridKeys.add(key);
        int keySubGrid = sudokuGrid.getSubGrid(key);
        for(Pair<Integer,Integer> test : sudokuGrid.getSudokuGrid().keySet()) {
            int tempSubGrid = sudokuGrid.getSubGrid(test);
            if(keySubGrid == tempSubGrid) {
                subGridKeys.add(test);
            }
        }
        for(Pair<Integer,Integer> subGridKey : subGridKeys) {
            if(sudokuGrid.getValue(subGridKey) != 0) {
                if (!subGridValues.add(sudokuGrid.getValue(subGridKey))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Simulates entering a key and number and determines if the grid is valid
     * @param grid SudokuGrid to validate
     * @param key Key to validate
     * @param num Number to validate
     * @return True if the grid is valid, false otherwise
     */
    public boolean validateGrid(Map<Pair<Integer,Integer>,Integer> grid,Pair<Integer,Integer> key,Integer num) {
        if (!key.contains(0)) {
            sudokuGrid.setSudokuGrid(grid);
            sudokuGrid.setValue(key, num);
            return validateSubGrid(key) && validateList(sudokuGrid.getColumn(key)) && validateList(sudokuGrid.getRow(key));
        }
        else  {
            return false;
        }
    }

    /**
     * @param grid SudokuGrid to validate
     * @return True if the grid is valid, false otherwise
     */
    public boolean validate(SudokuGrid grid) {
        sudokuGrid = grid;
        for (int i = 0; i < 9; i++) {
            Pair<Integer,Integer> key = new Pair<>(i,0);
            if(!validateList(grid.getRow(key))) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            Pair<Integer,Integer> key = new Pair<>(0,i);
            if(!validateList(grid.getColumn(key))) {
                return false;
            }
        }
        for(int i = 0; i<9;i++) {
            for (int j = 0; j < 9; j++) {
                Pair<Integer,Integer> key = new Pair<>(i,j);
                if(!validateSubGrid(key)) {
                    return false;
                }
            }
        }
        return true;
    }
}
