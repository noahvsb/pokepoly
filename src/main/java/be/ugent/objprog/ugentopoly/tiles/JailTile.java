package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
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
        return "Op bezoek";
    }

    @Override
    public void responseWasOk(Speler speler) {
        // doe niets
    }
}
