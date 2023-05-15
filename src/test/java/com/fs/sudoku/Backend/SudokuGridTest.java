package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGridTest {
    SudokuGrid grid;
    List<Integer> expectedColumn1 = new ArrayList<>();
    List<Integer> expectedColumn2 = new ArrayList<>();
    List<Integer> expectedColumn3 = new ArrayList<>();
    List<Integer> expectedColumn4 = new ArrayList<>();
    List<Integer> expectedColumn5 = new ArrayList<>();
    List<Integer> expectedColumn6 = new ArrayList<>();
    List<Integer> expectedColumn7 = new ArrayList<>();
    List<Integer> expectedColumn8 = new ArrayList<>();
    List<Integer> expectedColumn9 = new ArrayList<>();
    List<Integer> expectedRow1 = new ArrayList<>();
    List<Integer> expectedRow2 = new ArrayList<>();
    List<Integer> expectedRow3 = new ArrayList<>();
    List<Integer> expectedRow4 = new ArrayList<>();
    List<Integer> expectedRow5 = new ArrayList<>();
    List<Integer> expectedRow6 = new ArrayList<>();
    List<Integer> expectedRow7 = new ArrayList<>();
    List<Integer> expectedRow8 = new ArrayList<>();
    List<Integer> expectedRow9 = new ArrayList<>();
    int[][] subGrids = new int[][]{};


    @BeforeEach
    void setUp() {
        grid = new SudokuGrid();
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
        for(int i = 0; i <9;i++) {
            expectedColumn1.add(testArray[i][0]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn2.add(testArray[i][1]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn3.add(testArray[i][2]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn4.add(testArray[i][3]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn5.add(testArray[i][4]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn6.add(testArray[i][5]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn7.add(testArray[i][6]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn8.add(testArray[i][7]);
        }
        for(int i = 0; i <9;i++) {
            expectedColumn9.add(testArray[i][8]);
        }
        for (int i : testArray[0]) {
            expectedRow1.add(i);
        }
        for (int i : testArray[1]) {
            expectedRow2.add(i);
        }
        for (int i : testArray[2]) {
            expectedRow3.add(i);
        }
        for (int i : testArray[3]) {
            expectedRow4.add(i);
        }
        for (int i : testArray[4]) {
            expectedRow5.add(i);
        }
        for (int i : testArray[5]) {
            expectedRow6.add(i);
        }
        for (int i : testArray[6]) {
            expectedRow7.add(i);
        }
        for (int i : testArray[7]) {
            expectedRow8.add(i);
        }
        for (int i : testArray[8]) {
            expectedRow9.add(i);
        }
        grid.intArrayToSudoku(testArray);

        subGrids = new int[][]{
                {0,0,0,1,1,1,2,2,2},
                {0,0,0,1,1,1,2,2,2},
                {0,0,0,1,1,1,2,2,2},
                {3,3,3,4,4,4,5,5,5},
                {3,3,3,4,4,4,5,5,5},
                {3,3,3,4,4,4,5,5,5},
                {6,6,6,7,7,7,8,8,8},
                {6,6,6,7,7,7,8,8,8},
                {6,6,6,7,7,7,8,8,8},
        };

    }

    @Test
    void getValue() {
        assertEquals(4,grid.getValue(new Pair<>(1,0)));
    }

    @Test
    void setValue() {
        grid.setValue(new Pair<>(1,1),1);
        assertEquals(1,grid.getValue(new Pair<>(1,1)));
    }

    @Test
    void getColumn() {
        assertEquals(expectedColumn1,grid.getColumn(new Pair<>(0,0)));
        assertEquals(expectedColumn2,grid.getColumn(new Pair<>(1,1)));
        assertEquals(expectedColumn3,grid.getColumn(new Pair<>(2,2)));
        assertEquals(expectedColumn4,grid.getColumn(new Pair<>(3,3)));
        assertEquals(expectedColumn5,grid.getColumn(new Pair<>(4,4)));
        assertEquals(expectedColumn6,grid.getColumn(new Pair<>(5,5)));
        assertEquals(expectedColumn7,grid.getColumn(new Pair<>(6,6)));
        assertEquals(expectedColumn8,grid.getColumn(new Pair<>(7,7)));
        assertEquals(expectedColumn9,grid.getColumn(new Pair<>(8,8)));
    }

    @Test
    void getRow() {
        assertEquals(expectedRow1,grid.getRow(new Pair<>(0,0)));
        assertEquals(expectedRow2,grid.getRow(new Pair<>(1,1)));
        assertEquals(expectedRow3,grid.getRow(new Pair<>(2,2)));
        assertEquals(expectedRow4,grid.getRow(new Pair<>(3,3)));
        assertEquals(expectedRow5,grid.getRow(new Pair<>(4,4)));
        assertEquals(expectedRow6,grid.getRow(new Pair<>(5,5)));
        assertEquals(expectedRow7,grid.getRow(new Pair<>(6,6)));
        assertEquals(expectedRow8,grid.getRow(new Pair<>(7,7)));
        assertEquals(expectedRow9,grid.getRow(new Pair<>(8,8)));
    }

    @Test
    void getSubGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(subGrids[i][j],grid.getSubGrid(new Pair<>(i,j)));
            }
        }
    }
}