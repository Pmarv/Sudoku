package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter @Setter
public class SudokuGrid {
    private Map<Pair<Integer,Integer>, Integer> sudokuGrid = new TreeMap<>();
    private Pair<Integer,Integer> key;
    private SudokuValidator sudokuValidator;
    public void generateEmptyGrid() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                key = new Pair<>(i, j);
                sudokuGrid.put(key,0);
            }
        }
    }
    public void setSudokuGrid(Map<Pair<Integer,Integer>,Integer> grid) {
        this.generateEmptyGrid();
        for(Pair<Integer,Integer> key : grid.keySet()) {
            this.setValue(key,grid.get(key));
        }
    }

    /**
     * populates the subGridCoordinates map with the coordinates of the subgrid
     */


    /**
     * constructor for the grid which also calls populateSubGridCoordinates
     */
    public SudokuGrid(){
//      generateEmptyGrid();
    }


    /**
     * takes a set of coordinates and gives back the associated value
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the value at the given coordinates
     */
    public int getValue(Pair<Integer,Integer> key) {
        if(key.getValue1() < 9  && key.getValue0() < 9) {
        return sudokuGrid.get(key);
        }
        else {
            return 0;
        }
    }

    /**
     * takes a set of coordinates and their corresponding value to set in the grid
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @param value An Integer value that should be set for the coordinates given
     */
    public void setValue(Pair<Integer,Integer> key, Integer value) {
        if(value < 10 && value > 0) {
        sudokuGrid.put(key,value);
        }

    }

    /**
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the values of all points on the same row as the key
     */
    public List<Integer> getColumn(Pair<Integer,Integer> key) {
        List<Integer> result = new ArrayList<>();
        for(int j=0;j<9;j++) {
            key = new Pair<>(j,key.getValue1());
            result.add(sudokuGrid.get(key));
        }
        return result;
    }

    /**
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the values of all points on the same column as the key
     */
    public List<Integer> getRow(Pair<Integer,Integer> key) {
        List<Integer> result = new ArrayList<>();
        for(int j=0;j<9;j++) {
            key = new Pair<>(key.getValue0(),j);
            result.add(sudokuGrid.get(key));
        }
        return result;
    }

    /**
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the subgrid of the given key
     */
    public Integer getSubGrid(Pair<Integer,Integer> key) {
        int row = key.getValue0();
        int col = key.getValue1();
        return col /3 + row - row % 3;
    }


    public void intArrayToSudoku(int[][] array) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Pair<Integer,Integer> key = new Pair<>(i,j);
                sudokuGrid.put(key,array[i][j]);
            }
        }
    }




    public void printGrid() {
        Collection<Integer> gridValues = sudokuGrid.values();
        Object[] array = gridValues.toArray();
        StringBuilder str = new StringBuilder();
        int counter = 0;
        int rowCounter = 1;
        for(Object value : array) {
            switch (counter) {
                case 3, 6 -> {
                    str.append("|");
                    str.append(" ");
                    str.append(value);
                    str.append(" ");
                    counter++;
                }
                case 8 -> {
                    str.append(value);
                    str.append("\n");
                    if (rowCounter == 3 || rowCounter == 6) {
                        str.append("--------------------- \n");
                    }
                    rowCounter++;
                    counter = 0;
                }
                default -> {
                    str.append(value);
                    str.append(" ");
                    counter++;
                }
            }
        }
        System.out.println(str);
    }
    public boolean isComplete() {
        sudokuValidator = new SudokuValidator();
        for (Integer value:sudokuGrid.values()) {
            if(value == 0) {
                return false;
            }
        }
        return sudokuValidator.validate(this);
    }
}
