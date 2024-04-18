package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jdom2.Element;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ChestTile extends Tile {
    private Bord bord;
    private List<Element> deck;

    public ChestTile(String id, InfoTile infoTile, Bord bord, Element deck) throws IOException {
        this.id = id;
        imageName = "chest";

        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;

        this.deck = deck.getChildren();

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text description = new Text("Neem een Algemeen Fonds-kaart");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(new Font(15));
        description.setWrappingWidth(180);

        infoTile.setup(100, this, createGraphic(true), description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Neem een Algemeen Fonds-kaart";
    }

    @Override
    public void responseWasOk(Speler speler, Speler[] spelers) {
        Card card = new Card(deck.get(new Random().nextInt(deck.size())), bord);

        card.handleCardAction(speler, spelers);
    }
}