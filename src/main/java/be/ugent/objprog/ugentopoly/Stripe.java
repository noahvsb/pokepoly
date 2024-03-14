package be.ugent.objprog.ugentopoly;

import javafx.scene.layout.HBox;

public class Stripe extends HBox {
    public Stripe(String colour, int width, int height) {
        this.setStyle("-fx-background-color: " + colour + "; -fx-border-color: black; -fx-border-width: 0.5");
        this.setPrefSize(width, height);
    }
}
