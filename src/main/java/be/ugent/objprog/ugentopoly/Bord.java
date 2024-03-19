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
import java.util.ArrayList;
import java.util.Objects;

public class Bord extends BorderPane {

    private ArrayList<Tile> tiles;
    private VBox left;
    private VBox right;
    private HBox top;
    private HBox bottom;

    public Bord() throws IOException, JDOMException {
        // reading xml-file using JDOM
        Document doc = new SAXBuilder().build(getClass().getResourceAsStream("ugentopoly.deel1.xml"));
        Element root = doc.getRootElement();

        Element rootSettings = root.getChild("settings");
        Element rootAreas = root.getChild("areas");
        Element rootTiles = root.getChild("tiles");

        // tiles
        InfoTile infoTile = new InfoTile();
        tiles = new ArrayList<>();

        int util = 1;

        // instantiate tiles
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

                tiles.add(new StreetTile(id, colour, cost, houseCost, this, infoTile,
                        Integer.parseInt(tile.getAttributeValue("rent0")),
                        Integer.parseInt(tile.getAttributeValue("rent1")),
                        Integer.parseInt(tile.getAttributeValue("rent2")),
                        Integer.parseInt(tile.getAttributeValue("rent3")),
                        Integer.parseInt(tile.getAttributeValue("rent4")),
                        Integer.parseInt(tile.getAttributeValue("rent5"))));
            }

            // Chest
            if (type.equals("CHEST")) {
                tiles.add(new ChestTile(id, this, infoTile));
            }

            // Chance
            if (type.equals("CHANCE")) {
                tiles.add(new ChanceTile(id, this, infoTile));
            }

            // Tax
            if (type.equals("TAX")) {
                int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                tiles.add(new TaxTile(id, amount, this));
            }

            // Railway
            if (type.equals("RAILWAY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                tiles.add(new RailwayTile(id, cost, this));
            }

            // Utility
            if (type.equals("UTILITY")) {
                int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                tiles.add(new UtilityTile(id, util, cost, this));
                util++;
            }

            // Go to jail
            if (type.equals("GO_TO_JAIL")) {
                tiles.add(new CornerTile(id, "go_to_jail", this));
            }

            // Start
            if (type.equals("START")) {
                tiles.add(new CornerTile(id, "start", this));
            }

            // Jail
            if (type.equals("JAIL")) {
                tiles.add(new CornerTile(id, "jail", this));
            }

            // Free parking
            if (type.equals("FREE_PARKING")) {
                tiles.add(new CornerTile(id, "free_parking", this));
            }
        }

        // adding tiles to left, top, right, bottom
        left = new VBox();
        top = new HBox();
        right = new VBox();
        right.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        bottom = new HBox();

        for (int i = 1; i < tiles.size(); i++) {
            Tile t = tiles.get(i);

            // left
            if (i < 10) {
                left.getChildren().addFirst(t.getHBox());
            }

            // top
            else if (i < 21) {
                VBox v = t.getVBox();
                if (v.getPrefWidth() == 130)
                    v.setSpacing(40);
                top.getChildren().add(v);
            }

            // right
            else if (i < 30) {
                right.getChildren().add(t.getHBox());
            }

            // bottom
            else {
                VBox v = t.getVBox();
                if (v.getPrefWidth() != 130) {
                    v.setRotate(180);
                    v.getChildren().getFirst().setRotate(180);
                    v.getChildren().getLast().setRotate(180);
                } else {
                    v.setSpacing(10);
                }
                bottom.getChildren().addFirst(v);
            }
        }

        // adding starttile to bottom
        VBox start = tiles.getFirst().getVBox();
        start.setSpacing(40);
        bottom.getChildren().addFirst(start);


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
        setBottom(bottom);
        setLeft(left);
        setTop(top);
        setRight(right);
        setCenter(center);
    }

    public void changeMouseClickBlock(Tile dontToggle) {
        for (Tile t : tiles)
            if (!t.equals(dontToggle))
                t.changeMouseClickBlock();
    }
}
