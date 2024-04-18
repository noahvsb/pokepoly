package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class UtilityTile extends Tile {
    private Bord bord;
    private int cost;
    private int util;
    private int currentFactor;
    private Speler owner;
    private boolean beideInBezit;

    public UtilityTile(String id, int util, int cost, InfoTile infoTile, Bord bord) throws IOException {
        this.id = id;
        this.util = util;
        imageName = "utility" + util;

        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        currentFactor = 4;
        owner = null;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;

        createTile();
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2.0);
        imageView.setFitHeight(Math.max(width, height) / (util == 1 ? 6.0 : 4.0));

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(0);
        vbox.setSpacing(25);

        return imageView;
    }

    @Override
    public ImageView createGraphic(double height) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(height);
        imageView.setFitHeight(height / (util == 1 ? 3 : 2));

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        ImageView imageView = createGraphic(true);
        imageView.setFitWidth(100);
        imageView.setFitHeight(util == 1 ? 33.333 : 50);

        Text huur = new Text("Huur:     worp x €" + currentFactor);
        huur.setFont(new Font(13));

        Text costPrice = new Text("Kostprijs:   €" + cost);
        costPrice.setFont(new Font(13));

        Text currentOwner = new Text("Huidige eigenaar\n" + (owner == null ? "<te koop>" : owner.getName()));
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(20, this, imageView, huur, costPrice, currentOwner);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        if (owner == null && cost <= speler.getBalance())
            return Alert.AlertType.CONFIRMATION;
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance())
            return "Wilt u " + nameStr + " kopen voor €" + cost + "?";
        else if (owner == null)
            return "U heeft niet genoeg geld om dit eigendom te kopen";
        else if (!owner.equals(speler))
            return "U moet €" + speler.getLastRoll() * currentFactor + " huur (laatste worp x " + currentFactor + ") betalen aan " + owner.getName();
        return "Dit eigendom is in uw bezit";
    }

    @Override
    public void responseWasOk(Speler speler) {
        if (owner == null && cost <= speler.getBalance())
            buyProperty(speler);
        else if (owner != null && !owner.equals(speler))
            payRent(speler);
    }

    public void buyProperty(Speler speler) {
        // update styles
        String newColour = speler.getLighterColourString();

        normalStyle = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: " + newColour;
        hbox.setStyle(normalStyle);
        vbox.setStyle(normalStyle);

        // set owner to speler
        owner = speler;

        // remove balance from speler and add eigendom to speler
        speler.updateBalance(-cost);
        speler.addEigendom(this);

        // check if speler has other railways and update rent if so
        if (speler.getEigendommenOfTypeAmount("utility") == 2)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.utility[0-9]+") && speler.getEigendommen().contains(t))
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