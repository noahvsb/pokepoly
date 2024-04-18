package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.io.IOException;

public class GoToJailTile extends CornerTile {
    private Bord bord;
    public GoToJailTile(String id, String imageName, InfoTile infoTile, Bord bord) throws IOException {
        super(id, imageName, infoTile);

        this.bord = bord;
    }

    @Override
    public String getDescription() {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public Alert.AlertType getAlertType(Speler speler) {
        return Alert.AlertType.INFORMATION;
    }

    @Override
    public String getAlertDescription(Speler speler) {
        return "Ga direct naar de overpoort! Passeer niet langs start";
    }

    @Override
    public void responseWasOk(Speler speler, Speler[] spelers) {
        speler.setInJail(true);
        speler.setPos(10);
        bord.getTiles()[10].getPlayerBox().getChildren().add(speler.getIcon());

        // check for GetOutOfJailCards and use one if possible
        if (speler.getAmountOfGetOutOfJailCards() > 0) {
            speler.useAGetOutOfJailCard();
            speler.setInJail(false);
        }
    }
}
