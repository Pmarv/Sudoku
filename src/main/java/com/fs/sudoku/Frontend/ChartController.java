package com.fs.sudoku.Frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
    @Value("classpath:/Singleplayer.fxml")
    private Resource Test;
    public static Scene scene;
    @FXML
    private void initialize() {
        Singleplayer.setOnAction(this::handleButtonAction);
    }

    public Resource getTest() {
        return this.Test;
    }
    private void handleButtonAction(ActionEvent event) {
        try {

            System.out.println(Test.getURL());
            FXMLLoader loader = new FXMLLoader(Test.getURL());
            Parent root = loader.load();


            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            SingleplayerController controller = loader.getController();
            controller.setScene(scene);
            controller.init();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
