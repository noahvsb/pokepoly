package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.Dice;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage stage) {

        // spelbord

        Tile[] bottomTiles = new Tile[2];
        bottomTiles[0] = new Tile("start", "start-arrow", 100, 100);
        bottomTiles[1] = new Tile("test1", null, 50, 100);

        HBox bottom = new HBox();
        for (Tile tile : bottomTiles)
            bottom.getChildren().add(tile.getButton());


        Tile[] leftTiles = new Tile[2];
        leftTiles[0] = new Tile("overpoort", "go_to_jail", 100, 100);
        leftTiles[1] = new Tile("test2", null, 50, 100);

        VBox left = new VBox();
        for (Tile tile : leftTiles)
            left.getChildren().add(tile.getButton());

        BorderPane board = new BorderPane();
        board.setBottom(bottom);
        board.setLeft(left);

        Scene spelbordScene = new Scene(board, 845, 845);


        //dobbelstenen
        final Dice dice = new Dice();

        Button btn = new Button();
        btn.setText("ROL");
        btn.setFont(new Font(40));
        btn.setOnAction(event -> dice.roll(t -> System.out.println("Resultaat: ")));
        btn.setPrefSize(200, 100);

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene dobbelstenenScene = new Scene(root, 250, 150);

        //stage
        stage.setTitle("UGentopoly");
        stage.setOnCloseRequest(e -> dice.close());
        stage.setScene(dobbelstenenScene);
        stage.show();

        Stage spelbord = new Stage();
        spelbord.setScene(spelbordScene);
        spelbord.show();
    }

    public static void main(String[] args) {
        launch();
    }
}