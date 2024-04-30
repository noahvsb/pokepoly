package be.ugent.objprog.ugentopoly.tiles.card;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jdom2.Element;

import java.io.IOException;
import java.util.Objects;

public class ChanceTile extends CardTile {

    public ChanceTile(String id, InfoTile infoTile, Bord bord, Deck deck) throws IOException {
        super(id, "chance", infoTile, bord, deck);
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 3.0);
        imageView.setFitHeight(Math.max(width, height) / 2.0);

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(hbox.getSpacing());
        vbox.setSpacing(vbox.getSpacing());

        return imageView;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Neem een Kans-kaart";
    }
}