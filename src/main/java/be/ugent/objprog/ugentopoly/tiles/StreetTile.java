package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class StreetTile extends Tile {

    private String colour;
    private int cost;
    private int[] rents;
    private int currentRent;
    private String owner;

    public StreetTile(String id, String colour, int cost, InfoTile infoTile, int... rents) throws IOException {
        this.id = id;
        this.colour = colour;

        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        this.rents = rents;
        this.currentRent = rents[0];
        owner = "<te koop>";

        mouseToggle = true;
        this.infoTile = infoTile;

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
        return new Stripe(colour, orientation ? 25 : 65, orientation ? 65 : 25);
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
        Text currentOwner = new Text("Huidige eigenaar\n" + owner);
        currentOwner.setFont(new Font(13));

        infoTile.setup(Pos.TOP_CENTER, 30, this, new Stripe(colour, 200, 50), title, rent, price, currentOwner);
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        if (owner.equals("<te koop>"))
            return Alert.AlertType.CONFIRMATION;
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        if (owner.equals("<te koop>"))
            return "Wilt u " + nameStr + " kopen voor €" + cost + "?";
        else if (!owner.equals(speler.getName()))
            return "U moet €" + currentRent + " betalen aan " + owner;
        return "Dit eigendom is in uw bezit";
    }

    @Override
    public void responseWasOk(Speler speler) {
        if (owner.equals("<te koop>"))
            buyProperty();
        else if (!owner.equals(speler.getName()))
            payRent();
    }

    public void buyProperty() {
        // TODO
    }

    public void payRent() {
        // TODO
    }
}