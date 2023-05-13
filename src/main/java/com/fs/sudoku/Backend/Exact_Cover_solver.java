package com.fs.sudoku.Backend;

import com.google.common.base.Splitter;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class Exact_Cover_solver {

    List<Node> currentSolution = new ArrayList<>();

    Root root;



//    Implementation of Knuth's Algorithm X with Dancing Links
//    paper and pseudocode at https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf
    private void search(int k) {
        if(root.right == root) {
             printGrid(nodeToSolution(currentSolution));
        } else {
//            System.out.println("Search: " + k);
            Column c = root.findMinColumn();
            c.coverColumn();
//            System.out.println("covering column: " + c.columnName);
            for(Node r = c.down; r != c; r = r.down) {
                currentSolution.add(r);
                for(Node j = r.right;r != j; j = j.right) {
                    j.column.coverColumn();
//                    System.out.println("covering column: " + j.column.columnName);
                }
                search(k + 1);
                r = currentSolution.remove(currentSolution.size()-1);
                c = r.column;
                for(Node j = r.left;j != r; j = j.left) {
                    j.column.uncoverColumn();
//                    System.out.println("uncovering column: " + j.column.columnName);
                }
            }
            c.uncoverColumn();
//            System.out.println("uncovering column: " + c.columnName);
        }

    }

    /**
     * this first turns a given problem into a quad linked list and then solves it using Knuth's Algorithm X and Dancing Links
     *
     * @param matrix an exact cover problem in form of a double int array filled with 1s and 0s
     *
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
                lastColumn.right = new Column(Integer.toString(i-1));
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

    public int[][] sudokuToCover(
            Map<Pair<Integer,Integer>,Integer> grid
    ) throws IOException {
        String test;
        int count = 0;
        int count2 = 0;
        int[][] problem = new int[729][324];
        File matrix = new File("9x9 cover matrix.txt");
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(matrix)));
        while(count < 729) {
            test = r.readLine();
            String[] test2 = test.split(" ");
            String test3 = test2[1];
            Iterable<String> help = Splitter.fixedLength(1).split(test3);
            for(String testing : help) {
                problem[count][count2] = Integer.parseInt(testing);
                count2++;
            }
            count2 = 0;
            count++;
        }
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                Pair<Integer,Integer> key = new Pair<>(row,column);
                if(grid.get(key) !=0) {
                    int rowindex = (grid.get(key)-1) + 81 * (row-1)+9*(column-1);
                    for (int k = 1; k <= 9; k++) {
                    int currentRow = (k-1) + 81 * (row-1)+9*(column-1);
                        if (currentRow != rowindex) {
                            Arrays.fill(problem[currentRow],0);
                        }

                    }
                }
            }

        }
        return problem;
    }
    private Map<Pair<Integer,Integer>,Integer> nodeToSolution(List<Node> currentSolution) {
        Map<Pair<Integer,Integer>,Integer> result= new TreeMap<>();
        for(Node node:currentSolution) {
            Node r = node;
            int minColumn = Integer.parseInt(r.column.columnName);
            for(Node j = node.right;j != node; j = j.right) {
                int column = Integer.parseInt(j.column.columnName);
                if(column < minColumn) {
                    minColumn = column;
                    r = j;
                }
            }
            int test = Integer.parseInt(r.column.columnName);
            int test2 = Integer.parseInt(r.right.column.columnName);
            int row = test / 9;
            int col = test % 9;
            int num = (test2 % 9) +1;
            Pair<Integer,Integer> key = new Pair<>(row,col);
            result.put(key,num);
//            System.out.println("Row: " + row + " Col: " + col + " Num: " + num);
        }
        return result;
    }
    public void printGrid(Map<Pair<Integer,Integer>,Integer> grid) {
        Collection<Integer> gridValues = grid.values();
        Object[] array = gridValues.toArray();
        StringBuilder str = new StringBuilder();
        int counter = 0;
        int rowCounter = 1;
        for(Object value : array) {
            switch (counter) {
                case 3, 6 -> {
                    str.append("|");
                    str.append(" ");
                    str.append(value);
                    str.append(" ");
                    counter++;
                }
                case 8 -> {
                    str.append(value);
                    str.append("\n");
                    if (rowCounter == 3 || rowCounter == 6) {
                        str.append("--------------------- \n");
                    }
                    rowCounter++;
                    counter = 0;
                }
                default -> {
                    str.append(value);
                    str.append(" ");
                    counter++;
                }
            }
        }
        System.out.println(str);
    }
}
