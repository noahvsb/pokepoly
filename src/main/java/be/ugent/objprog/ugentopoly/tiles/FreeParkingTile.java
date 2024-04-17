package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class FreeParkingTile extends CornerTile {

    private int currentPot;

    public FreeParkingTile(String id, String imageName, InfoTile infoTile, StageController stageController) throws IOException {
        super(id, imageName, infoTile, stageController);
        currentPot = 0;
    }

    @Override
    public String getDescription() {
        return "Ontvang alles uit de bonuspot";
    }

    @Override
    public double getImageWidthDivider() {
        return 2.0;
    }

    public void addToCurrentPot(int amount) {
        currentPot += amount;
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}
