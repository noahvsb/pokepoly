package be.ugent.objprog.ugentopoly.tiles.card;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.corner.FreeParkingTile;
import javafx.scene.control.Alert;
import org.jdom2.Element;

public class Card {
    private Bord bord;
    private FreeParkingTile freeParkingTile;
    private Element card;

    public Card(Element card, Bord bord) {
        this.bord = bord;
        freeParkingTile = (FreeParkingTile) bord.getTiles()[20];
        this.card = card;
    }

    public void handleCardAction(Speler speler, Speler[] spelers) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kaart");
        alert.setHeaderText(getAlertHeaderText());

        alert.showAndWait().ifPresent(r -> executeAction(speler, spelers));
    }

    private void executeAction(Speler speler, Speler[] spelers) {
        switch (card.getAttributeValue("type")) {
            case "JAIL" -> speler.addGetOutOfJailCard();
            case "MOVE" -> {
                int pos = Integer.parseInt(card.getAttributeValue("position"));
                boolean collect = Boolean.parseBoolean(card.getAttributeValue("collect"));
                int prevPos = speler.getPos();

                speler.setPos(pos);
                bord.setPos(speler, pos);

                if (collect && prevPos > pos)
                    bord.getTiles()[0].handleTileAction(speler, spelers);

                bord.getTiles()[pos].handleTileAction(speler, spelers);
            }
            case "MOVEREL" -> {
                int relative = Integer.parseInt(card.getAttributeValue("relative"));
                int prevPos = speler.getPos();
                int pos = (prevPos + relative < 0 ? prevPos + relative + 40 : prevPos + relative);

                speler.setPos(pos);
                bord.setPos(speler, pos);

                if (relative > 0 && prevPos > pos)
                    bord.getTiles()[0].handleTileAction(speler, spelers);

                bord.getTiles()[pos].handleTileAction(speler, spelers);
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

    private String getAlertHeaderText() {
        String headerText = null;

        switch (card.getAttributeValue("type")) {
            case "JAIL" -> headerText = "Verlaat de overpoort zonder te betalen";
            case "MOVE" -> {
                int pos = Integer.parseInt(card.getAttributeValue("position"));
                boolean collect = Boolean.parseBoolean(card.getAttributeValue("collect"));

                headerText = "Ga" + (collect ? " " : " direct ") + "naar " + bord.getTiles()[pos].getName() +
                        " (" + pos + ")";
            }
            case "MOVEREL" -> {
                int relative = Integer.parseInt(card.getAttributeValue("relative"));

                if (relative >= 0)
                    headerText = "Ga " + relative + " stappen verder";
                else
                    headerText =  "Ga " + -relative + " stappen terug";
            }
            case "MONEY" -> {
                int money = Integer.parseInt(card.getAttributeValue("amount"));

                if (money >= 0)
                    headerText = "U ontvangt €" + money;
                else
                    headerText = "U moet €" + -money + " betalen";
            }
            case "PLAYERS_MONEY" -> {
                int money = Integer.parseInt(card.getAttributeValue("amount"));

                headerText = "Elke andere speler betaalt u €" + money;
            }
        }
        return headerText;
    }
}
