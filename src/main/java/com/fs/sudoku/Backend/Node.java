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

    /**
     * Constructor for the node
     */
    public Node() {
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

}
