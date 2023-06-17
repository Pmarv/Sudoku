package com.fs.sudoku.Frontend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MultiplayerModeController {
    @FXML
    public Button VersusButton;
    @FXML
    public Button CoopButton;
    @FXML
    public Button BackButton;
    private Client client;

    private Scene previousScene;
    public void setClient(Client client) {
        this.client = client;
    }
    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    @FXML
    private void initialize() {
        //initialize buttons
        VersusButton.setOnAction(this::handleVersusAction);
        CoopButton.setOnAction(this::handleCoopAction);
        BackButton.setOnAction(this::handleBackAction);

    }

    private void handleBackAction(ActionEvent event) {
        //go back to previous scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();

    }

    private void handleVersusAction(ActionEvent event) {
        client.startMultiplayer("VS");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Multiplayer.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            MultiplayerController controller = loader.getController();
            controller.setClient(client);
            controller.setPreviousScene(previousScene);
            controller.setMode("VS");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void handleCoopAction(ActionEvent event) {
        client.startMultiplayer("Co-op");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Multiplayer.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            MultiplayerController controller = loader.getController();
            controller.setClient(client);
            controller.setPreviousScene(previousScene);
            controller.setMode("Co-op");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
