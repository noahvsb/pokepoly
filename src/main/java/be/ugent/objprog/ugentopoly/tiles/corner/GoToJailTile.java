package be.ugent.objprog.ugentopoly.tiles.corner;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import be.ugent.objprog.ugentopoly.tiles.card.ChanceTile;
import be.ugent.objprog.ugentopoly.tiles.card.ChestTile;
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
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "Ga direct naar de overpoort!";
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
        alert.setTitle(speler.isInJail() ? "Spijtig" : "Hoera");
        alert.setHeaderText(speler.isInJail()
                ? "Gooi vanaf volgende beurt dubbel om uit de overpoort te geraken"
                : "U heeft een verlaat-overpoort-kaart gebruikt, geen overpoort deze keer");

        // logs
        logText = speler.getShortendName(10) + (speler.isInJail()
                ? " is in de overpoort beland."
                : " heeft een verlaat-overpoort-kaart gebruikt om aan de overpoort te ontsnappen.");
    }
}
