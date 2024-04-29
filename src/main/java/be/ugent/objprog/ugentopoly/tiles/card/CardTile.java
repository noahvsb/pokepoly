package be.ugent.objprog.ugentopoly.tiles.card;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import org.jdom2.Element;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class CardTile extends Tile {
    private Bord bord;
    private List<Element> deck;
    private int cardCounter;

    public CardTile(String id, String imageName, InfoTile infoTile, Bord bord, Element deck) throws IOException {
        this.id = id;
        this.imageName = imageName;
        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;
        this.deck = deck.getChildren();
        Collections.shuffle(this.deck);

        cardCounter = 0;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text description = Tile.Texts.description(getAlertDescription(null), INFO_TILE_FONT_SIZE + 2);

        infoTile.setup(100, this, createGraphic(true), description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public void responseWasOk(Speler speler) {
        Element c = deck.get(cardCounter);
        Card card = new Card(c, bord);

        cardCounter = cardCounter != deck.size() - 1 ? cardCounter + 1 : 0;

        card.handleCardAction(speler, spelers);

        logText = speler.getShortendName(10) + " heeft een " + (imageName.equals("chance") ? "Kans-" : "Algemeen Fonds-") + "kaart getrokken.";
    }
}
