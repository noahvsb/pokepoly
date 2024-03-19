package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InfoTile extends VBox {
    private double w = 200;
    private double h = 300;
    public InfoTile() {
        setVisible(false);
        setPrefSize(w, h);
        setMinSize(w, h);
        setMaxSize(w, h);
        setStyle("-fx-background-color: white; -fx-background-radius: 7; -fx-border-width: 1; -fx-border-color: black; -fx-border-insets: 5");
    }

    // default
    public void setup(double spacing, Node... nodes) {
        setAlignment(Pos.CENTER);
        setSpacing(spacing);
        getChildren().addAll(nodes);
        setVisible(true);
    }

    // different alignment
    public void setup(Pos alignment, double spacing, Node... nodes) {
        setAlignment(alignment);
        setSpacing(spacing);
        getChildren().addAll(nodes);
        setVisible(true);
    }

    public void reset() {
        setVisible(false);
        getChildren().clear();
    }
}
