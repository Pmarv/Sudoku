package com.fs.sudoku.Backend;

public class Root extends Column{

    /**
     * Standard constructor for the root
     * @param columnName Name of the column
     */
    public Root(String columnName) {
        super(columnName);
    }

    /**
     * Finds the column with the least amount of nodes
     * @return The column with the least amount of nodes
     */
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
