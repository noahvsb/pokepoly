package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class FreeParkingTile extends CornerTile {

    private int currentPot;

    public FreeParkingTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
        currentPot = 0;
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2.0); // only difference between super
        imageView.setFitHeight(Math.max(width, height) / 2.0);

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(10);
        vbox.setSpacing(10);

        return imageView;
    }

    @Override
    public String getDescription() {
        return "Ontvang alles uit de bonuspot";
    }

    public void addToCurrentPot(int amount) {
        currentPot += amount;
    }
}
