package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.io.IOException;

public class GoToJailTile extends CornerTile {
    public GoToJailTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
    }

    @Override
    public String getDescription() {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public void responseWasOk(Speler speler) {
        // TODO
    }
}
