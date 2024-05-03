package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class UtilityTile extends PossessionTile {
    private int util;
    private int currentFactor;

    public UtilityTile(String id, int util, int cost, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        this.imageName = "utility" + util;

        this.util = util;
        currentFactor = 4;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        ImageView imageView = (ImageView) createGraphic(100);

        Text huur = new Text("Huur:   worp x €" + currentFactor);
        huur.setFont(new Font(INFO_TILE_FONT_SIZE));

        Text cost = Tile.Texts.cost(this.cost);
        Text owner = Tile.Texts.owner(this.owner);

        infoTile.setup(20, this, imageView, huur, cost, owner);
    }

    @Override
    public double getImageSizeDivider() {
        return 2;
    }

    @Override
    public double getHBoxSpacing() {
        return 0;
    }
    @Override
    public double getVBoxSpacing() {
        return 42;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return "Wilt u " + name + " kopen voor €" + cost + "?";
        else if (owner == null) // can't buy
            return "U heeft niet genoeg geld om deze bezitting te kopen";
        else if (!owner.equals(speler)) // rent
            return "U moet €" + speler.getLastRoll() * currentFactor + " huur (laatste worp x " + currentFactor + ") betalen aan " + owner.getShortendName(30);
        return "Deze bezitting is in uw bezit"; // yours
    }
    @Override
    public void checkForRentUpdate(Speler speler) {
        if (speler.getAmountOfBezittingenOfType("utility") == 2)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.utility[0-9]+") && speler.getBezittingen().contains((UtilityTile) t))
                    ((UtilityTile) t).updateCurrentFactor();
    }
    private void updateCurrentFactor() {
        currentFactor = 10;
    }

    public void payRent(Speler speler) {
        speler.updateBalance(-(speler.getLastRoll() * currentFactor));
        owner.updateBalance(speler.getLastRoll() * currentFactor);

        logs.add(speler.getShortendName(10) + " moest €" + (speler.getLastRoll() * currentFactor) + " betalen aan " +
                owner.getShortendName(10) + " voor het huren van " + name + ".");
    }
}