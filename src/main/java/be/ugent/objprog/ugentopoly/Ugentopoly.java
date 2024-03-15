package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.Dice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jdom2.JDOMException;

import java.io.IOException;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage stage) throws IOException, JDOMException {

        // spelbord
        Bord bord = new Bord();
        Scene spelbordScene = new Scene(bord, 845, 845);


        //dobbelstenen
        final Dice dice = new Dice();

        Button btn = new Button();
        btn.setText("ROL");
        btn.setFont(new Font(40));
        btn.setOnAction(event -> dice.roll(t -> System.out.println("Resultaat: " + dice.getLastRoll())));
        btn.setPrefSize(200, 100);

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene dobbelstenenScene = new Scene(root, 250, 150);

        //stage
        stage.setTitle("Dobbelstenen");
        stage.setOnCloseRequest(e -> dice.close());
        stage.setScene(dobbelstenenScene);
        stage.show();

        Stage spelbord = new Stage();
        spelbord.setTitle("Ugentopoly");
        spelbord.setScene(spelbordScene);
        spelbord.show();
    }

    public static void main(String[] args) {
        launch();
    }
}