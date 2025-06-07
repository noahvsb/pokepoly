package be.ugent.objprog.pokepoly.tiles.corner;

import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.scene.text.Text;

import java.io.IOException;

public abstract class CornerTile extends Tile {

    public CornerTile(String id, String imageName, InfoTile infoTile) throws IOException {
        this.id = id;
        this.imageName = imageName;
        this.width = N * 2;
        this.height = N * 2;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = Tile.Texts.title(name);

        Text description = Tile.Texts.description(getDescription());

        infoTile.setup(40, this, createGraphic(100), title, description);
    }

    @Override
    public double getImageSizeDivider() {
        return 2;
    }

    @Override
    public double getHBoxSpacing() {
        return 10;
    }
    @Override
    public double getVBoxSpacing() {
        return 10;
    }

    public abstract String getDescription();
}