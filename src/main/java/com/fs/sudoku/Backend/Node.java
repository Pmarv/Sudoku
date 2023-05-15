package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class Node {
    public Node up;
    public Node down;
    public Node left;
    public Node right;

    Column column;

    public Node() {
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    public List<String> getCurrentSolution() {
        List<String> currentSolution = new ArrayList<>();
        currentSolution.add(this.column.columnName);
        for(Node i = this.right;this != i;i = i.right) {
            currentSolution.add(i.column.columnName);
        }
        return  currentSolution;
    }

}
