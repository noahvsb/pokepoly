package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import javafx.scene.control.Alert;
import org.jdom2.Element;

public class Card {
    private Bord bord;
    private Element card;
    private String type;
    public Card(Element card, Bord bord) {
        this.bord = bord;
        this.card = card;
        type = card.getAttributeValue("type");
    }

    public void handleCardAction(Speler speler, Speler[] spelers) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kaart");
        alert.setHeaderText(getAlertHeaderText());

        System.out.println(alert.getHeaderText());

        alert.showAndWait().ifPresent(response -> executeAction(speler, spelers));
    }

    private void executeAction(Speler speler, Speler[] spelers) {
        if (type.equals("JAIl")) {
            speler.addGetOutOfJailCard();
        } else if (type.equals("MOVE")) {
            int pos = Integer.parseInt(card.getAttributeValue("position"));
            boolean collect = Boolean.parseBoolean(card.getAttributeValue("collect"));

            int prevPos = speler.getPos();
            speler.setPos(pos);
            bord.getTiles()[pos].getPlayerBox().getChildren().add(speler.getIcon());

            bord.getTiles()[pos].handleTileAction(speler, spelers);

            if (collect && prevPos > pos)
                bord.getTiles()[0].handleTileAction(speler, spelers);
        } else if (type.equals("MOVEREL")) {
            int relative = Integer.parseInt(card.getAttributeValue("relative"));

            int prevPos = speler.getPos();
            int pos = (prevPos + relative < 0 ? prevPos + relative + 40 : prevPos + relative);
            speler.setPos(pos);
            bord.getTiles()[pos].getPlayerBox().getChildren().add(speler.getIcon());

            bord.getTiles()[pos].handleTileAction(speler, spelers);

            if (relative > 0 && prevPos > pos)
                bord.getTiles()[0].handleTileAction(speler, spelers);
        } else if (type.equals("MONEY")) {
            speler.updateBalance(Integer.parseInt(card.getAttributeValue("amount")));
        } else if (type.equals("PLAYERS_MONEY")) {
            int money = Integer.parseInt(card.getAttributeValue("amount"));
            int factor = 0;

            for (Speler s : spelers)
                if (s != null && !s.equals(speler)) {
                    s.updateBalance(-money);
                    factor++;
                }

            speler.updateBalance(money * factor);
        }
    }

    private String getAlertHeaderText() {
        if (type.equals("JAIl"))
            return "Verlaat de overpoort zonder te betalen";
        if (type.equals("MOVE")) {
            int pos = Integer.parseInt(card.getAttributeValue("position"));
            boolean collect = Boolean.parseBoolean(card.getAttributeValue("collect"));

            return "Ga" + (collect ? " " : " direct ") + "naar " + bord.getTiles()[pos].getName() +
                    " (" + pos + ")";
        }
        if (type.equals("MOVEREL")) {
            int relative = Integer.parseInt(card.getAttributeValue("relative"));

            if (relative >= 0)
                return "Ga " + relative + " stappen verder";
            else
                return "Ga " + -relative + " stappen terug";
        }
        if (type.equals("MONEY")) {
            int money = Integer.parseInt(card.getAttributeValue("amount"));

            if (money >= 0)
                return "U ontvangt €" + money;
            else
                return "U moet €" + -money + " betalen";
        }
        if (type.equals("PLAYERS_MONEY")) {
            int money = Integer.parseInt(card.getAttributeValue("amount"));

            return "Elke andere speler betaalt u €" + money;
        }
        return null;
    }
}
