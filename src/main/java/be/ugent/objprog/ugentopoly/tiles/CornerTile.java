package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class CornerTile extends Tile {

    public CornerTile(String id, String imageName, InfoTile infoTile) throws IOException {
        this.id = id;

        this.width = N * 2;
        this.height = N * 2;
        this.imageName = imageName;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        infoTile.setup(75, this, createGraphic(true), title);
    }
}