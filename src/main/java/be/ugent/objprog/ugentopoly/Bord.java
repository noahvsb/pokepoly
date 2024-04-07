package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.*;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.Objects;

public class Bord extends BorderPane {

    private Tile[] tiles;
    private VBox leftTiles;
    private VBox rightTiles;
    private HBox topTiles;
    private HBox bottomTiles;

    public Bord() throws IOException, JDOMException {
        // reading xml-file using JDOM
        Document doc = new SAXBuilder().build(getClass().getResourceAsStream("ugentopoly.xml"));
        Element root = doc.getRootElement();

        Element rootSettings = root.getChild("settings");
        Element rootAreas = root.getChild("areas");
        Element rootTiles = root.getChild("tiles");

        // tiles
        InfoTile infoTile = new InfoTile();
        tiles = new Tile[40];

        int util = 1;

        // instantiate tiles
        for (int i = 0; i < 40; i++) {
            Element tile = rootTiles.getChildren().get(i);
            String type = tile.getAttributeValue("type");
            String id = tile.getAttributeValue("id");
            int pos = Integer.parseInt(tile.getAttributeValue("position"));

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
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                tiles[pos] = new StreetTile(id, colour, cost, infoTile,
                        Integer.parseInt(tile.getAttributeValue("rent0")),
                        Integer.parseInt(tile.getAttributeValue("rent1")),
                        Integer.parseInt(tile.getAttributeValue("rent2")),
                        Integer.parseInt(tile.getAttributeValue("rent3")),
                        Integer.parseInt(tile.getAttributeValue("rent4")),
                        Integer.parseInt(tile.getAttributeValue("rent5")));
            }

            // Chest
            if (type.equals("CHEST"))
                tiles[pos] = new ChestTile(id, infoTile);

            // Chance
            if (type.equals("CHANCE"))
                tiles[pos] = new ChanceTile(id, infoTile);

            // Tax
            if (type.equals("TAX")) {
                int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                tiles[pos] = new TaxTile(id, amount, infoTile);
            }

            // Railway
            if (type.equals("RAILWAY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                int rent = Integer.parseInt(tile.getAttributeValue("rent"));
                tiles[pos] = new RailwayTile(id, cost, rent, infoTile);
            }

            // Utility
            if (type.equals("UTILITY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                tiles[pos] = new UtilityTile(id, util, cost, infoTile);
                util++;
            }

            // Go to jail
            if (type.equals("GO_TO_JAIL"))
                tiles[pos] = new CornerTile(id, "go_to_jail", infoTile);

            // Start
            if (type.equals("START"))
                tiles[pos] = new CornerTile(id, "start", infoTile, Integer.parseInt(rootSettings.getAttributeValue("start")));

            // Jail
            if (type.equals("JAIL"))
                tiles[pos] = new CornerTile(id, "jail", infoTile);

            // Free parking
            if (type.equals("FREE_PARKING"))
                tiles[pos] = new CornerTile(id, "free_parking",  infoTile);
        }

        // adding tiles to left, top, right, bottom
        leftTiles = new VBox();
        topTiles = new HBox();
        rightTiles = new VBox();
        rightTiles.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        bottomTiles = new HBox();

        for (int i = 1; i < tiles.length; i++) {
            Tile t = tiles[i];

            // left
            if (i < 10) {
                leftTiles.getChildren().addFirst(t.getHBox());
            }

            // top
            else if (i < 21) {
                VBox v = t.getVBox();
                topTiles.getChildren().add(v);
            }

            // right
            else if (i < 30) {
                rightTiles.getChildren().add(t.getHBox());
            }

            // bottom
            else {
                VBox v = t.getVBox();
                if (v.getPrefWidth() != 130) {
                    v.setRotate(180);
                    v.getChildren().getFirst().setRotate(180);
                    v.getChildren().getLast().setRotate(180);
                }
                bottomTiles.getChildren().addFirst(v);
            }
        }

        // adding starttile to bottom
        VBox start = tiles[0].getVBox();
        bottomTiles.getChildren().addFirst(start);


        // logo + infoTile
        ImageView logo = new ImageView();
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/logo.png")).toExternalForm()));
        logo.setFitWidth(500);
        logo.setFitHeight(100);
        logo.setRotate(45);

        StackPane center = new StackPane(logo, infoTile);
        center.setPrefSize(585, 585);
        center.setStyle("-fx-background-color: lightgreen; -fx-border-color: black; -fx-border-width: 1");
        center.setAlignment(Pos.CENTER);

        // set all parts
        setBottom(bottomTiles);
        setLeft(leftTiles);
        setTop(topTiles);
        setRight(rightTiles);
        setCenter(center);
    }
}