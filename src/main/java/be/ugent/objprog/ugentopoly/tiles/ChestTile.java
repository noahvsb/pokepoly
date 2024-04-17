package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class ChestTile extends Tile {

    public ChestTile(String id, InfoTile infoTile, StageController stageController) throws IOException {
        this.id = id;
        imageName = "chest";

        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.stageController = stageController;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text description = new Text("Neem een Algemeen Fonds-kaart");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(new Font(15));
        description.setWrappingWidth(180);

        infoTile.setup(100, this, createGraphic(true), description);
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}