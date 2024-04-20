package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class InfoTile extends VBox {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 300;
    private Tile currentActive;

    public InfoTile() {
        currentActive = null;

        setVisible(false);
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
        setStyle("-fx-background-color: white; -fx-background-radius: 7; -fx-border-width: 1; -fx-border-color: black; -fx-border-insets: 5");
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
        this.currentActive = null;

        setVisible(false);
        getChildren().clear();
    }

    public Tile getCurrentActive() {
        return currentActive;
    }
}