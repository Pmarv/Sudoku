package com.fs.sudoku.Frontend;

import com.fs.sudoku.Backend.Multiplayer.Client;
import com.fs.sudoku.SudokuApplication;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class UIApplication extends javafx.application.Application {
    private ConfigurableApplicationContext applicationContext;

    /**
     * Sets up the stage together with the application context
     * @param stage Stage
     */
    @Override
    public void start(Stage stage)  {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    /**
     * Initializes the application context
     */
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(SudokuApplication.class).run();
    }

    /**
     * Closes the application context and cleans up the connection
     */
    @Override
    public void stop() {
        Client.isConnected  = false;
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
