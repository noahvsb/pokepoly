package be.ugent.objprog.pokepoly.tiles.card;

import be.ugent.objprog.pokepoly.rightdisplay.Logs;
import be.ugent.objprog.pokepoly.tiles.corner.FreeParkingTile;
import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jdom2.Element;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

public class Card {
    private Bord bord;
    private Logs logs;
    private FreeParkingTile freeParkingTile;
    private Element card;
    private String id;
    private String headerText;

    public Card(Element card, Bord bord, Logs logs) {
        this.bord = bord;
        this.logs = logs;
        freeParkingTile = (FreeParkingTile) bord.getTiles()[20];

        this.card = card;
        id = card.getAttributeValue("id");
        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("/be/ugent/objprog/pokepoly/pokepoly.properties"));
        } catch(IOException e) {
            // smd
        }
        headerText = props.getProperty(id);
    }

    public void handleCardAction(Speler speler, Speler[] spelers, Deck deck) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card");
        alert.setHeaderText(headerText);

        alert.setGraphic(getAlertGraphic(deck));

        alert.showAndWait().ifPresent(r -> executeAction(speler, spelers, deck));
    }

    public ImageView getAlertGraphic(Deck deck) {
        ImageView graphic = new ImageView();
        if (deck.getType() == 1)
            graphic.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/be/ugent/objprog/pokepoly/assets/chance.png"))));
        else
            graphic.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/be/ugent/objprog/pokepoly/assets/chest.png"))));

        graphic.setFitHeight(40); graphic.setFitWidth(40); graphic.setPreserveRatio(true);

        return graphic;
    }

    private void executeAction(Speler speler, Speler[] spelers, Deck deck) {
        switch (card.getAttributeValue("type")) {
            case "JAIL" -> {
                speler.addGetOutOfJailCard(deck.getType());
                deck.removeGetOutOfJailCard();
            }
            case "MOVE" -> {
                int pos = Integer.parseInt(card.getAttributeValue("position"));
                boolean collect = Boolean.parseBoolean(card.getAttributeValue("collect"));
                int prevPos = speler.getPos();

                speler.setPos(pos);
                bord.setPos(speler, pos);

                if (collect && prevPos > pos)
                    bord.getTiles()[0].handleTileAction(speler);

                if (pos == 10)
                    toJail(speler);
                else
                    bord.getTiles()[pos].handleTileAction(speler);
            }
            case "MOVEREL" -> {
                int relative = Integer.parseInt(card.getAttributeValue("relative"));
                int prevPos = speler.getPos();
                int pos = (prevPos + relative < 0 ? prevPos + relative + 40 : prevPos + relative);

                speler.setPos(pos);
                bord.setPos(speler, pos);

                if (relative > 0 && prevPos > pos)
                    bord.getTiles()[0].handleTileAction(speler);

                bord.getTiles()[pos].handleTileAction(speler);
            }
            case "MONEY" -> {
                int amount = Integer.parseInt(card.getAttributeValue("amount"));
                speler.updateBalance(amount);
                if (amount < 0)
                    freeParkingTile.addToCurrentPot(-amount);
            }
            case "PLAYERS_MONEY" -> {
                int amount = Integer.parseInt(card.getAttributeValue("amount"));
                int factor = 0;

                for (Speler s : spelers)
                    if (s != null && !s.equals(speler)) {
                        s.updateBalance(-amount);
                        factor++;
                    }

                speler.updateBalance(amount * factor);
            }
        }
    }

    public void toJail(Speler speler) {
        speler.setInJail(true);

        // check for GetOutOfJailCards and use one if possible
        int cardType = speler.getGetOutOfJailCards();
        if (cardType != 0) {
            if (cardType == 2)
                cardType = new Random().nextInt(2) == 0 ? -1 : 1;

            speler.useGetOutOfJailCard(cardType);
            speler.setInJail(false);

            // add the card back to the deck
            int i = 0;
            Tile t = bord.getTiles()[i];
            while ((cardType == 1 && !t.getId().matches("tile.chance")) || (cardType == -1 && !t.getId().matches("tile.chest"))) {
                i++;
                t = bord.getTiles()[i];
            }
            if (cardType == 1)
                ((ChanceTile) t).getDeck().addGetOutOfJailCard();
            else
                ((ChestTile) t).getDeck().addGetOutOfJailCard();
        }

        // logs
        logs.add(speler.getShortendName(10) + (speler.isInJail()
                ? " got captured by Team Rocket."
                : " used an Escape Rope to escape the Team Rocket Hideout."));
    }
}
