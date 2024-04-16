package be.ugent.objprog.ugentopoly;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

public class Speler {
    // general info
    private Label label;
    private String name;
    private ImageView icon;
    private int iconIndex;

    private static final double DEFAULT_WIDTH = 50;
    private static final double DEFAULT_HEIGHT = 60;

    // gameplay
    private int pos;

    public Speler(String name, ImageView icon, int iconIndex) {
        this.name = name;

        this.icon = icon;
        icon.setFitWidth(DEFAULT_WIDTH);
        icon.setFitHeight(DEFAULT_HEIGHT);

        this.iconIndex = iconIndex;

        ImageView iconCopy = new ImageView(icon.getImage());
        iconCopy.setFitHeight(30);
        iconCopy.setFitWidth(25);
        label = new Label(name, iconCopy);
    }

    public Label getLabel() {
        return label;
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
}
