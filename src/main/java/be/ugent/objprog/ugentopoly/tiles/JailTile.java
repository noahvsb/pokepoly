package be.ugent.objprog.ugentopoly.tiles;

import java.io.IOException;

public class JailTile extends CornerTile {
    public JailTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
    }

    @Override
    public String getDescription() {
        return "Gooi met beide dobbelstenen hetzelfde aantal ogen om uit de overpoort te geraken";
    }
}
