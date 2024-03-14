package be.ugent.objprog.ugentopoly;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Bord extends BorderPane {

    public Bord() throws IOException, JDOMException {
        // reading xml-file using JDOM
        Document doc = new SAXBuilder().build(getClass().getResourceAsStream("ugentopoly.deel1.xml"));
        Element root = doc.getRootElement();

        Element settings = root.getChild("settings");
        Element areas = root.getChild("areas");
        Element tiles = root.getChild("tiles");

        // tiles
        ArrayList<Tile> leftTiles = new ArrayList<>();
        ArrayList<Tile> topTiles = new ArrayList<>();
        ArrayList<Tile> rightTiles = new ArrayList<>();
        ArrayList<Tile> bottomTiles = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            Element tile = tiles.getChildren().get(i);
            String type = tile.getAttributeValue("type");
            String id = tile.getAttributeValue("id");

            // left
            if (1 <= i && i <= 9) {
                // Street
                if (type.equals("STREET")) {
                    String areaId = tile.getAttributeValue("area");
                    Element area = null;
                    for (int x = 0; x < 8; x++) {
                        area = areas.getChildren().get(x);
                        if (areaId.equals(area.getAttributeValue("id")))
                            break;
                    }

                    String colour = area.getAttributeValue("color");
                    int houseCost = Integer.parseInt(area.getAttributeValue("house"));
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                    leftTiles.add(new Tile(id, colour, 130, 65, true, cost, houseCost,
                            Integer.parseInt(tile.getAttributeValue("rent0")),
                            Integer.parseInt(tile.getAttributeValue("rent1")),
                            Integer.parseInt(tile.getAttributeValue("rent2")),
                            Integer.parseInt(tile.getAttributeValue("rent3")),
                            Integer.parseInt(tile.getAttributeValue("rent4")),
                            Integer.parseInt(tile.getAttributeValue("rent5"))));
                }

                // Chest
                if (type.equals("CHEST")) {
                    leftTiles.add(new Tile(id, "chest", 130, 65, true));
                }

                // Chance
                if (type.equals("CHANCE")) {
                    leftTiles.add(new Tile(id, "chance", 130, 65, true));
                }

                // Tax
                if (type.equals("TAX")) {
                    int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                    leftTiles.add(new Tile(id, "tax", 130, 65, true, amount));
                }

                // Railway
                if (type.equals("RAILWAY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    leftTiles.add(new Tile(id, "railway", 130, 65, true, cost));
                }
            }

            // top
            if (10 <= i && i <= 20) {
                // Street
                if (type.equals("STREET")) {
                    String areaId = tile.getAttributeValue("area");
                    Element area = null;
                    for (int x = 0; x < 8; x++) {
                        area = areas.getChildren().get(x);
                        if (areaId.equals(area.getAttributeValue("id")))
                            break;
                    }

                    String colour = area.getAttributeValue("color");
                    int houseCost = Integer.parseInt(area.getAttributeValue("house"));
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                    topTiles.add(new Tile(id, colour, 65, 130, false, cost, houseCost,
                            Integer.parseInt(tile.getAttributeValue("rent0")),
                            Integer.parseInt(tile.getAttributeValue("rent1")),
                            Integer.parseInt(tile.getAttributeValue("rent2")),
                            Integer.parseInt(tile.getAttributeValue("rent3")),
                            Integer.parseInt(tile.getAttributeValue("rent4")),
                            Integer.parseInt(tile.getAttributeValue("rent5"))));
                }

                // Chest
                if (type.equals("CHEST")) {
                    topTiles.add(new Tile(id, "chest", 65, 130, false));
                }

                // Chance
                if (type.equals("CHANCE")) {
                    topTiles.add(new Tile(id, "chance", 65, 130, false));
                }

                // Tax
                if (type.equals("TAX")) {
                    int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                    topTiles.add(new Tile(id, "tax", 65, 130, false, amount));
                }

                // Railway
                if (type.equals("RAILWAY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    topTiles.add(new Tile(id, "railway", 65, 130, false, cost));
                }

                // Jail
                if (type.equals("JAIL")) {
                    topTiles.add(new Tile(id, "jail", 130, 130, false));
                }

                // Free parking
                if (type.equals("FREE_PARKING")) {
                    topTiles.add(new Tile(id, "free_parking", 130, 130, false));
                }

                // Utility
                if (type.equals("UTILITY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    topTiles.add(new Tile(id, "utility1", 65, 130, false, cost));
                }
            }

            // right
            if (21 <= i && i <= 29) {
                // Street
                if (type.equals("STREET")) {
                    String areaId = tile.getAttributeValue("area");
                    Element area = null;
                    for (int x = 0; x < 8; x++) {
                        area = areas.getChildren().get(x);
                        if (areaId.equals(area.getAttributeValue("id")))
                            break;
                    }

                    String colour = area.getAttributeValue("color");
                    int houseCost = Integer.parseInt(area.getAttributeValue("house"));
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                    rightTiles.add(new Tile(id, colour, 130, 65, true, cost, houseCost,
                            Integer.parseInt(tile.getAttributeValue("rent0")),
                            Integer.parseInt(tile.getAttributeValue("rent1")),
                            Integer.parseInt(tile.getAttributeValue("rent2")),
                            Integer.parseInt(tile.getAttributeValue("rent3")),
                            Integer.parseInt(tile.getAttributeValue("rent4")),
                            Integer.parseInt(tile.getAttributeValue("rent5"))));
                }

                // Chest
                if (type.equals("CHEST")) {
                    rightTiles.add(new Tile(id, "chest", 130, 65, true));
                }

                // Chance
                if (type.equals("CHANCE")) {
                    rightTiles.add(new Tile(id, "chance", 130, 65, true));
                }

                // Tax
                if (type.equals("TAX")) {
                    int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                    rightTiles.add(new Tile(id, "tax", 130, 65, true, amount));
                }

                // Railway
                if (type.equals("RAILWAY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    rightTiles.add(new Tile(id, "railway", 130, 65, true, cost));
                }

                // Utility
                if (type.equals("UTILITY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    rightTiles.add(new Tile(id, "utility2", 130, 65, true, cost));
                }
            }

            // bottom
            if ((30 <= i && i <= 39) || i == 0) {
                // Street
                if (type.equals("STREET")) {
                    String areaId = tile.getAttributeValue("area");
                    Element area = null;
                    for (int x = 0; x < 8; x++) {
                        area = areas.getChildren().get(x);
                        if (areaId.equals(area.getAttributeValue("id")))
                            break;
                    }

                    String colour = area.getAttributeValue("color");
                    int houseCost = Integer.parseInt(area.getAttributeValue("house"));
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));

                    bottomTiles.add(new Tile(id, colour, 65, 130, false, cost, houseCost,
                            Integer.parseInt(tile.getAttributeValue("rent0")),
                            Integer.parseInt(tile.getAttributeValue("rent1")),
                            Integer.parseInt(tile.getAttributeValue("rent2")),
                            Integer.parseInt(tile.getAttributeValue("rent3")),
                            Integer.parseInt(tile.getAttributeValue("rent4")),
                            Integer.parseInt(tile.getAttributeValue("rent5"))));
                }

                // Chest
                if (type.equals("CHEST")) {
                    bottomTiles.add(new Tile(id, "chest", 65, 130, false));
                }

                // Chance
                if (type.equals("CHANCE")) {
                    bottomTiles.add(new Tile(id, "chance", 65, 130, false));
                }

                // Tax
                if (type.equals("TAX")) {
                    int amount = Integer.parseInt(tile.getAttributeValue("amount"));
                    bottomTiles.add(new Tile(id, "tax", 65, 130, false, amount));
                }

                // Railway
                if (type.equals("RAILWAY")) {
                    int cost = Integer.parseInt(tile.getAttributeValue("cost"));
                    bottomTiles.add(new Tile(id, "railway", 65, 130, false, cost));
                }

                // Go to jail
                if (type.equals("GO_TO_JAIL")) {
                    bottomTiles.add(new Tile(id, "go_to_jail", 130, 130, false));
                }

                // Start
                if (type.equals("START")) {
                    bottomTiles.add(new Tile(id, "start", 130, 130, false));
                }
            }
        }

        Collections.reverse(leftTiles);
        ArrayList<Tile> subBottom = new ArrayList<>(bottomTiles.subList(1, 11));
        bottomTiles = new ArrayList<>(Collections.singletonList(bottomTiles.getFirst()));
        Collections.reverse(subBottom);
        bottomTiles.addAll(subBottom);

        VBox left = new VBox();
        for (Tile t : leftTiles) {
            HBox hb = t.getHBox();
            left.getChildren().add(hb);
        }

        HBox top = new HBox();
        for (Tile t : topTiles) {
            VBox vb = t.getVBox();
            top.getChildren().add(vb);
        }

        VBox right = new VBox();
        for (Tile t : rightTiles) {
            HBox hb = t.getHBox();
            hb.setRotate(180);
            hb.getChildren().getFirst().setRotate(180);
            hb.getChildren().getLast().setRotate(180);
            right.getChildren().add(hb);
        }

        HBox bottom = new HBox();
        for (Tile t : bottomTiles) {
            VBox vb = t.getVBox();
            vb.setRotate(180);
            vb.getChildren().getFirst().setRotate(180);
            vb.getChildren().getLast().setRotate(180);
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
