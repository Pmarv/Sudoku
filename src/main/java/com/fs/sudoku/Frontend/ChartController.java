package com.fs.sudoku.Frontend;

import org.springframework.stereotype.Component;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;


@Component
public class ChartController {
    @FXML
    private Button Singleplayer;

        @FXML
        private void initialize() {
            Singleplayer.setOnAction(this::handleButtonAction);
        }
    private void handleButtonAction(ActionEvent event) {
        try {
            // Load the FXML file for the new scene
            System.out.println(getClass().getResource("../../../Singeplayer.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("../../../Singeplayer.fxml"));

            // Create the new scene and set it on the stage
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle the exception, e.g., show an error dialog
            e.printStackTrace();
        }
    }
}
