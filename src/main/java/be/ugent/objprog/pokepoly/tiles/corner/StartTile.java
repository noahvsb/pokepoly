package be.ugent.objprog.pokepoly.tiles.corner;

import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import javafx.scene.control.Alert;

import java.io.IOException;

public class StartTile extends CornerTile {
    private int start;

    public StartTile(String id, String imageName, InfoTile infoTile, int start) throws IOException {
        super(id, imageName, infoTile);
        this.start = start;
    }

    @Override
    public String getDescription() {
        return "Collect €" + start + " by passing or 2 x €" +  start + " when landing on start";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        if (speler.getPos() != 0)
            return "You passed start and collected €" + start;
        return "You landed on start and collected 2 x €" + start;
    }
    @Override
    public void responseWasOk(Speler speler) {
        if (speler.getPos() != 0) {
            speler.updateBalance(start);
            logs.add(speler.getShortendName(10) + " passed start and collected €" + start);
        } else {
            speler.updateBalance(start * 2);
            logs.add(speler.getShortendName(10) + " landed on start and collected 2 x €" + start);
        }
    }
}
