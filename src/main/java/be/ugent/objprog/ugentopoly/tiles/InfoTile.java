package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class InfoTile extends VBox {
    private double w = 200;
    private double h = 300;
    private Tile currentActive;

    public InfoTile() {
        setVisible(false);
        setPrefSize(w, h);
        setMinSize(w, h);
        setMaxSize(w, h);
        setStyle("-fx-background-color: white; -fx-background-radius: 7; -fx-border-width: 1; -fx-border-color: black; -fx-border-insets: 5");

        currentActive = null;
    }

    // default
    public void setup(double spacing, Tile currentActive, Node... nodes) {
        this.currentActive = currentActive;

        setAlignment(Pos.CENTER);
        setSpacing(spacing);
        getChildren().addAll(nodes);
        setVisible(true);
    }

    // different alignment
    public void setup(Pos alignment, double spacing, Tile currentActive, Node... nodes) {
        this.currentActive = currentActive;

        setAlignment(alignment);
        setSpacing(spacing);
        getChildren().addAll(nodes);
        setVisible(true);
    }

    public void reset() {
        setVisible(false);
        getChildren().clear();
        this.currentActive = null;
    }

    public Tile getCurrentActive() {
        return currentActive;
    }
}
