package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.layout.*;

import java.io.IOException;

public interface Tile {

    double fontSize = 11;

    int n = 65;

    double borderWidth = 1;

    String normalStyle = "-fx-border-color: black; -fx-border-width: " + borderWidth + "; -fx-background-color: white";
    String highlightStyle = "-fx-border-color: #1F8CFF; -fx-border-width: " + (borderWidth + 1) + "; -fx-background-color: white";

    void createTile() throws IOException;

    void tilePressed();

    void changeMouseClickBlock();

    String getId();

    String getName();

    String getImagePath();

    HBox getHBox();

    VBox getVBox();
}
