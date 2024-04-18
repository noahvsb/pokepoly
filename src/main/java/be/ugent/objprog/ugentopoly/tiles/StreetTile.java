package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class StreetTile extends Tile {

    private Bord bord;
    private String areaId;
    private String areaColour;
    private int cost;
    private int[] rents;
    private int currentRent;

    private Speler owner;

    public StreetTile(String id, String areaId, String areaColour, int cost, InfoTile infoTile, Bord bord, int... rents) throws IOException {
        this.id = id;
        this.areaId = areaId;
        this.areaColour = areaColour;

        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        this.rents = rents;
        this.currentRent = rents[0];
        owner = null;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.bord = bord;

        createTile();
    }

    public Stripe createGraphic(boolean orientation) {
        /* deze code hoort hier niet echt thuis, maar het is wel doordat deze graphic zo specifiek is,
           dat de spacing lichtjes aangepast moet worden
         */
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(35);

        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setSpacing(55);

        /*
            de stripe is een HBox, met de bedoeling om er zo makkelijk de huisjes op te plaatsen
            (dit is niet nodig voor dit project, maar als ik later nog zou willen verder werken hieraan,
            is dit wel al handig meegenomen)
         */
        return new Stripe(areaColour, orientation ? 25 : 65, orientation ? 65 : 25);
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text rent = new Text("Huur:           €" + currentRent);
        rent.setFont(new Font(13));

        Text price = new Text("Kostprijs:     €" + cost);
        price.setFont(new Font(13));

        // current owner
        Text currentOwner = new Text("Huidige eigenaar\n" + (owner == null ? "<te koop>" : owner.getName()));
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(Pos.TOP_CENTER, 30, this, new Stripe(areaColour, 200, 50), title, rent, price, currentOwner);
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
            return "U heeft niet genoeg geld om dit eigendommen te kopen";
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

        // check if speler now has the all eigendommen of this areaColour and update rent if so
        int amount = speler.getEigendommenOfAreaAmount(areaId);
        if (((areaId.equals("area1") || areaId.equals("area8")) && amount == 2) || amount == 3)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.street[0-9]+") && ((StreetTile) t).getAreaId().equals(areaId))
                    ((StreetTile) t).doubleRent();
    }

    public void payRent(Speler speler) {
        owner.updateBalance(currentRent);
        speler.updateBalance(-currentRent);
    }

    public String getAreaId() {
        return areaId;
    }

    public void doubleRent() {
        currentRent *= 2;
    }
}