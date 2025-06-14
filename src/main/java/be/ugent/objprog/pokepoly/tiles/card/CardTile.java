package be.ugent.objprog.pokepoly.tiles.card;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import org.jdom2.Element;

import java.io.IOException;

public abstract class CardTile extends Tile {
    private Bord bord;
    private Deck deck;

    public CardTile(String id, String imageName, InfoTile infoTile, Bord bord, Deck deck) throws IOException {
        this.id = id;
        this.imageName = imageName;
        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;
        this.deck = deck;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text description = Tile.Texts.description(getAlertDescription(null), INFO_TILE_FONT_SIZE + 2);

        infoTile.setup(100, this, createGraphic(100), description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public void responseWasOk(Speler speler) {
        logs.add(speler.getShortendName(10) + " has taken a " + (imageName.equals("chance") ? "Chance" : "Chest") + " card");

        Element c = deck.getTopCard();
        Card card = new Card(c, bord, logs);

        card.handleCardAction(speler, spelers, deck);
    }

    public Deck getDeck() {
        return deck;
    }
}
