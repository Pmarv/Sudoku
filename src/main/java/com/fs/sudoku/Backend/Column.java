package com.fs.sudoku.Backend;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Column extends Node {
    int nodeCount;
    String columnName;
    Column right;
    Column left;
    public Column(String columnName) {
        super();
        nodeCount = 0;
        this.columnName = columnName;
        column = this;
    }

    public void coverColumn() {
        column.right.left = column.left;
        column.left.right = column.right;
        for (Node i = column.down;i != column;i=i.down) {
            for (Node j = i.right;i!=j;j = j.right) {
                j.down.up = j.up;
                j.up.down = j.down;
                j.column.nodeCount = j.column.nodeCount - 1;
            }
        }
    }
    public void uncoverColumn() {
        for(Node i = column.up;i != column;i=i.up) {
            for(Node j = i.left;j != i; j = j.left) {
                j.column.nodeCount = j.column.nodeCount + 1;
                j.down.up = j;
                j.up.down = j;
            }
        }
        column.right.left = column;
        column.left.right = column;
    }
}
