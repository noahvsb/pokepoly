package be.ugent.objprog.ugentopoly.tiles;

import javafx.scene.layout.*;

import java.io.IOException;

public interface Tile {

    HBox hbox = new HBox();
    VBox vbox = new VBox();

    void createTile() throws IOException;

    void showInfo();

    void mouseReleased();

    String getId();

    String getName();

    String getImagePath();

    HBox getHbox();

    VBox getVBox();
}
