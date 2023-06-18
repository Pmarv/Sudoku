package com.fs.sudoku.Frontend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import com.fs.sudoku.Backend.Multiplayer.IncomingUdpThread;
import com.fs.sudoku.Backend.Multiplayer.OutgoingUdpThread;
import com.fs.sudoku.Backend.RandomPuzzleGenerator;
import com.fs.sudoku.Backend.SudokuGrid;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.javatuples.Pair;

import java.util.concurrent.TimeUnit;

public class MultiplayerController {
    @FXML
    public Label modeDescriptor;
    private RandomPuzzleGenerator randomPuzzleGenerator = new RandomPuzzleGenerator();
    private int lastNumberButton = 1;
    @FXML
    private Button Sudoku_Button_00;
    @FXML
    private Button Sudoku_Button_01;
    @FXML
    private Button Sudoku_Button_02;
    @FXML
    private Button Sudoku_Button_03;
    @FXML
    private Button Sudoku_Button_04;
    @FXML
    private Button Sudoku_Button_05;
    @FXML
    private Button Sudoku_Button_06;
    @FXML
    private Button Sudoku_Button_07;
    @FXML
    private Button Sudoku_Button_08;

    @FXML
    private Button Sudoku_Button_10;
    @FXML
    private Button Sudoku_Button_11;
    @FXML
    private Button Sudoku_Button_12;
    @FXML
    private Button Sudoku_Button_13;
    @FXML
    private Button Sudoku_Button_14;
    @FXML
    private Button Sudoku_Button_15;
    @FXML
    private Button Sudoku_Button_16;
    @FXML
    private Button Sudoku_Button_17;
    @FXML
    private Button Sudoku_Button_18;

    @FXML
    private Button Sudoku_Button_20;
    @FXML
    private Button Sudoku_Button_21;
    @FXML
    private Button Sudoku_Button_22;
    @FXML
    private Button Sudoku_Button_23;
    @FXML
    private Button Sudoku_Button_24;
    @FXML
    private Button Sudoku_Button_25;
    @FXML
    private Button Sudoku_Button_26;
    @FXML
    private Button Sudoku_Button_27;
    @FXML
    private Button Sudoku_Button_28;

    @FXML
    private Button Sudoku_Button_30;
    @FXML
    private Button Sudoku_Button_31;
    @FXML
    private Button Sudoku_Button_32;
    @FXML
    private Button Sudoku_Button_33;
    @FXML
    private Button Sudoku_Button_34;
    @FXML
    private Button Sudoku_Button_35;
    @FXML
    private Button Sudoku_Button_36;
    @FXML
    private Button Sudoku_Button_37;
    @FXML
    private Button Sudoku_Button_38;

    @FXML
    private Button Sudoku_Button_40;
    @FXML
    private Button Sudoku_Button_41;
    @FXML
    private Button Sudoku_Button_42;
    @FXML
    private Button Sudoku_Button_43;
    @FXML
    private Button Sudoku_Button_44;
    @FXML
    private Button Sudoku_Button_45;
    @FXML
    private Button Sudoku_Button_46;
    @FXML
    private Button Sudoku_Button_47;
    @FXML
    private Button Sudoku_Button_48;

    @FXML
    private Button Sudoku_Button_50;
    @FXML
    private Button Sudoku_Button_51;
    @FXML
    private Button Sudoku_Button_52;
    @FXML
    private Button Sudoku_Button_53;
    @FXML
    private Button Sudoku_Button_54;
    @FXML
    private Button Sudoku_Button_55;
    @FXML
    private Button Sudoku_Button_56;
    @FXML
    private Button Sudoku_Button_57;
    @FXML
    private Button Sudoku_Button_58;

    @FXML
    private Button Sudoku_Button_60;
    @FXML
    private Button Sudoku_Button_61;
    @FXML
    private Button Sudoku_Button_62;
    @FXML
    private Button Sudoku_Button_63;
    @FXML
    private Button Sudoku_Button_64;
    @FXML
    private Button Sudoku_Button_65;
    @FXML
    private Button Sudoku_Button_66;
    @FXML
    private Button Sudoku_Button_67;
    @FXML
    private Button Sudoku_Button_68;

    @FXML
    private Button Sudoku_Button_70;
    @FXML
    private Button Sudoku_Button_71;
    @FXML
    private Button Sudoku_Button_72;
    @FXML
    private Button Sudoku_Button_73;
    @FXML
    private Button Sudoku_Button_74;
    @FXML
    private Button Sudoku_Button_75;
    @FXML
    private Button Sudoku_Button_76;
    @FXML
    private Button Sudoku_Button_77;
    @FXML
    private Button Sudoku_Button_78;

    @FXML
    private Button Sudoku_Button_80;
    @FXML
    private Button Sudoku_Button_81;
    @FXML
    private Button Sudoku_Button_82;
    @FXML
    private Button Sudoku_Button_83;
    @FXML
    private Button Sudoku_Button_84;
    @FXML
    private Button Sudoku_Button_85;
    @FXML
    private Button Sudoku_Button_86;
    @FXML
    private Button Sudoku_Button_87;
    @FXML
    private Button Sudoku_Button_88;

    @FXML
    private Button Number_Button_1;
    @FXML
    private Button Number_Button_2;

    @FXML
    private Button Number_Button_3;

    @FXML
    private Button Number_Button_4;

    @FXML
    private Button Number_Button_5;

    @FXML
    private Button Number_Button_6;

    @FXML
    private Button Number_Button_7;

    @FXML
    private Button Number_Button_8;
    @FXML
    private Button Number_Button_9;

