package be.ugent.objprog.ugentopoly.tiles.corner;

import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

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
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / getImageWidthDivider());
        imageView.setFitHeight(Math.max(width, height) / getImageHeightDivider());

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(10);
        vbox.setSpacing(10);

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        Text title = Tile.Texts.title(name);

        Text description = Tile.Texts.description(getDescription());

        infoTile.setup(40, this, createGraphic(true), title, description);
    }

    public abstract String getDescription();
    public double getImageWidthDivider() {
        return 2.8;
    }
    public double getImageHeightDivider() {
        return 2.0;
    }
}