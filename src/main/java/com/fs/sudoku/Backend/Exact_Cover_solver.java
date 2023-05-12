package com.fs.sudoku.Backend;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Exact_Cover_solver {

    Map<Integer,Node> currentSolution = new HashMap<>();

    Root root;



//    Implementation of Knuth's Algorithm X with Dancing Links
//    paper and pseudocode at https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf
    private void search(int k) {
        if(root.right == root) {
            for(Node solutionRow:currentSolution.values()) {
                System.out.println(solutionRow.getCurrentSolution());
            }
        } else {
            System.out.println("Search: " + k);
            Column c = root.findMinColumn();
            c.coverColumn();
            for(Node r = c.down; r != c; r = r.down) {
                currentSolution.put(k,r);
                for(Node j = r.right;r != j; j = j.right) {
                    j.column.coverColumn();
                }
                search(k + 1);
                r = currentSolution.get(k);
                c = r.column;
                for(Node j = r.left;j != r; j = j.left) {
                    j.column.uncoverColumn();
                }
            }
            c.uncoverColumn();
        }
    }

    /**
     * this first turns a given problem into a quad linked list and then solves it using Knuth's Algorithm X and Dancing Links
     * @param matrix an exact cover problem in form of a double int array filled with 1s and 0s
     */
    public void solve(int[][] matrix) {
        setUpProblem(matrix);
        search(0);
    }

    private void setUpProblem(int[][] test) {
        Column lastColumn = null;
        Node lastNodeTouched;
        Node firstRowNode = null;
        Node lastRowNodeTouched = null;
        int countRowNodes = 0;

        for (int i = 0; i <= test[0].length; i++) {
            if (i == 0) {
                root = new Root("Root");
                lastColumn = root;
            } else {
                lastColumn.right = new Column(Integer.toString(i));
                lastColumn.right.left = lastColumn;
                lastColumn = lastColumn.right;
            }
        }
        root.left = lastColumn;
        lastColumn.right = root;
        lastColumn = root.right;
        lastNodeTouched = lastColumn;
        for (int[] i : test) {
            for (int j : i) {
                if (j == 1) {
                    if (lastColumn.down == null) {
                        lastColumn.down = new Node();
                        lastColumn.down.up = lastColumn;
                        lastColumn.down.column = lastColumn;
                        lastColumn.up = lastColumn.down;
                        lastColumn.down.down = lastColumn;
                        lastColumn.nodeCount++;
                        if(countRowNodes == 0) {
                            firstRowNode = lastColumn.down;
                        } else {
                            lastColumn.down.left = lastRowNodeTouched;
                            lastRowNodeTouched.right = lastColumn.down;
                        }
                        lastRowNodeTouched = lastColumn.down;
                    } else {
                    while (lastNodeTouched.down != lastColumn) {
                        lastNodeTouched = lastNodeTouched.down;
                    }
                    lastNodeTouched.down = new Node();
                    lastNodeTouched.down.up = lastNodeTouched;
                    lastNodeTouched.down.column = lastColumn;
                    lastColumn.up = lastNodeTouched.down;
                    lastNodeTouched.down.down = lastColumn;
                    lastColumn.nodeCount++;
                    if(countRowNodes == 0) {
                        firstRowNode = lastNodeTouched.down;
                    } else {
                        lastNodeTouched.down.left = lastRowNodeTouched;
                        lastRowNodeTouched.right = lastNodeTouched.down;
                    }
                    lastRowNodeTouched = lastNodeTouched.down;
                    }
                    countRowNodes++;
                }
                lastColumn = lastColumn.right;
                lastNodeTouched = lastColumn;

            }
            assert lastRowNodeTouched != null;
            lastRowNodeTouched.right = firstRowNode;
            firstRowNode.left = lastRowNodeTouched;
            lastColumn = root.right;
            lastNodeTouched = lastColumn;
            countRowNodes = 0;
        }
        System.out.println();
    }

    public int[][] sudokuToCover(Map<Pair<Integer,Integer>,Integer> grid) {
        int[][] problem = new int[324][729];

        return problem;
    }
}
