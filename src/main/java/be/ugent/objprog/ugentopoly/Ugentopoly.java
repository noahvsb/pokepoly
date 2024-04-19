package be.ugent.objprog.ugentopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import org.jdom2.JDOMException;

import java.io.IOException;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage mainStage) throws IOException, JDOMException {
        StageController stageController = new StageController();

        // SCENES

        //main
        Bord bord = new Bord();
        GeneralInfoAndRollHandler generalInfoAndRollHandler = new GeneralInfoAndRollHandler(260, 845, bord);
        HBox main = new HBox(bord, generalInfoAndRollHandler);

        Scene mainScene = new Scene(main, 1105, 845);

        //spel starten
        StartSpel startSpel = new StartSpel(500, 325, bord, generalInfoAndRollHandler, stageController);
        Scene startSpelScene = new Scene(startSpel, 500, 325);

        // STAGES

        //main
        mainStage.setTitle("Ugentopoly");
        mainStage.setScene(mainScene);
        mainStage.resizableProperty().setValue(false);
        mainStage.show();

        //spel starten
        Stage startSpelStage = new Stage();
        startSpelStage.setTitle("Start spel");
        startSpelStage.setScene(startSpelScene);
        startSpelStage.initStyle(StageStyle.UNDECORATED);
        startSpelStage.show();

        mainStage.setOnCloseRequest(e -> startSpelStage.close());
        stageController.addStages(mainStage, startSpelStage);
    }

    public static void main(String[] args) {
        launch();
    }
}