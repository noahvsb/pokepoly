package be.ugent.objprog.ugentopoly.tiles.card;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;

import java.io.IOException;

public class ChanceTile extends CardTile {

    public ChanceTile(String id, InfoTile infoTile, Bord bord, Deck deck) throws IOException {
        super(id, "chance", infoTile, bord, deck);
    }

    @Override
    public double getHBoxSpacing() {
        return 35;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Neem een Kans-kaart";
    }
}