package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class ChanceTile extends Tile {

    public ChanceTile(String id, InfoTile infoTile, StageController stageController) throws IOException {
        this.id = id;
        imageName = "chance";

        this.width = N * 2;
        this.height = N;

        mouseToggle = true;
        this.infoTile = infoTile;
        this.stageController = stageController;

        createTile();
    }

    @Override
    public ImageView createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 3.0);
        imageView.setFitHeight(Math.max(width, height) / 2.0);

        // zelfde opmerking als bij StreetTile
        hbox.setSpacing(hbox.getSpacing());
        vbox.setSpacing(vbox.getSpacing());

        return imageView;
    }

    @Override
    public void setupInfoTile() {
        Text description = new Text("Neem een Kans-kaart");
        description.setFont(new Font(15));

        infoTile.setup(100, this, createGraphic(true), description);
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}