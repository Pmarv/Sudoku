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
    private Map<Pair<Integer,Integer>,Integer> subGridCoordinates = new TreeMap<>();

    public void generateEmptyGrid() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                key = new Pair<>(i, j);
                sudokuGrid.put(key,0);
            }
        }
    }

    /**
     * populates the subGridCoordinates map with the coordinates of the subgrid
     */
    private void populateSubGridCoordinates() {
        int subGrid = 0;
            for(int y=0;y<4;y++) {
                for(int x=0;x<4;x++) {
                    key = new Pair<>(x,y);
                    subGridCoordinates.put(key,subGrid++);
                }
            }
        System.out.println(subGridCoordinates);
    }

    /**
     * constructor for the grid which also calls populateSubGridCoordinates
     */
    public SudokuGrid(){
//      generateEmptyGrid();
        populateSubGridCoordinates();
    }


    /**
     * takes a set of coordinates and gives back the associated value
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the value at the given coordinates
     */
    public int getValue(Pair<Integer,Integer> key) {
        return sudokuGrid.get(key);
    }

    /**
     * takes a set of coordinates and their corresponding value to set in the grid
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @param value An Integer value that should be set for the coordinates given
     */
    public void setValue(Pair<Integer,Integer> key, Integer value) {
        if(value < 10 && value > 0) {
        sudokuGrid.put(key,value);
        System.out.println(validateNumberSet(key));
        }
    }

    /**
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns the values of all points on the same row as the key
     */
    public List<Integer> getRow(Pair<Integer,Integer> key) {
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
    public List<Integer> getColumn(Pair<Integer,Integer> key) {
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
        int x = key.getValue0();
        int y = key.getValue1();
        Pair<Integer,Integer> subGridKey = new Pair<>((int) Math.ceil((float) x /3),(int) Math.ceil((float) y /3));
        return subGridCoordinates.get(subGridKey);
    }

    /**
     * Validates the row of the given key by looking if there are any duplicates
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns false if there is a duplicate number in the row, returns true if there is no duplicated number
     */
    private boolean validateRow(Pair<Integer,Integer> key) {
        List<Integer> row = getRow(key);
        List<Integer> validatedRow = new ArrayList<>();
        for(Integer value : row) {
            if(value == null) {
                continue;
            }
            if(!validatedRow.contains(value)) {
                validatedRow.add(value);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the Column of the given key by looking if there are any duplicates
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns false if there is a duplicate number in the column, returns true if there is no duplicated number
     */
    private boolean validateColumn(Pair<Integer,Integer> key) {
        List<Integer> column = getColumn(key);
        List<Integer> validatedColumn = new ArrayList<>();
        for(Integer value : column) {
            if(value == null) {
                continue;
            }
            if(!validatedColumn.contains(value)) {
                validatedColumn.add(value);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the subGrid of the given key by looking if there are any duplicates
     * @param key Pair of Integers(x,y) are given that correspond to coordinates in the grid
     * @return returns false if there is a duplicate number in the subgrid, returns true if there is no duplicated number
     */
    private boolean validateSubGrid(Pair<Integer,Integer> key) {
        Set<Integer> subGridValues = new HashSet<>();
        Set<Pair<Integer,Integer>> subGridKeys = new HashSet<>();
        subGridKeys.add(key);
        int keySubGrid = getSubGrid(key);
        for(Pair<Integer,Integer> test : sudokuGrid.keySet()) {
            int tempSubGrid = getSubGrid(test);
            if(keySubGrid == tempSubGrid) {
                subGridKeys.add(test);
            }
        }
        for(Pair<Integer,Integer> subGridKey : subGridKeys) {
               if(!subGridValues.add(sudokuGrid.get(subGridKey))) {
                   return false;
               }
        }
        return true;
    }

    /**
     *
     * @param key returns false if there is a duplicate number in the row, returns true if there is no duplicated number
     * @return returns the result of all other validation methods and short circuits if one of them returns false
     */
    public boolean validateNumberSet(Pair<Integer,Integer> key) {
        return validateColumn(key) && validateRow(key) && validateSubGrid(key);
    }
}
