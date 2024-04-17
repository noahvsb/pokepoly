package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

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

    public Speler(String name, ImageView icon, String colour, int iconIndex, int colourIndex) {
        this.name = name;

        this.icon = icon;
        icon.setFitWidth(DEFAULT_WIDTH);
        icon.setFitHeight(DEFAULT_HEIGHT);

        this.colour = colour;

        this.iconIndex = iconIndex;
        this.colourIndex = colourIndex;

        ImageView iconCopy = new ImageView(icon.getImage());
        iconCopy.setFitHeight(28);
        iconCopy.setFitWidth(21);
        label = new Label(name, iconCopy);
        label.setTextFill(Color.web(colour));
    }

    public Label getLabel() {
        Label newLabel = new Label(label.getText(), label.getGraphic());
        newLabel.setTextFill(Color.web(colour));
        return newLabel;
    }

    public String getName() {
        return name;
    }

    public ImageView getIcon() {
        return icon;
    }

    public String getColourString() {
        return this.colour;
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

    public void addBalance(int amount) {
        balance += amount;
    }

    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }

    public int getLastRoll() {
        return lastRoll;
    }
}
