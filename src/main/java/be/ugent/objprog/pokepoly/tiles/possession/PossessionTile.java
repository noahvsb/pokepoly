package be.ugent.objprog.pokepoly.tiles.possession;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.InfoTile;
import be.ugent.objprog.pokepoly.tiles.Tile;
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

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return Alert.AlertType.CONFIRMATION;
        return Alert.AlertType.INFORMATION; // other
    }
    @Override
    public String getAlertDescription(Speler speler) {
        if (owner == null && cost <= speler.getBalance()) // buy?
            return "Do you want to buy " + name + " for €" + cost + "?";
        else if (owner == null) // can't buy
            return "You don't have enough money to buy this";
        else if (!owner.equals(speler)) // rent
            return "You have to pay €" + currentRent + " rent to " + owner.getShortendName(30);
        return "This is in your possession"; // yours
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
        logs.add(speler.getName() + " has bought " + name + " for €" + cost + ".");
    }
    public abstract void checkForRentUpdate(Speler speler);

    public void payRent(Speler speler) {
        owner.updateBalance(currentRent);
        speler.updateBalance(-currentRent);

        logs.add(speler.getShortendName(10) + " had to pay €" + currentRent + " to " +
                owner.getShortendName(10) + " for the renting of " + name + ".");
    }
}
