package be.ugent.objprog.ugentopoly;

import javafx.scene.layout.HBox;

public class Stripe extends HBox {
    public Stripe(String colour, int width, int height) {
        this.setStyle("-fx-background-color: " + colour);
        this.setPrefSize(width, height);
    }
}
