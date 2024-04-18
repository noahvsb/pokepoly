package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.StreetTile;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Speler {
    // general info
    private Label label;
    private String name;
    private ImageView icon;
    private String colour;
    private int iconIndex;
    private int colourIndex;

    private static final double DEFAULT_WIDTH = 30;
    private static final double DEFAULT_HEIGHT = 40;

    // gameplay
    private int pos;
    private int balance;
    private List<Tile> eigendommen;
    private int lastRoll;
    private int amountOfGetOutOfJailCards;
    private boolean inJail;

    public Speler(String name, ImageView icon, String colour, int iconIndex, int colourIndex) {
        this.name = name;

        this.icon = icon;
        icon.setFitWidth(DEFAULT_WIDTH);
        icon.setFitHeight(DEFAULT_HEIGHT);

        this.colour = colour;

        this.iconIndex = iconIndex;
        this.colourIndex = colourIndex;

        eigendommen = new ArrayList<>();

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

    public int getEigendommenOfAreaAmount(String areaId) {
        int amount = 0;
        for (Tile t : eigendommen)
            if (t.getId().matches("tile.street[0-9]+") && ((StreetTile) t).getAreaId().equals(areaId))
                amount++;
        return amount;
    }

    public int getEigendommenOfTypeAmount(String type) {
        int amount = 0;
        for (Tile t : eigendommen)
            if (t.getId().matches("tile." + type + "[0-9]+"))
                amount++;
        return amount;
    }

    public String getName() {
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

    public void addEigendom(Tile tile) {
        eigendommen.add(tile);
        eigendommen.sort(Comparator.comparing(Tile::getId));
    }

    public List<Tile> getEigendommen() {
        return eigendommen;
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

    public void useAGetOutOfJailCard() {
        amountOfGetOutOfJailCards--;
    }
}
