package be.ugent.objprog.pokepoly;

import be.ugent.objprog.pokepoly.rightdisplay.RightDisplay;
import be.ugent.objprog.pokepoly.startspel.StartSpel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import org.jdom2.JDOMException;

import java.io.IOException;

public class Pokepoly extends Application {
    @Override
    public void start(Stage mainStage) throws IOException, JDOMException {
        StageController stageController = new StageController();

        // SCENES

        //main
        Bord bord = new Bord();
        RightDisplay rightDisplay = new RightDisplay(260, 845, bord);
        HBox main = new HBox(bord, rightDisplay);

        Scene mainScene = new Scene(main, 1105, 845);

        //spel starten
        StartSpel startSpel = new StartSpel(500, 325, bord, rightDisplay, stageController);
        Scene startSpelScene = new Scene(startSpel, 500, 325);

        // STAGES

        //main
        mainStage.setTitle("Poképoly");
        mainStage.setScene(mainScene);
        mainStage.resizableProperty().setValue(false);
        mainStage.show();

        //spel starten
        Stage startSpelStage = new Stage();
        startSpelStage.setTitle("Start Game");
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