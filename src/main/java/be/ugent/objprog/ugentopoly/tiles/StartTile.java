package be.ugent.objprog.ugentopoly.tiles;

import java.io.IOException;

public class StartTile extends CornerTile {

    private int start;

    public StartTile(String id, String imageName, InfoTile infoTile, int start) throws IOException {
        super(id, imageName, infoTile);

        this.start = start;
    }

    @Override
    public String getDescription() {
        return "Ontvang €" + start + " bij passeren";
    }
}
