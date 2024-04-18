package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class TaxTile extends Tile {
    private int cost;

    public TaxTile(String id, int cost, InfoTile infoTile) throws IOException {
        this.id = id;
        imageName = "tax";

        width = N * 2;
        height = N;

        this.cost = cost;

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text description = new Text("te betalen: €" + cost);
        description.setFont(new Font(13));

        infoTile.setup(50, this, createGraphic(true), title, description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Betaal €" + cost + " TAX";
    }

    @Override
    public void responseWasOk(Speler speler) {
        speler.updateBalance(-cost);
        // TODO: toevoegen aan bonuspot
    }
}