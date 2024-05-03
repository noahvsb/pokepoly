package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.io.IOException;

public abstract class PossessionTile extends Tile {
    protected Bord bord;

    protected int cost;
    protected int currentRent;
    protected Speler owner;

    public PossessionTile(String id, int cost, InfoTile infoTile, Bord bord) throws IOException {
        this.id = id;
        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        owner = null;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;
    }

    public Node createGraphic(double height) {
        ImageView imageView = (ImageView) createGraphic(true);
        imageView.setFitWidth(height);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return Alert.AlertType.CONFIRMATION;
        return Alert.AlertType.INFORMATION; // other
    }
    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return "Wilt u " + name + " kopen voor €" + cost + "?";
        else if (owner == null) // can't buy
            return "U heeft niet genoeg geld om deze bezitting te kopen";
        else if (!owner.equals(speler)) // rent
            return "U moet €" + currentRent + " huur betalen aan " + owner.getShortendName(30);
        return "Deze bezitting is in uw bezit"; // yours
    }
    @Override
    public void responseWasOk(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy
            buyProperty(speler);
        else if (owner != null && !owner.equals(speler)) // rent
            payRent(speler);

    }

    public void buyProperty(Speler speler) {
        // update styles
        normalStyle = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: " + speler.getLighterColourString();
        hbox.setStyle(normalStyle);
        vbox.setStyle(normalStyle);

        // set owner to speler
        owner = speler;

        // remove balance from speler and add eigendom to speler
        speler.updateBalance(-cost);
        speler.addBezitting(this);

        // check if speler now has the all eigendommen of this areaColour and update rent if so
        checkForRentUpdate(speler);

        // logs
        logs.add(speler.getName() + " heeft " + name + " gekocht voor €" + cost + ".");
    }
    public abstract void checkForRentUpdate(Speler speler);

    public void payRent(Speler speler) {
        owner.updateBalance(currentRent);
        speler.updateBalance(-currentRent);

        logs.add(speler.getShortendName(10) + " moest €" + currentRent + " betalen aan " +
                owner.getShortendName(10) + " voor het huren van " + name + ".");
    }
}
