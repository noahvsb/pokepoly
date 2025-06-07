package be.ugent.objprog.pokepoly.tiles.card;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;

import java.io.IOException;

public class ChestTile extends CardTile {

    public ChestTile(String id, InfoTile infoTile, Bord bord, Deck deck) throws IOException {
        super(id, "chest", infoTile, bord, deck);
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Take a Chest card";
    }
}