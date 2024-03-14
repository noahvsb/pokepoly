package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.Dice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Ugentopoly extends Application {
    @Override
    public void start(Stage stage) {

        // spelbord
        ArrayList<Tile> leftTiles = new ArrayList<>();
        leftTiles.add(new Tile("tile 9", null, 130, 65));
        leftTiles.add(new Tile("tile 8", null, 130, 65));
        leftTiles.add(new Tile("tile 7", null, 130, 65));
        leftTiles.add(new Tile("tile 6", null, 130, 65));
        leftTiles.add(new Tile("tile 5", null, 130, 65));
        leftTiles.add(new Tile("tile 4", null, 130, 65));
        leftTiles.add(new Tile("tile 3", null, 130, 65));
        leftTiles.add(new Tile("tile 2", null, 130, 65));
        leftTiles.add(new Tile("tile 1", null, 130, 65));

        VBox left = new VBox();
        for (Tile t : leftTiles)
            left.getChildren().add(t.getButton());

        ArrayList<Tile> topTiles = new ArrayList<>();
        topTiles.add(new Tile("overpoort", "jail", 130, 130));
        topTiles.add(new Tile("tile 11", null, 65, 130));
        topTiles.add(new Tile("tile 12", null, 65, 130));
        topTiles.add(new Tile("tile 13", null, 65, 130));
        topTiles.add(new Tile("tile 14", null, 65, 130));
        topTiles.add(new Tile("tile 15", null, 65, 130));
        topTiles.add(new Tile("tile 16", null, 65, 130));
        topTiles.add(new Tile("tile 17", null, 65, 130));
        topTiles.add(new Tile("tile 18", null, 65, 130));
        topTiles.add(new Tile("tile 19", null, 65, 130));
        topTiles.add(new Tile("gratis\nparkeren", "free_parking", 130, 130));

        HBox top = new HBox();
        for (Tile t : topTiles)
            top.getChildren().add(t.getButton());

        ArrayList<Tile> rightTiles = new ArrayList<>();
        rightTiles.add(new Tile("tile 21", null, 130, 65));
        rightTiles.add(new Tile("tile 22", null, 130, 65));
        rightTiles.add(new Tile("tile 23", null, 130, 65));
        rightTiles.add(new Tile("tile 24", null, 130, 65));
        rightTiles.add(new Tile("tile 25", null, 130, 65));
        rightTiles.add(new Tile("tile 26", null, 130, 65));
        rightTiles.add(new Tile("tile 27", null, 130, 65));
        rightTiles.add(new Tile("tile 28", null, 130, 65));
        rightTiles.add(new Tile("tile 29", null, 130, 65));

        VBox right = new VBox();
        for (Tile t : rightTiles)
            right.getChildren().add(t.getButton());

        ArrayList<Tile> bottomTiles = new ArrayList<>();
        bottomTiles.add(new Tile("start", "start", 130, 130));
        bottomTiles.add(new Tile("tile 39", null, 65, 130));
        bottomTiles.add(new Tile("tile 38", null, 65, 130));
        bottomTiles.add(new Tile("tile 37", null, 65, 130));
        bottomTiles.add(new Tile("tile 36", null, 65, 130));
        bottomTiles.add(new Tile("tile 35", null, 65, 130));
        bottomTiles.add(new Tile("tile 34", null, 65, 130));
        bottomTiles.add(new Tile("tile 33", null, 65, 130));
        bottomTiles.add(new Tile("tile 32", null, 65, 130));
        bottomTiles.add(new Tile("tile 31", null, 65, 130));
        bottomTiles.add(new Tile("go to\noverpoort", "go_to_jail", 130, 130));

        HBox bottom = new HBox();
        for (Tile t : bottomTiles)
            bottom.getChildren().add(t.getButton());

        ImageView logo = new ImageView();
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/logo.png")).toExternalForm()));
        logo.setFitWidth(400);
        logo.setFitHeight(75);
        logo.setRotate(45);

        BorderPane board = new BorderPane();
        board.setBottom(bottom);
        board.setLeft(left);
        board.setTop(top);
        board.setRight(right);
        board.setCenter(logo);

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