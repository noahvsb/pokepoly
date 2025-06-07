package be.ugent.objprog.pokepoly.tiles.possession;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class UtilityTile extends PossessionTile {
    private int util;
    private int currentFactor;

    public UtilityTile(String id, int cost, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        this.imageName = id.split("[.]")[1];

        currentFactor = 10;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text huur = new Text("Rent:   throw x €" + currentFactor);
        huur.setFont(new Font(INFO_TILE_FONT_SIZE));

        Text cost = Tile.Texts.cost(this.cost);
        Text owner = Tile.Texts.owner(this.owner);

        infoTile.setup(20, this, createGraphic(100), huur, cost, owner);
    }

    @Override
    public double getImageSizeDivider() {
        return 2.5;
    }

    @Override
    public double getHBoxSpacing() {
        return 0;
    }
    @Override
    public double getVBoxSpacing() {
        return 10;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return "Do you want to buy " + name + " for €" + cost + "?";
        else if (owner == null) // can't buy
            return "You don't have enough money to buy this";
        else if (!owner.equals(speler)) // rent
            return "You have to pay €" + speler.getLastRoll() * currentFactor + " (last throw x " + currentFactor
                    + ") rent to " + owner.getShortendName(30);
        return "This is in your possession"; // yours
    }
    @Override
    public void checkForRentUpdate(Speler speler) {
        if (speler.getAmountOfBezittingenOfType("utility") == 2)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.utility[0-9]+") && speler.getBezittingen().contains((UtilityTile) t))
                    ((UtilityTile) t).updateCurrentFactor();
    }
    private void updateCurrentFactor() {
        currentFactor = 25;
    }

    public void payRent(Speler speler) {
        speler.updateBalance(-(speler.getLastRoll() * currentFactor));
        owner.updateBalance(speler.getLastRoll() * currentFactor);

        logs.add(speler.getShortendName(10) + " had to pay €" + (speler.getLastRoll() * currentFactor) + " to " +
                owner.getShortendName(10) + " for the renting of " + name + ".");
    }
}