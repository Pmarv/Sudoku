package com.fs.sudoku.Frontend;

import com.fs.sudoku.Frontend.UIApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/chart.fxml")
    private Resource chartResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    protected StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationTitle = applicationTitle;
    }

    /**
     * This method is called when the application is ready to start and opens the first screen
     * @param event StageReadyEvent
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {

        FXMLLoader fxmlLoader = new FXMLLoader(chartResource.getURL());
        fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
        Parent parent = fxmlLoader.load();
        Stage stage = event.getStage();
        stage.setScene(new Scene(parent));
        stage.setTitle(applicationTitle);
        stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
