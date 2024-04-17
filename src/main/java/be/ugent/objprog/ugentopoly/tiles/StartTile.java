package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;

import java.io.IOException;

public class StartTile extends CornerTile {

    private int start;

    public StartTile(String id, String imageName, InfoTile infoTile, int start, StageController stageController) throws IOException {
        super(id, imageName, infoTile, stageController);

        this.start = start;
    }

    @Override
    public String getDescription() {
        return "Ontvang €" + start + " bij passeren";
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}
