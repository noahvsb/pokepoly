package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.corner.FreeParkingTile;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class TaxTile extends Tile {
    private Bord bord;
    private FreeParkingTile freeParkingTile;
    private int cost;

    public TaxTile(String id, int cost, InfoTile infoTile, Bord bord) throws IOException {
        this.id = id;
        imageName = "tax";
        width = N * 2;
        height = N;

        this.cost = cost;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;
        freeParkingTile = null;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(name);
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
    public void responseWasOk(Speler speler, Speler[] spelers) {
        if (freeParkingTile == null)
            freeParkingTile = (FreeParkingTile) bord.getTiles()[20];

        speler.updateBalance(-cost);
        freeParkingTile.addToCurrentPot(cost);
    }
}