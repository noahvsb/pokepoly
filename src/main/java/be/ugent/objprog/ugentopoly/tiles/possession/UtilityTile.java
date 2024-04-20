package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class UtilityTile extends PossessionTile {
    private int util;
    private int currentFactor;

    public UtilityTile(String id, int util, int cost, InfoTile infoTile, Bord bord) throws IOException {
        super(id, cost, infoTile, bord);
        this.imageName = "utility" + util;

        this.util = util;
        currentFactor = 4;

        createTile();
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2);
        imageView.setFitHeight(Math.max(width, height) / (util == 1 ? 6 : 4));

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(0);
        vbox.setSpacing(25);

        return imageView;
    }
    @Override
    public Node createGraphic(double height) {
        ImageView imageView = createGraphic(true);
        imageView.setFitWidth(height);
        imageView.setFitHeight(height / (util == 1 ? 3 : 2));

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        ImageView imageView = createGraphic(true);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100.0 / (util == 1 ? 3 : 2));

        Text huur = new Text("Huur:   worp x €" + currentFactor);
        huur.setFont(new Font(13));

        Text cost = new Text("Kostprijs:   €" + this.cost);
        cost.setFont(new Font(13));

        Text owner = new Text("Huidige eigenaar\n" + (this.owner == null ? "<te koop>" : this.owner.getShortendName()));
        owner.setFont(new Font(13));
        owner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(20, this, imageView, huur, cost, owner);
    }

    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return "Wilt u " + name + " kopen voor €" + cost + "?";
        else if (owner == null) // can't buy
            return "U heeft niet genoeg geld om deze bezitting te kopen";
        else if (!owner.equals(speler)) // rent
            return "U moet €" + speler.getLastRoll() * currentFactor + " huur (laatste worp x " + currentFactor + ") betalen aan " + owner.getShortendName();
        return "Deze bezitting is in uw bezit"; // yours
    }
    @Override
    public void checkForRentUpdate(Speler speler) {
        if (speler.getAmountOfBezittingenOfType("utility") == 2)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.utility[0-9]+") && speler.getBezittingen().contains((UtilityTile) t))
                    ((UtilityTile) t).updateCurrentFactor();
    }
    private void updateCurrentFactor() {
        currentFactor = 10;
    }

    public void payRent(Speler speler) {
        speler.updateBalance(-(speler.getLastRoll() * currentFactor));
        owner.updateBalance(speler.getLastRoll() * currentFactor);
    }
}