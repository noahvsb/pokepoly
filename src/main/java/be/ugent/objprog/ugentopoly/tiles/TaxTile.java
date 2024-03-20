package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class TaxTile extends Tile {
    private int cost;

    public TaxTile(String id, int cost, InfoTile infoTile) throws IOException {
        this.id = id;
        imageName = "tax";

        width = N * 2;
        height = N;

        this.cost = cost;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text description = new Text("te betalen: €" + cost);
        description.setFont(new Font(13));

        infoTile.setup(50, this, createGraphic(true), title, description);
    }
}