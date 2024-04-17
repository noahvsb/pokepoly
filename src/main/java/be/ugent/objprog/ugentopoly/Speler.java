package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

public class Speler {
    // general info
    private Label label;
    private String name;
    private ImageView icon;
    private int iconIndex;

    private static final double DEFAULT_WIDTH = 30;
    private static final double DEFAULT_HEIGHT = 40;

    // gameplay
    private int pos;
    private int balance;
    private List<Tile> eigendommen;
    private int lastRoll;

    public Speler(String name, ImageView icon, int iconIndex) {
        this.name = name;

        this.icon = icon;
        icon.setFitWidth(DEFAULT_WIDTH);
        icon.setFitHeight(DEFAULT_HEIGHT);

        this.iconIndex = iconIndex;

        ImageView iconCopy = new ImageView(icon.getImage());
        iconCopy.setFitHeight(28);
        iconCopy.setFitWidth(21);
        label = new Label(name, iconCopy);
    }

    public Label getLabel() {
        Label newLabel = new Label(label.getText());
        newLabel.setGraphic(label.getGraphic());
        return newLabel;
    }

    public String getName() {
        return name;
    }

    public ImageView getIcon() {
        return icon;
    }

    public int getIconIndex() {
        return iconIndex;
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
