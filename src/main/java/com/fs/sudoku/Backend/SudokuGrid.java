package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
@Getter @Setter
public class SudokuGrid {
    private Map<Pair<Integer,Integer>, Integer> sudokuGrid = new TreeMap<>();
    private Pair<Integer,Integer> key;

    public void generateEmptyGrid() {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                key = new Pair<>(i, j);
                sudokuGrid.put(key,0);
            }
        }
    }

    public int getValue(Pair<Integer,Integer> key) {
        return sudokuGrid.get(key);
    }

    public void setValue(Pair<Integer,Integer> key, Integer value) {
        sudokuGrid.put(key,value);
    }
    public SudokuGrid(){
        generateEmptyGrid();
    }
}
