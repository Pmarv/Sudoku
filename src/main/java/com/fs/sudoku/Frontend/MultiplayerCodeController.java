package com.fs.sudoku.Frontend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;

public class MultiplayerCodeController {

    @FXML
    public TextField CodeField;

    @FXML
    public Button SubmitButton;
    public Button BackButton;

    private Scene previousScene;
    private final Client client = new Client();
    @FXML
    private void initialize() {
        SubmitButton.setOnAction(this::handleSubmitButtonAction);
        BackButton.setOnAction(this::handleBackAction);
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }
    private void handleBackAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
    }

    private void handleSubmitButtonAction(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Please Wait");
        alert.setHeaderText(null);
        alert.setContentText("Connecting to another player. Please Standby");
        alert.getButtonTypes().remove(0);
        alert.show();
        Thread t = new Thread(() -> {
            try {
                client.connectToOtherClient(CodeField.getText());
//                System.out.println(CodeField.getText());
            Platform.runLater(() -> {
                alert.getButtonTypes().add(ButtonType.CANCEL);
                alert.close();
                alert.getButtonTypes().remove(ButtonType.CANCEL);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MultiplayerMode.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MultiplayerModeController controller = loader.getController();
                controller.setClient(client);
                controller.setPreviousScene(previousScene);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                    });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.close();
                    alert.getButtonTypes().remove(ButtonType.CANCEL);
                });
                throw new RuntimeException(e);
            }
        });
        t.start();

    }
}
