package com.fs.sudoku.Backend;

public class Root extends Column{

    public Root(String columnName) {
        super(columnName);
    }
    public Column findMinColumn() {
        Column result = null;
        int nodeCount = Integer.MAX_VALUE;
        for (Column i = this.right; i != this; i = i.right) {
            if(i.nodeCount < nodeCount) {
                nodeCount = i.nodeCount;
                result = i;
            }
        }
        return result;
    }
}
