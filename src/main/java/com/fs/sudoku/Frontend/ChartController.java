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
    public Button MultiplayerButton;
    @FXML
    private Button Singleplayer;
    @Value("classpath:/ChooseDifficulty.fxml")
    private Resource DifficultyStage;
    @Value("classpath:/Singleplayer.fxml")
    private Resource SinglepayerScene;
    @Value("classpath:/MultiplayerCode.fxml")
    private Resource MultiplayerCode;
    public static Scene scene;
    @FXML
    private void initialize() {
        Singleplayer.setOnAction(this::handleButtonAction);
        MultiplayerButton.setOnAction(this::handleMultiplayerAction);
    }

    public Resource getDifficultyStage() {
        return this.DifficultyStage;
    }
    private void handleButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(DifficultyStage.getURL());
            Parent root = loader.load();
            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            DifficultyController controller = loader.getController();
            controller.setSingleplayer(SinglepayerScene);
            controller.setPreviousScene(Singleplayer.getScene());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleMultiplayerAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MultiplayerCode.getURL());
            Parent root = loader.load();
            scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            MultiplayerCodeController controller = loader.getController();
            controller.setPreviousScene(Singleplayer.getScene());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
