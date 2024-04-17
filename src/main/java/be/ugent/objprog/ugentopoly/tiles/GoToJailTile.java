package be.ugent.objprog.ugentopoly.tiles;

import java.io.IOException;

public class GoToJailTile extends CornerTile {
    public GoToJailTile(String id, String imageName, InfoTile infoTile) throws IOException {
        super(id, imageName, infoTile);
    }

    @Override
    public String getDescription() {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }
}
