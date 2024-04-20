package be.ugent.objprog.ugentopoly.tiles.corner;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import javafx.scene.control.Alert;

import java.io.IOException;

public class FreeParkingTile extends CornerTile {
    private int bonuspot;

    public FreeParkingTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
        bonuspot = 0;
    }

    public void addToCurrentPot(int amount) {
        bonuspot += amount;
    }

    @Override
    public String getDescription() {
        return "Ontvang alles (€" + bonuspot + ") uit de bonuspot";
    }
    @Override
    public double getImageWidthDivider() {
        return 2.0;
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "U ontvang alles (€" + bonuspot + ") uit de bonuspot";
    }
    @Override
    public void responseWasOk(Speler speler, Speler[] spelers) {
        speler.updateBalance(bonuspot);
    }
}
