package be.ugent.objprog.ugentopoly;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

public class Speler {

    private Label label;
    private String name;
    private ImageView icon;
    private int iconIndex;
    public Speler(String name, ImageView icon, int iconIndex) {
        this.name = name;

        this.icon = icon;
        this.icon.setFitWidth(25);
        this.icon.setFitHeight(30);

        this.iconIndex = iconIndex;

        label = new Label(name, this.icon);
    }

    public Label getLabel() {
        return label;
    }

    public int getIconIndex() {
        return iconIndex;
    }
}
