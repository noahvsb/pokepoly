package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class RailwayTile extends Tile {

    private int cost;
    private int rent;
    private String owner;

    public RailwayTile(String id, int cost, int rent, InfoTile infoTile, StageController stageController) throws IOException {
        this.id = id;
        imageName = "railway";

        width = N * 2;
        height = N;

        this.cost = cost;
        this.rent = rent;
        owner = "<te koop>";

        mouseToggle = true;
        this.infoTile = infoTile;
        this.stageController = stageController;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text huur1 = new Text("Huur met 1:          €" + rent);
        huur1.setFont(new Font(13));

        Text huur2 = new Text("Huur met 2:        €" + (rent * 2));
        huur2.setFont(new Font(13));

        Text huur3 = new Text("Huur met 3:        €" + (rent * 3));
        huur3.setFont(new Font(13));

        Text huur4 = new Text("Huur met 4:        €" + (rent * 4));
        huur4.setFont(new Font(13));

        Text costPrice = new Text("Kostprijs:   €" + cost);
        costPrice.setFont(new Font(13));

        Text currentOwner = new Text("Huidige eigenaar\n" + owner);
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(13, this, createGraphic(true), title, huur1, huur2, huur3, huur4, costPrice, currentOwner);
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}