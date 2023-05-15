package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuValidatorTest {

SudokuGrid sudokuGrid = new SudokuGrid();
SudokuValidator sudokuValidator = new SudokuValidator();
    @BeforeEach
    void setUp() {
        int[][]testArray = {
                {0,0,0,2,3,0,0,0,9},
                {4,0,0,7,0,0,2,0,0},
                {0,0,0,0,0,0,3,0,0},
                {7,3,2,0,0,0,0,0,0},
                {0,0,9,0,0,0,0,6,0},
                {0,0,4,0,0,0,1,9,0},
                {0,7,0,0,0,4,0,0,0},
                {5,0,0,0,9,0,0,0,0},
                {0,0,0,0,0,1,0,8,5}
        };

        sudokuGrid.intArrayToSudoku(testArray);
    }

    @Test
    void validateGrid() {
        assertTrue(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),new Pair<>(1,1),6));
        assertFalse(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),new Pair<>(1,1),4));
        assertFalse(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),new Pair<>(4,1),4));
        assertFalse(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),new Pair<>(1,1),2));
//        assertFalse(sudokuValidator.validateGrid(sudokuGrid.getSudokuGrid(),new Pair<>(1,1),4));
    }
}