package be.ugent.objprog.pokepoly.tiles.corner;

import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
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
        return "Take everything (€" + bonuspot + ") from the bonus pot";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "U can take everything (€" + bonuspot + ") from the bonus pot";
    }
    @Override
    public void responseWasOk(Speler speler) {
        speler.updateBalance(bonuspot);
        logs.add(speler.getShortendName(10) + " took €" + bonuspot + " from the bonus pot.");
        bonuspot = 0;
    }
}
