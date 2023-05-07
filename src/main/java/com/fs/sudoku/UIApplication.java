package com.fs.sudoku;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class UIApplication extends javafx.application.Application {
    private ConfigurableApplicationContext applicationContext;
    @Override
    public void start(Stage stage)  {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(SudokuApplication.class).run();
    }

    @Override
    public void stop() {
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
