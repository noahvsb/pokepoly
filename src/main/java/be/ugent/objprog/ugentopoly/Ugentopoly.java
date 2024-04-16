package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.DicePanel;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.jdom2.JDOMException;

import java.io.IOException;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage mainStage) throws IOException, JDOMException {

        // SCENES

        //main
        Bord bord = new Bord();

        //logs
        //dobbelstenen
        final DicePanel dicePanel = new DicePanel();

        Button btn = new Button();
        btn.setText("ROL");
        btn.setFont(new Font(15));
        btn.setOnAction(event -> dicePanel.roll(result -> System.out.println("Resultaat: " + result)));
        btn.setPrefSize(100, 50);

        VBox dice = new VBox();
        dice.getChildren().addAll(btn, dicePanel);
        dice.setAlignment(Pos.CENTER);

        VBox logs = new Logs(dice);

        HBox main = new HBox(bord, logs);

        Scene mainScene = new Scene(main, 1105, 845);

        //spel starten
        StartSpel startSpel = new StartSpel(500, 325);
        Scene startSpelScene = new Scene(startSpel, 500, 325);

        // STAGES

        //main
        mainStage.setTitle("Ugentopoly");
        mainStage.setScene(mainScene);
        mainStage.show();

        //spel starten
        Stage startSpelStage = new Stage();
        startSpelStage.setTitle("Start spel");
        startSpelStage.setScene(startSpelScene);
        startSpelStage.show();

        mainStage.setOnCloseRequest(e -> startSpelStage.close());

    }

    public static void main(String[] args) {
        launch();
    }
}