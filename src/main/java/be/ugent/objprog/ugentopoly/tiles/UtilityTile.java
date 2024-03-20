package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class UtilityTile extends Tile {
    private int cost;
    private int util;
    private String owner;

    public UtilityTile(String id, int util, int cost, InfoTile infoTile) throws IOException {
        this.id = id;
        this.util = util;
        imageName = "utility" + util;

        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        owner = "<te koop>";

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2.0);
        imageView.setFitHeight(Math.max(width, height) / (util == 1 ? 6.0 : 4.0));

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(8);
        vbox.setSpacing(33);

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        ImageView imageView = createGraphic(true);
        imageView.setFitWidth(100);
        imageView.setFitHeight(util == 1 ? 33.333 : 50);

        Text huur1 = new Text("Huur met 1:     worp x €4");
        huur1.setFont(new Font(13));

        Text huur2 = new Text("Huur met 2:   worp x €10");
        huur2.setFont(new Font(13));

        Text costPrice = new Text("Kostprijs:   €" + cost);
        costPrice.setFont(new Font(13));

        Text currentOwner = new Text("Huidige eigenaar\n" + owner);
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(20, this, imageView, huur1, huur2, costPrice, currentOwner);
    }
}