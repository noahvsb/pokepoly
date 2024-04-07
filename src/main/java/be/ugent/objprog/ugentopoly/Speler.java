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
    public Speler(String name, String icon) {
        this.name = name;

        Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/" + icon + ".png")));

        this.icon = new ImageView(iconImage);
        this.icon.setFitWidth(25);
        this.icon.setFitHeight(30);

        label = new Label(name, this.icon);
        label.setFont(new Font(20));
    }

    public Label getLabel() {
        return label;
    }
}
