package com.fs.sudoku.Backend;

import com.google.common.base.Splitter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Getter @Setter
@Component
@NoArgsConstructor
public class Exact_Cover_solver {

    List<Node> currentSolution = new ArrayList<>();
    List<Node> currentSolutionCopy;
    private  SudokuGrid solvedGrid = new SudokuGrid();
    private  SudokuGrid partialSolvedGrid = new SudokuGrid();

    Root root;

    int solutions = 0;



//    Implementation of Knuth's Algorithm X with Dancing Links
//    paper and pseudocode at https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf
    private void search(int k,boolean partial) {
        if(root.right == root) {
            if(partial){
            partialSolvedGrid.setSudokuGrid(nodeToPartialSolution(currentSolution));
            } else {
            solvedGrid.setSudokuGrid(nodeToSolution(currentSolution));
            }
//            partialSolvedGrid.printGrid();
//             solvedGrid.printGrid();
             solutions++;
        } else if (solutions < 2) {
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
                search(k + 1,partial);
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
    public void solve(int[][] matrix,boolean partial) {
        setUpProblem(matrix);
        search(0,partial);
        root = null;
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
            if (lastRowNodeTouched != null) {
            lastRowNodeTouched.right = firstRowNode;
            firstRowNode.left = lastRowNodeTouched;
            }
            lastColumn = root.right;
            lastNodeTouched = lastColumn;
            countRowNodes = 0;
        }
//        System.out.println();
    }

    public int[][] sudokuToCover(
            Map<Pair<Integer,Integer>,Integer> grid
    ){
        String test;
        int count = 0;
        int count2 = 0;
        int[][] problem = new int[729][324];
        File matrix = new File("9x9 cover matrix.txt");
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(new FileInputStream(matrix)));
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
        r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                Pair<Integer,Integer> key = new Pair<>(row-1,column-1);
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
    public Map<Pair<Integer,Integer>,Integer> nodeToPartialSolution(List<Node> currentSolution) {
        currentSolutionCopy = new ArrayList<>(currentSolution);
        Map<Pair<Integer,Integer>,Integer> result;
        Set<Node> partialSolutionSet = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            int randomIndex = (int) (Math.random()*100);
            while ( randomIndex >= currentSolution.size() || !partialSolutionSet.add(currentSolution.get(randomIndex))) {
                randomIndex = (int) (Math.random()*100);
            }
        }
        List<Node> partialSolution = new ArrayList<>(partialSolutionSet);
        result = nodeToSolution(partialSolution);
        return result;
    }

}
