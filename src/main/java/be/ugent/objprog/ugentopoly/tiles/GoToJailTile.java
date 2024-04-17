package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;

import java.io.IOException;

public class GoToJailTile extends CornerTile {
    public GoToJailTile(String id, String imageName, InfoTile infoTile, StageController stageController) throws IOException {
        super(id, imageName, infoTile, stageController);
    }

    @Override
    public String getDescription() {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}
