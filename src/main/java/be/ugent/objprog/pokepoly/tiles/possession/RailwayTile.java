package be.ugent.objprog.pokepoly.tiles.possession;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.scene.text.Text;

import java.io.IOException;

public class RailwayTile extends PossessionTile {
    private int rent;
    public RailwayTile(String id, int cost, int rent, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        imageName = id.split("[.]")[1];

        this.rent = rent;
        currentRent = rent;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = Tile.Texts.title(name);
        Text rent = Tile.Texts.rent(currentRent);
        Text cost = Tile.Texts.cost(this.cost);
        Text owner = Tile.Texts.owner(this.owner);

        infoTile.setup(13, this, createGraphic(100), title, rent, cost, owner);
    }

    @Override
    public void checkForRentUpdate(Speler speler) {
        int factor = speler.getAmountOfBezittingenOfType("railway");
        for (Tile t : bord.getTiles())
            if (t.getId().matches("tile.railway[0-9]+") && speler.getBezittingen().contains((RailwayTile) t))
                ((RailwayTile) t).updateCurrentRent(factor);
    }
    private void updateCurrentRent(int factor) {
        currentRent = rent * factor;
    }
}