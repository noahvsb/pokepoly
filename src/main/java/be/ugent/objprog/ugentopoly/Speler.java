package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.possession.PossessionTile;
import be.ugent.objprog.ugentopoly.tiles.possession.StreetTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Speler {
    // general info
    private String name;
    private ImageView icon;
    private String colour;

    private int iconIndex;
    private int colourIndex;

    private static final double DEFAULT_ICON_WIDTH = 30;
    private static final double DEFAULT_ICON_HEIGHT = 40;

    // gameplay
    private int pos;
    private int balance;
    private List<PossessionTile> bezittingen;

    private int lastRoll;

    private int amountOfGetOutOfJailCards;
    private boolean inJail;

    public Speler(String name, ImageView icon, String colour, int iconIndex, int colourIndex) {
        this.name = name;

        this.icon = icon;
        icon.setFitWidth(DEFAULT_ICON_WIDTH);
        icon.setFitHeight(DEFAULT_ICON_HEIGHT);

        this.colour = colour;

        this.iconIndex = iconIndex;
        this.colourIndex = colourIndex;

        bezittingen = new ArrayList<>();

        amountOfGetOutOfJailCards = 0;
        inJail = false;
    }

    public Label getLabel() {
        ImageView iconCopy = new ImageView(icon.getImage());
        iconCopy.setFitHeight(28);
        iconCopy.setFitWidth(21);

        Label label = new Label(name, iconCopy);
        label.setTextFill(Color.web(colour));

        return label;
    }

    public Label getLabel(double fontSize) {
        Label label = this.getLabel();
        label.setFont(new Font(fontSize));

        return label;
    }

    public String getName() {
        return name;
    }
    public String getShortendName(int i) {
        if (name.length() > i)
            return name.substring(0, i - 3) + "...";
        return name;
    }
    public ImageView getIcon() {
        return icon;
    }
    public String getColourString() {
        return colour;
    }
    public String getLighterColourString() {
        return colour.equals("red") ? "pink" : "light" + colour;
    }
    public int getIconIndex() {
        return iconIndex;
    }
    public int getColourIndex() {
        return colourIndex;
    }

    public void addBezitting(PossessionTile tile) {
        bezittingen.add(tile);
        bezittingen.sort(Comparator.comparing(Tile::getId));
    }
    public List<PossessionTile> getBezittingen() {
        return bezittingen;
    }
    public int getAmountOfBezittingenInArea(String areaId) {
        int amount = 0;
        for (Tile t : bezittingen)
            if (t.getId().matches("tile.street[0-9]+") && ((StreetTile) t).getAreaId().equals(areaId))
                amount++;
        return amount;
    }
    public int getAmountOfBezittingenOfType(String type) {
        int amount = 0;
        for (Tile t : bezittingen)
            if (t.getId().matches("tile." + type + "[0-9]+"))
                amount++;
        return amount;
    }

    public int getPos() {
        return pos;
    }
    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void updateBalance(int amount) {
        balance += amount;
    }

    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }
    public int getLastRoll() {
        return lastRoll;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }
    public boolean isInJail() {
        return inJail;
    }
    public void addGetOutOfJailCard() {
        amountOfGetOutOfJailCards++;
    }
    public int getAmountOfGetOutOfJailCards() {
        return amountOfGetOutOfJailCards;
    }
    public void useGetOutOfJailCard() {
        amountOfGetOutOfJailCards--;
    }
}
