package com.fs.sudoku;

import com.fs.sudoku.Frontend.UIApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SudokuApplication {

    public static void main(String[] args) {
//        Application.launch(UIApplication.class, args);
        SpringApplication.run(SudokuApplication.class);
    }

}

