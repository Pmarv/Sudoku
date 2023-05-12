package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;

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

}
