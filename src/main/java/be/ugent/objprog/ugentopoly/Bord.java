package be.ugent.objprog.ugentopoly;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Bord extends BorderPane {

    public Bord() throws IOException {
        // reading xml-file using JDOM


        // tiles
        ArrayList<Tile> leftTiles = new ArrayList<>();
        for (int i = 9; i > 0; i--)
            leftTiles.add(new Tile("tile.street0" + i, null, "red", 130, 65, true));

        VBox left = new VBox();
        for (Tile t : leftTiles) {
            HBox hb = t.getHBox();
            left.getChildren().add(hb);
        }

        ArrayList<Tile> topTiles = new ArrayList<>();
        topTiles.add(new Tile("tile.jail", "jail", null, 130, 130, false));
        for (int i = 1; i <= 9; i++)
            topTiles.add(new Tile("tile.street" + (i + 10), null, "blue", 65, 130, false));
        topTiles.add(new Tile("tile.freeparking", "free_parking", null, 130, 130, false));

        HBox top = new HBox();
        for (Tile t : topTiles) {
            VBox vb = t.getVBox();
            top.getChildren().add(vb);
        }

        ArrayList<Tile> rightTiles = new ArrayList<>();
        for (int i = 1; i <= 9; i++)
            rightTiles.add(new Tile("tile.street" + (i + 20), null, "green", 130, 65, true));

        VBox right = new VBox();
        for (Tile t : rightTiles) {
            HBox hb = t.getHBox();
            hb.setRotate(180);
            hb.getChildren().getFirst().setRotate(180);
            right.getChildren().add(hb);
        }

        ArrayList<Tile> bottomTiles = new ArrayList<>();
        bottomTiles.add(new Tile("tile.start", "start", null, 130, 130, false));
        for (int i = 9; i > 0; i--)
            bottomTiles.add(new Tile("tile.street" + (i + 30), null, "yellow", 65, 130, false));
        bottomTiles.add(new Tile("tile.gotojail", "go_to_jail", null, 130, 130, false));

        HBox bottom = new HBox();
        for (Tile t : bottomTiles) {
            VBox vb = t.getVBox();
            if (vb.getPrefWidth() != 130) {
                vb.setRotate(180);
                vb.getChildren().getFirst().setRotate(180);
            }
            bottom.getChildren().add(vb);
        }

        // logo
        ImageView logoImage = new ImageView();
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/logo.png")).toExternalForm()));
        logoImage.setFitWidth(400);
        logoImage.setFitHeight(75);
        logoImage.setRotate(45);
        HBox logo = new HBox(logoImage);
        logo.setPrefSize(585, 585);
        logo.setStyle("-fx-background-color: lightgreen; -fx-border-color: black; -fx-border-width: 1.5");
        logo.setAlignment(Pos.CENTER);

        // main
        setBottom(bottom);
        setLeft(left);
        setTop(top);
        setRight(right);
        setCenter(logo);
    }
}
