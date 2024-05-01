package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.*;
import be.ugent.objprog.ugentopoly.tiles.card.ChanceTile;
import be.ugent.objprog.ugentopoly.tiles.card.ChestTile;
import be.ugent.objprog.ugentopoly.tiles.card.Deck;
import be.ugent.objprog.ugentopoly.tiles.corner.FreeParkingTile;
import be.ugent.objprog.ugentopoly.tiles.corner.GoToJailTile;
import be.ugent.objprog.ugentopoly.tiles.corner.JailTile;
import be.ugent.objprog.ugentopoly.tiles.corner.StartTile;
import be.ugent.objprog.ugentopoly.tiles.possession.RailwayTile;
import be.ugent.objprog.ugentopoly.tiles.possession.StreetTile;
import be.ugent.objprog.ugentopoly.tiles.possession.UtilityTile;
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
import java.util.List;
import java.util.Objects;

public class Bord extends BorderPane {
    private Element rootSettings;
    private Element rootAreas;
    private Element rootTiles;

    private InfoTile infoTile;
    private Tile[] tiles;
    private List<Element> decks;

    private VBox leftTiles;
    private VBox rightTiles;
    private HBox topTiles;
    private HBox bottomTiles;

    public Bord() throws JDOMException, IOException {
        // reading xml-file using JDOM
        Document doc = new SAXBuilder().build(getClass().getResourceAsStream("ugentopoly.xml"));
        Element root = doc.getRootElement();

        rootSettings = root.getChild("settings");
        rootAreas = root.getChild("areas");
        rootTiles = root.getChild("tiles");

        // tiles
        infoTile = new InfoTile();
        tiles = new Tile[40];
        decks  = root.getChildren("deck");

        addTilesToArray();

        // adding tiles to left, top, right, bottom
        addTiles();

        // adding logo and infoTile to center
        ImageView logo = new ImageView();
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/logo.png")).toExternalForm()));
        logo.setFitWidth(500); logo.setFitHeight(100);
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

    private void addTilesToArray() throws IOException {
        int util = 1;

        Deck chanceDeck = new Deck(decks.stream().filter(d -> d.getAttributeValue("type").equals("CHANCE")).toList().getFirst(), 1);
        Deck chestDeck = new Deck(decks.stream().filter(d -> d.getAttributeValue("type").equals("CHEST")).toList().getFirst(), -1);

        for (int i = 0; i < 40; i++) {
            Element tile = rootTiles.getChildren().get(i);
            String type = tile.getAttributeValue("type");
            String id = tile.getAttributeValue("id");
            int pos = Integer.parseInt(tile.getAttributeValue("position"));

            switch (type) {
                case "STREET" -> {
                    String areaId = tile.getAttributeValue("area");
                    Element area = null;
                    for (int x = 0; x < 8; x++) {
                        area = rootAreas.getChildren().get(x);
                        if (areaId.equals(area.getAttributeValue("id")))
                            break;
                    }

                    String colour = area.getAttributeValue("color");
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                    tiles[pos] = new StreetTile(id, areaId, colour, cost, infoTile, this,
                            Integer.parseInt(tile.getAttributeValue("rent0")),
                            Integer.parseInt(tile.getAttributeValue("rent1")),
                            Integer.parseInt(tile.getAttributeValue("rent2")),
                            Integer.parseInt(tile.getAttributeValue("rent3")),
                            Integer.parseInt(tile.getAttributeValue("rent4")),
                            Integer.parseInt(tile.getAttributeValue("rent5")));
                }

                case "CHANCE" -> tiles[pos] = new ChanceTile(id, infoTile, this, chanceDeck);

                case "CHEST" -> tiles[pos] = new ChestTile(id, infoTile, this, chestDeck);

                case "TAX" -> {
                    int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                    tiles[pos] = new TaxTile(id, amount, infoTile, this);
                }

                case "RAILWAY" -> {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    int rent = Integer.parseInt(tile.getAttributeValue("rent"));
                    tiles[pos] = new RailwayTile(id, cost, rent, infoTile, this);
                }

                case "UTILITY" -> {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    tiles[pos] = new UtilityTile(id, util, cost, infoTile, this);
                    util++;
                }

                case "GO_TO_JAIL" -> tiles[pos] = new GoToJailTile(id, "go_to_jail", infoTile, this);

                case "START" -> tiles[pos] = new StartTile(id, "start", infoTile, Integer.parseInt(rootSettings.getAttributeValue("start")));

                case "JAIL" -> tiles[pos] = new JailTile(id, "jail", infoTile);

                case "FREE_PARKING" -> tiles[pos] = new FreeParkingTile(id, "free_parking",  infoTile);
            }
        }
    }

    private void addTiles() {
        leftTiles = new VBox();
        topTiles = new HBox();
        rightTiles = new VBox(); rightTiles.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        bottomTiles = new HBox();

        for (int i = 1; i < tiles.length; i++) {
            Tile t = tiles[i];

            // left
            if (i < 10)
                leftTiles.getChildren().addFirst(t.getTileWithHBox());
            // top
            else if (i < 21)
                topTiles.getChildren().add(t.getTileWithVBox());
            // right
            else if (i < 30)
                rightTiles.getChildren().add(t.getTileWithHBox());
            // bottom
            else {
                VBox v = t.getVBox();
                if (v.getPrefWidth() != 130) {
                    v.setRotate(180);
                    v.getChildren().getFirst().setRotate(180);
                    v.getChildren().getLast().setRotate(180);
                }
                bottomTiles.getChildren().addFirst(t.getTileWithVBox());
            }
        }

        // adding starttile to bottom
        bottomTiles.getChildren().addFirst(tiles[0].getTileWithVBox());
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setPos(Speler speler, int pos) {
        tiles[pos].getPlayerBox().getChildren().add(speler.getIcon());
    }

    public int getStartBalance() {
        return Integer.parseInt(rootSettings.getAttributeValue("balance"));
    }
}