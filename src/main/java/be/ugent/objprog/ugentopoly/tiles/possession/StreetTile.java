package be.ugent.objprog.ugentopoly.tiles.possession;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.tiles.InfoTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

import java.io.IOException;

public class StreetTile extends PossessionTile {
    private String areaId;
    private String areaColour;

    public StreetTile(String id, String areaId, String areaColour, int cost, InfoTile infoTile, Bord bord, int... rents) throws IOException {
        super(id, cost, infoTile, bord);
        this.areaId = areaId;
        this.areaColour = areaColour;

        this.currentRent = rents[0];

        createTile();
    }

    @Override
    public Stripe createGraphic(boolean orientation) {
        // deze code hoort hier niet echt thuis, maar het is wel doordat deze graphic specifieker is,
        // dat de alignment en spacing lichtjes aangepast moeten worden
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(35);

        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setSpacing(60);

        /*
            de stripe is een HBox, met de bedoeling om er zo makkelijk de huisjes op te plaatsen
            (dit is niet nodig voor dit project, maar als ik later nog zou willen verder werken hieraan,
            is dit wel al handig meegenomen)
         */
        return new Stripe(areaColour, orientation ? 25 : 65, orientation ? 65 : 25);
    }

    @Override
    public Stripe createGraphic(double height) {
        return new Stripe(areaColour, height, height);
    }

    @Override
    public void setupInfoTile() {
        Text title = Tile.Texts.title(name);
        Text rent = Tile.Texts.rent(currentRent);
        Text cost = Tile.Texts.cost(this.cost);
        Text owner = Tile.Texts.owner(this.owner);

        infoTile.setup(Pos.TOP_CENTER, 30, this, new Stripe(areaColour, 200, 50), title, rent, cost, owner);
    }
    @Override
    public void checkForRentUpdate(Speler speler) {
        int amount = speler.getAmountOfBezittingenInArea(areaId);
        if (((areaId.equals("area1") || areaId.equals("area8")) && amount == 2) || amount == 3)
            for (Tile t : bord.getTiles())
                if (t.getId().matches("tile.street[0-9]+") && ((StreetTile) t).getAreaId().equals(areaId))
                    ((StreetTile) t).doubleRent();
    }
    public void doubleRent() {
        currentRent *= 2;
    }

    public String getAreaId() {
        return areaId;
    }
}