package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;

import java.io.IOException;

public class JailTile extends CornerTile {
    public JailTile(String id, String imageName, InfoTile infoTile, StageController stageController) throws IOException {
        super(id, imageName, infoTile, stageController);
    }

    @Override
    public String getDescription() {
        return "Gooi met beide dobbelstenen hetzelfde aantal ogen om uit de overpoort te geraken";
    }

    @Override
    public Node[] getTileActionNodes() {
        return new Node[0];
    }
}
