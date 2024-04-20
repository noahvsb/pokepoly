package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.text.Text;

import java.io.IOException;

public class RailwayTile extends PossessionTile {
    private int rent;
    public RailwayTile(String id, int cost, int rent, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        imageName = "railway";

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

        infoTile.setup(13, this, createGraphic(true), title, rent, cost, owner);
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