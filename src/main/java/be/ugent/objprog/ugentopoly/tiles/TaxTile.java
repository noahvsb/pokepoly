package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

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
