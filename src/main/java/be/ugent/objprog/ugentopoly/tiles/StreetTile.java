package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class StreetTile extends Tile {

    private String colour;
    private int cost;
    private int houseCost;
    private int[] rents;
    private String owner;

    public StreetTile(String id, String colour, int cost,  int houseCost, InfoTile infoTile, int... rents) throws IOException {
        this.id = id;
        this.colour = colour;

        this.width = N * 2;
        this.height = N;

        this.cost = cost;
        this.houseCost = houseCost;
        this.rents = rents;
        owner = "<te koop>";

        mouseToggle = true;
        this.infoTile = infoTile;

        createTile();
    }

    public Stripe createGraphic(boolean orientation) {
        /* deze code hoort hier niet echt thuis, maar het is wel doordat deze graphic zo specifiek is,
           dat de spacing lichtjes aangepast moet worden
         */
        hbox.setSpacing(30);
        vbox.setSpacing(30);

        return new Stripe(colour, orientation ? 25 : 65, orientation ? 65 : 25);
    }

    @Override
    public void setupInfoTile() {
        Text title = new Text(nameStr);
        title.setFont(Font.font("System", FontWeight.BOLD, 13));

        Text rent = new Text("Huur:           €" + rents[0]);
        rent.setFont(new Font(13));

        Text price = new Text("Kostprijs:     €" + cost);
        price.setFont(new Font(13));

        Text currentOwner = new Text("Huidige eigenaar\n" + owner);
        currentOwner.setFont(new Font(13));
        currentOwner.setTextAlignment(TextAlignment.CENTER);

        infoTile.setup(Pos.TOP_CENTER, 30, this, new Stripe(colour, 200, 50), title, rent, price, currentOwner);
    }
}