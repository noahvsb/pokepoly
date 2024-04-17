package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class TileAction extends VBox {
    public TileAction(double width, double height, Tile tile) {
        setMaxSize(width, height);
        setPrefSize(width, height);
        setMinSize(width, height);

        getChildren().addAll(tile.getTileActionNodes());
        setSpacing(100);
        setAlignment(Pos.CENTER);
    }
}