    private boolean[][] initialValues = new boolean[9][9];

    @FXML
    private Button Menu;
    private Scene scene;
    private String mode;
    private Scene previousScene;
    private Client client;
    private int value;
    @FXML
    public void init() {
        Client.multiplayerGrid.printGrid();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String buttonId = "Sudoku_Button_" +(int) i + (int)j;
                Button button = (Button) scene.lookup("#" + buttonId);
                if(Client.hasGeneratedPuzzle) {
                     value = Client.multiplayerGrid.getValue(new Pair<>(i, j));
                } else {
                 value = Client.multiplayerGrid.getValueLong(new Pair<>((long)i,(long) j));
                }
                if (value != 0) {
                    initialValues[i][j] = true;
                }
                button.setText(String.valueOf(value));
                button.setOnAction(this::handleSudokuButton);
            }
        }
        if(mode.equals("Co-op")){
            modeDescriptor.setText("Co-op");
        }

        Number_Button_1.setOnAction(this::handleNumberButtonAction1);
        Number_Button_2.setOnAction(this::handleNumberButtonAction2);
        Number_Button_3.setOnAction(this::handleNumberButtonAction3);
        Number_Button_4.setOnAction(this::handleNumberButtonAction4);
        Number_Button_5.setOnAction(this::handleNumberButtonAction5);
        Number_Button_6.setOnAction(this::handleNumberButtonAction6);
        Number_Button_7.setOnAction(this::handleNumberButtonAction7);
        Number_Button_8.setOnAction(this::handleNumberButtonAction8);
        Number_Button_9.setOnAction(this::handleNumberButtonAction9);
        Menu.setOnAction(this::handleMenuButton);
        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setTitle("Please Wait...");
        alert.setHeaderText("Waiting for other player to make a move");
        alert.getButtonTypes().clear();
        if(mode.equals("Co-op")) {
            Thread t = new Thread(() -> {
                while (Client.isConnected) {
                    if (Client.lastPlayer) {
                        Platform.runLater(alert::show);
                    } else {
                        updateGrid();
                        alert.getButtonTypes().add(ButtonType.CANCEL);
                        alert.close();
                        alert.getButtonTypes().clear();
                    }
                }
            });
            t.start();
        }
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    private void handleNumberButtonAction1(ActionEvent actionEvent) {
        lastNumberButton = 1;
    }
    private void handleNumberButtonAction2(ActionEvent actionEvent) {
        lastNumberButton = 2;
    }
    private void handleNumberButtonAction3(ActionEvent actionEvent) {
        lastNumberButton = 3;
    }
    private void handleNumberButtonAction4(ActionEvent actionEvent) {
        lastNumberButton = 4;
    }
    private void handleNumberButtonAction5(ActionEvent actionEvent) {
        lastNumberButton = 5;
    }
    private void handleNumberButtonAction6(ActionEvent actionEvent) {
        lastNumberButton = 6;
    }
    private void handleNumberButtonAction7(ActionEvent actionEvent) {
        lastNumberButton = 7;
    }
    private void handleNumberButtonAction8(ActionEvent actionEvent) {
        lastNumberButton = 8;
    }
    private void handleNumberButtonAction9(ActionEvent actionEvent) {
        lastNumberButton = 9;
    }
    private void handleMenuButton(ActionEvent actionEvent) {
        Client.multiplayerGridSet = false;
        Client.hasGeneratedPuzzle = false;
        Client.isConnected = false;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    private void handleGridButtonAction(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String buttonId = button.getId();
        int i = Character.getNumericValue(buttonId.charAt(14));
        int j = Character.getNumericValue(buttonId.charAt(15));
        if (initialValues[i][j]) {
            return;
        }
        if (lastNumberButton == 0) {
            return;
        }
        button.setText(String.valueOf(lastNumberButton));
        Client.multiplayerGrid.setValue(new Pair<>(i, j), lastNumberButton);
        if (Client.multiplayerGrid.isComplete()) {
            if(mode.equals("VS")){
                client.stopVs();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sudoku");
                if(Client.vsWinOrLose || Client.OpponentTime == 0) {
                    alert.setHeaderText("Congratulations!");
                    alert.setContentText("You won the game! \n Your time was: " + TimeUnit.MINUTES.convert(Client.timeTaken, TimeUnit.NANOSECONDS) + " minutes");
                    alert.showAndWait();
                } else {
                    alert.setHeaderText("Sorry!");
                    alert.setContentText("You lost the game! \n Your time was: " + TimeUnit.MINUTES.convert(Client.timeTaken, TimeUnit.NANOSECONDS) + " minutes");
                    alert.showAndWait();
                }
            } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sudoku");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("You solved the puzzle!");
            alert.showAndWait();
            }
        }
    }
    private void handleSudokuButton(ActionEvent event) {
        if (mode.equals("VS")) {
            handleGridButtonAction(event);
        } else if (mode.equals("Co-op")){
            handleGridButtonAction(event);
            client.sendSudoku();
            Client.lastPlayer = true;
        }
    }
    private void updateGrid() {
        Platform.runLater(() -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String buttonId = "Sudoku_Button_" + (int) i + (int) j;
                    Button button = (Button) scene.lookup("#" + buttonId);
                    if(Client.hasGeneratedPuzzle) {
                        value = Client.multiplayerGrid.getValue(new Pair<>(i, j));
                    } else {
                        value = Client.multiplayerGrid.getValueLong(new Pair<>((long)i,(long) j));
                    }
                    button.setText(String.valueOf(value));
                }
            }
        });
    }

}
