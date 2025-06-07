package be.ugent.objprog.pokepoly.tiles;

import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.corner.FreeParkingTile;
import be.ugent.objprog.pokepoly.Bord;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
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
        imageName = id.split("[.]")[1];
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
        title.setFont(Font.font("System", FontWeight.BOLD, INFO_TILE_FONT_SIZE));

        Text description = new Text("to pay: €" + cost);
        description.setFont(new Font(INFO_TILE_FONT_SIZE));

        infoTile.setup(50, this, createGraphic(100), title, description);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }
    @Override
    public String getAlertDescription(Speler speler) {
        return "Pay €" + cost + " TAX";
    }
    @Override
    public void responseWasOk(Speler speler) {
        if (freeParkingTile == null)
            freeParkingTile = (FreeParkingTile) bord.getTiles()[20];

        speler.updateBalance(-cost);
        freeParkingTile.addToCurrentPot(cost);

        logs.add(speler.getShortendName(10) + " had to pay €" + cost + " TAX.");
    }

    @Override
    public double getImageSizeDivider() {
        return 2.5;
    }
}