package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class RailwayTile extends PossessionTile {
    private int rent;
    public RailwayTile(String id, int cost, int rent, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        imageName = "railway";

        this.rent = rent;
        currentRent = rent;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(name);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text huur = new Text("Huur:          €" + currentRent);
        huur.setFont(new Font(13));

        Text cost = new Text("Kostprijs:   €" + this.cost);
        cost.setFont(new Font(13));

        Text owner = new Text("Huidige eigenaar\n" + (this.owner == null ? "<te koop>" : this.owner.getShortendName()));
        owner.setFont(new Font(13));
        owner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(13, this, createGraphic(true), title, huur, cost, owner);
    }

    @Override
    public void checkForRentUpdate(Speler speler) {
        int factor = speler.getAmountOfBezittingenOfType("railway");
        for (Tile t : bord.getTiles())
            if (t.getId().matches("tile.railway[0-9]+") && speler.getBezittingen().contains((RailwayTile) t))
                ((RailwayTile) t).updateCurrentRent(factor);
    }
    private void updateCurrentRent(int factor) {
        currentRent = rent * factor;
    }
}