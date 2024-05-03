package be.ugent.objprog.ugentopoly.tiles.corner;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
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
        return "Ontvang €" + start + " bij passeren";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "U passeert langs start en ontvangt €" + start;
    }
    @Override
    public void responseWasOk(Speler speler) {
        if (speler.getPos() != 0) {
            speler.updateBalance(start);
            logs.add(speler.getShortendName(10) + " passeerde langs start en ontving €" + start);
        } else {
            speler.updateBalance(start * 2);
            logs.add(speler.getShortendName(10) + " lande op start en ontving het dubbele bedrag: €" + (start * 2));
        }
    }
}
