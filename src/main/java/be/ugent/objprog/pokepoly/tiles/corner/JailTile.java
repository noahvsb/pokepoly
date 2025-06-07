package be.ugent.objprog.pokepoly.tiles.corner;

import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import javafx.scene.control.Alert;

import java.io.IOException;

public class JailTile extends CornerTile {
    public JailTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
    }

    @Override
    public String getDescription() {
        return "Throw a double to escape the Team Rocket Hideout";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return speler.isInJail() ? "You did not throw a double, tough luck" : "Fun family trip to the Team Rocket Hideout, enjoy!";
    }
    @Override
    public void responseWasOk(Speler speler) {
        logs.add(speler.getShortendName(10) + (speler.isInJail()
                ? " did not throw a double, remains captured by Team Rocket."
                : " visited the Team Rocket Hideout."));
    }
}
