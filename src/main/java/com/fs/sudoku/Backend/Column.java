package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Column extends Node {
    int nodeCount;
    String columnName;

    public Column(String columnName) {
        super();
        nodeCount = 0;
        this.columnName = columnName;
        column = this;
    }

}
