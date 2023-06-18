package com.fs.sudoku.Frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;

import static com.fs.sudoku.Frontend.ChartController.scene;

@Component
public class DifficultyController {
    @FXML
    public Button EasyButton;
    @FXML
    public Button MediumButton;
    @FXML
    public Button BackButton;
    private Resource Singlepayer;
    @FXML
    public Button HardButton;
    private Scene previousScene;
    @FXML
    private void initialize() {
        EasyButton.setOnAction(this::handleEasyAction);
        MediumButton.setOnAction(this::handleMediumAction);
        HardButton.setOnAction(this::handleHardAction);
        BackButton.setOnAction(this::handleBackAction);
    }
    protected void setSingleplayer(Resource Singleplayer) {
        this.Singlepayer = Singleplayer;
    }
    protected void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }
    private void handleEasyAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Singlepayer.getURL());
            Parent root = loader.load();
            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            SingleplayerController controller = loader.getController();
            controller.setScene(scene);
            controller.setMode("Debugging");
            controller.setPreviousScene(EasyButton.getScene());
            controller.init();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleMediumAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Singlepayer.getURL());
            Parent root = loader.load();
            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            SingleplayerController controller = loader.getController();
            controller.setScene(scene);
            controller.setMode("Medium");
            controller.setPreviousScene(EasyButton.getScene());
            controller.init();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleHardAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Singlepayer.getURL());
            Parent root = loader.load();
            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            SingleplayerController controller = loader.getController();
            controller.setScene(scene);
            controller.setMode("Hard");
            controller.setPreviousScene(EasyButton.getScene());
            controller.init();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleBackAction(ActionEvent event) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
    }
}
