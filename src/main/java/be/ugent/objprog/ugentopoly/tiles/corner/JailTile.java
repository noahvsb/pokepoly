package be.ugent.objprog.ugentopoly.tiles.corner;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import javafx.scene.control.Alert;

import java.io.IOException;

public class JailTile extends CornerTile {
    public JailTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
    }

    @Override
    public String getDescription() {
        return "Gooi met beide dobbelstenen hetzelfde aantal ogen om uit de overpoort te geraken";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return speler.isInJail() ? "U heeft geen dubbel gegooid, blijven zitten dus" : "Op bezoek";
    }
    @Override
    public void responseWasOk(Speler speler, Speler[] spelers) {
        // doe niets
    }
}
