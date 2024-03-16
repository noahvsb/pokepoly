package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.ChanceTile;
import be.ugent.objprog.ugentopoly.tiles.ChestTile;
import be.ugent.objprog.ugentopoly.tiles.StreetTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Bord extends BorderPane {

    public Bord() throws IOException, JDOMException {
        // reading xml-file using JDOM
        Document doc = new SAXBuilder().build(getClass().getResourceAsStream("ugentopoly.deel1.xml"));
        Element root = doc.getRootElement();

        Element rootSettings = root.getChild("settings");
        Element rootAreas = root.getChild("areas");
        Element rootTiles = root.getChild("tiles");

        // tiles
        ArrayList<Tile> tiles = new ArrayList<>();

        int util = 1;

        for (int i = 0; i < 40; i++) {
            Element tile = rootTiles.getChildren().get(i);
            String type = tile.getAttributeValue("type");
            String id = tile.getAttributeValue("id");

            // Street
            if (type.equals("STREET")) {
                String areaId = tile.getAttributeValue("area");
                Element area = null;
                for (int x = 0; x < 8; x++) {
                    area = rootAreas.getChildren().get(x);
                    if (areaId.equals(area.getAttributeValue("id")))
                        break;
                }

                String colour = area.getAttributeValue("color");
                int houseCost = Integer.parseInt(area.getAttributeValue("house"));
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                tiles.add(new StreetTile(id, colour, 130, 65, cost, houseCost,
                        Integer.parseInt(tile.getAttributeValue("rent0")),
                        Integer.parseInt(tile.getAttributeValue("rent1")),
                        Integer.parseInt(tile.getAttributeValue("rent2")),
                        Integer.parseInt(tile.getAttributeValue("rent3")),
                        Integer.parseInt(tile.getAttributeValue("rent4")),
                        Integer.parseInt(tile.getAttributeValue("rent5"))));
            }

            // Chest
            if (type.equals("CHEST")) {
                tiles.add(new ChestTile(id, 130, 65));
            }

            // Chance
            if (type.equals("CHANCE")) {
                tiles.add(new ChanceTile(id, 130, 65));
            }

            // Tax
            if (type.equals("TAX")) {
                int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                tiles.add(new Tile(id, "tax", 130, 65, amount));
            }

            // Railway
            if (type.equals("RAILWAY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                tiles.add(new Tile(id, "railway", 130, 65, cost));
            }

            // Utility
            if (type.equals("UTILITY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                tiles.add(new Tile(id, "utility" + util, 130, 65, cost));
                util++;
            }

            // Go to jail
            if (type.equals("GO_TO_JAIL")) {
                tiles.add(new Tile(id, "go_to_jail", 130, 130));
            }

            // Start
            if (type.equals("START")) {
                tiles.add(new Tile(id, "start", 130, 130));
            }

            // Jail
            if (type.equals("JAIL")) {
                tiles.add(new Tile(id, "jail", 130, 130));
            }

            // Free parking
            if (type.equals("FREE_PARKING")) {
                tiles.add(new Tile(id, "free_parking", 130, 130));
            }
        }

        // adding tiles to left, top, right, bottom
        VBox left = new VBox();
        HBox top = new HBox();
        VBox right = new VBox();
        right.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        HBox bottom = new HBox();

        for (int i = 1; i < tiles.size(); i++) {
            Tile t = tiles.get(i);

            // left
            if (i < 10) {
                left.getChildren().addFirst(t.getHbox());
            }

            // top
            else if (i < 21) {
                VBox v = t.getVBox();
                if (v.getPrefWidth() == 130) {
                    v.setSpacing(40);
                }
                top.getChildren().add(v);
            }

            // right
            else if (i < 30) {
                right.getChildren().add(t.getHbox());
            }

            // bottom
            else {
                VBox v = t.getVBox();
                if (v.getPrefWidth() != 130) {
                    v.setRotate(180);
                    v.getChildren().getFirst().setRotate(180);
                    v.getChildren().getLast().setRotate(180);
                } else {
                    v.setSpacing(20);
                }
                bottom.getChildren().addFirst(v);
            }
        }

        // adding starttile to bottom
        VBox start = tiles.getFirst().getVBox();
        start.setSpacing(40);
        bottom.getChildren().addFirst(start);


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

        // set all parts
        setBottom(bottom);
        setLeft(left);
        setTop(top);
        setRight(right);
        setCenter(logo);
    }
}
