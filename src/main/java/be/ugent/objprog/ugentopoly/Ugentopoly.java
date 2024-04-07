package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.Dice;

import be.ugent.objprog.ugentopoly.tiles.Stripe;
import javafx.application.Application;
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

        VBox logs = new VBox();
        logs.setPrefSize(260, 845);
        logs.setMinSize(260, 845);
        logs.setMaxSize(260, 845);
        logs.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray");

        HBox main = new HBox(bord, logs);

        Scene mainScene = new Scene(main, 1105, 845);


        //dobbelstenen
        final Dice dice = new Dice();

        Button btn = new Button();
        btn.setText("ROL");
        btn.setFont(new Font(40));
        btn.setOnAction(event -> dice.roll(t -> System.out.println("Resultaat: " + dice.getLastRoll())));
        btn.setPrefSize(200, 100);

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene rolScene = new Scene(root, 250, 150);

        //spel starten
        StartSpel startSpel = new StartSpel(500, 325);
        Scene startSpelScene = new Scene(startSpel, 500, 325);

        // STAGES

        //main
        mainStage.setTitle("Ugentopoly");
        mainStage.setScene(mainScene);
        mainStage.show();

        //dobbelstenen
        /*
            Stage rolStage = new Stage();
            rolStage.setTitle("Dobbelstenen");
            rolStage.setOnCloseRequest(e -> dice.close());
            rolStage.setScene(rolScene);
            rolStage.show();

            mainStage.setOnCloseRequest(e -> rolStage.close());
         */


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