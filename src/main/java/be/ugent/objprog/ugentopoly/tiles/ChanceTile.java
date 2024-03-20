package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChanceTile extends Tile {

    public ChanceTile(String id, InfoTile infoTile) throws IOException {
        this.id = id;
        imageName = "chance";

        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text description = new Text("Neem een Kans-kaart");
        description.setFont(new Font(15));

        infoTile.setup(100, this, createGraphic(true), description);
    }
}