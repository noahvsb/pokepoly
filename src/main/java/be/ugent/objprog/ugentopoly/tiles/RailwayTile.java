package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class RailwayTile extends Tile {

    private Bord bord;
    private int cost;
    private int rent;
    private int currentRent;
    private Speler owner;

    public RailwayTile(String id, int cost, int rent, InfoTile infoTile, Bord bord) throws IOException {
        this.id = id;
        imageName = "railway";

        width = N * 2;
        height = N;

        this.cost = cost;
        this.rent = rent;
        currentRent = rent;
        owner = null;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;

        createTile();
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text huur = new Text("Huur:          €" + currentRent);
        huur.setFont(new Font(13));

        Text costPrice = new Text("Kostprijs:   €" + cost);
        costPrice.setFont(new Font(13));

        Text currentOwner = new Text("Huidige eigenaar\n" + (owner == null ? "<te koop>" : owner.getName()));
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(13, this, createGraphic(true), title, huur, costPrice, currentOwner);
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
            return "U moet €" + currentRent + " huur betalen aan " + owner.getName();
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
        int factor = speler.getEigendommenOfTypeAmount("railway");
        for (Tile t : bord.getTiles())
            if (t.getId().matches("tile.railway[0-9]+") && speler.getEigendommen().contains(t))
                ((RailwayTile) t).updateCurrentRent(factor);

    }

    private void updateCurrentRent(int factor) {
        currentRent = rent * factor;
    }

    public void payRent(Speler speler) {
        owner.updateBalance(currentRent);
        speler.updateBalance(-currentRent);
    }
}