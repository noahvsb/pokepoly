package be.ugent.objprog.pokepoly.tiles.possession;

import javafx.scene.layout.HBox;

public class Stripe extends HBox {
    public Stripe(String colour, double width, double height) {
        setPrefSize(width, height);
        setStyle("-fx-background-color: " + colour);
    }
}
