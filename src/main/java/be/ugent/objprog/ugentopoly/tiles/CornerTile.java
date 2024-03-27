package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

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
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2.8);
        imageView.setFitHeight(Math.max(width, height) / 2.0);

        if (imageName.equals("free_parking"))
            imageView.setFitWidth(Math.max(width, height) / 2.0);

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(10);
        vbox.setSpacing(10);

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        infoTile.setup(75, this, createGraphic(true), title);
    }
}