package be.ugent.objprog.ugentopoly;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class Logs extends VBox {
    public Logs(VBox dice) {
        setPrefSize(260, 845);
        setMinSize(260, 845);
        setMaxSize(260, 845);

        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray");

        setAlignment(Pos.BOTTOM_CENTER);

        getChildren().add(dice);
    }
}
