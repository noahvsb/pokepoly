package be.ugent.objprog.ugentopoly.tiles.card;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import org.jdom2.Element;

import java.io.IOException;

public class ChestTile extends CardTile {

    public ChestTile(String id, InfoTile infoTile, Bord bord, Element deck) throws IOException {
        super(id, "chest", infoTile, bord, deck);
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Neem een Algemeen Fonds-kaart";
    }
}