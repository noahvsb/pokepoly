package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class CornerTile extends Tile {

    private int start;

    public CornerTile(String id, String imageName, InfoTile infoTile) throws IOException {
        this.id = id;

        this.width = N * 2;
        this.height = N * 2;
        this.imageName = imageName;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    public CornerTile(String id, String imageName, InfoTile infoTile, int start) throws IOException {
        this.id = id;

        this.width = N * 2;
        this.height = N * 2;
        this.imageName = imageName;

        mouseToggle = true;
        this.infoTile = infoTile;

        this.start = start;

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

        Text description = new Text(getDescription());
        description.setFont(new Font(13));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(180);

        infoTile.setup(40, this, createGraphic(true), title, description);
    }

    public String getDescription() {
        if (id.equals("tile.start"))
            return "Ontvang €" + start + " bij passeren";
        if (id.equals("tile.jail"))
            return "Gooi met beide dobbelstenen hetzelfde aantal ogen om uit de overpoort te geraken";
        if (id.equals("tile.freeparking"))
            return "Ontvang alles uit de bonuspot";
        if (id.equals("tile.gotojail"))
            return "Ga direct naar de overpoort! Passeer niet langs start";

        System.err.println("Noah, gij prutser!!");
        return null;
    }
}