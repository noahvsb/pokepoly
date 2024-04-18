package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jdom2.Element;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ChanceTile extends Tile {

    private Bord bord;
    private List<Element> deck;

    public ChanceTile(String id, InfoTile infoTile, Bord bord, Element deck) throws IOException {
        this.id = id;
        imageName = "chance";

        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;

        this.deck = deck.getChildren();

        createTile();
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
    public ImageView createGraphic(double height) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(height);
        imageView.setFitHeight(height * 2/3);

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        Text description = new Text("Neem een Kans-kaart");
        description.setFont(new Font(15));

        infoTile.setup(100, this, createGraphic(true), description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Neem een Kans-kaart";
    }

    @Override
    public void responseWasOk(Speler speler, Speler[] spelers) {
        Card card = new Card(deck.get(new Random().nextInt(deck.size())), bord);

        card.handleCardAction(speler, spelers);
    }
}