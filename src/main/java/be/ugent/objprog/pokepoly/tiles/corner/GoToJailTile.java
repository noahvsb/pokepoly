package be.ugent.objprog.pokepoly.tiles.corner;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
import be.ugent.objprog.pokepoly.tiles.card.ChanceTile;
import be.ugent.objprog.pokepoly.tiles.card.ChestTile;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Random;

public class GoToJailTile extends CornerTile {
    private Bord bord;

    public GoToJailTile(String id, String imageName, InfoTile infoTile, Bord bord) throws IOException {
        super(id, imageName, infoTile);
        this.bord = bord;
    }

    @Override
    public String getDescription() {
        return "You were captured by Team Rocket! Don't collect when passing start";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "You were captured by Team Rocket!";
    }
    @Override
    public void responseWasOk(Speler speler) {
        speler.setInJail(true);
        speler.setPos(10);
        bord.setPos(speler, 10);

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

        // show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(speler.isInJail() ? "Sad" : "Hurray");
        alert.setHeaderText(speler.isInJail()
                ? "From the next turn on, throw a double to escape the Team Rocket Hideout"
                : "You used an Escape Rope to escape the Team Rocket Hideout");

        // logs
        logs.add(speler.getShortendName(10) + (speler.isInJail()
                ? " was captured by Team Rocket."
                : " used an Escape Rope to escape the Team Rocket Hideout."));
    }
}
