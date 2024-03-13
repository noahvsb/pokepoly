package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.Dice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage spelbordStage) throws Exception {

        // spelbord
        ImageView chance = new ImageView();
        chance.setImage(new Image(getClass().getResource("assets/chance.png").toExternalForm()));
        chance.setFitHeight(50);
        chance.setFitWidth(50);

        ImageView chest = new ImageView();
        chest.setImage(new Image(getClass().getResource("assets/chest.png").toExternalForm()));
        chest.setFitHeight(50);
        chest.setFitWidth(50);

        ImageView freeParking = new ImageView();
        freeParking.setImage(new Image(getClass().getResource("assets/free_parking.png").toExternalForm()));
        freeParking.setFitHeight(50);
        freeParking.setFitWidth(50);

        ImageView goToJail = new ImageView();
        goToJail.setImage(new Image(getClass().getResource("assets/go_to_jail.png").toExternalForm()));
        goToJail.setFitHeight(50);
        goToJail.setFitWidth(50);

        Node[] bottomNode = new Node[2];

        Button chanceTile = new Button("chance", chance);
        chanceTile.setFont(new Font(20));
        bottomNode[0] = chanceTile;

        Button chestTile = new Button("chest", chest);
        chestTile.setFont(new Font(20));
        bottomNode[1] = chestTile;

        HBox bottomTiles = new HBox(bottomNode);

        Node[] leftNode = new Node[2];

        Button freeParkingTile = new Button("free parking", freeParking);
        freeParkingTile.setFont(new Font(20));
        leftNode[0] = freeParkingTile;

        Button goToJailTile = new Button("go to jail", goToJail);
        goToJailTile.setFont(new Font(20));
        leftNode[1] = goToJailTile;

        HBox leftTiles = new HBox(leftNode);

        BorderPane mainPane = new BorderPane();
        mainPane.setBottom(bottomTiles);
        mainPane.setLeft(leftTiles);

        Scene spelbordScene = new Scene(mainPane, 845, 845);

        spelbordStage.setTitle("Ugentopoly");
        spelbordStage.setScene(spelbordScene);
        spelbordStage.show();


        //dobbelstenen
        Stage dobbelstenenStage = new Stage();
        final Dice dice = new Dice();

        Button btn = new Button();
        btn.setText("ROL");
        btn.setFont(new Font(40));
        btn.setOnAction(event -> dice.roll(t -> System.out.println("Resultaat: ")));
        btn.setPrefSize(200, 100);

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene dobbelstenenScene = new Scene(root, 250, 150);

        dobbelstenenStage.setTitle("Dobbelstenen");
        dobbelstenenStage.setOnCloseRequest(e -> dice.close());
        dobbelstenenStage.setScene(dobbelstenenScene);
        dobbelstenenStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}