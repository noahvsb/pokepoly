package be.ugent.objprog.ugentopoly.rightDisplay;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RollHandler {
    RightDisplay parent;

    private Bord bord;
    private SpelerStatus spelerStatus;

    private Button rolButton;

    private Speler[] spelers;
    private int spelersAmount;

    private int beurt;
    private int amountOfDoubleRollsAfterEachOther;

    public RollHandler(RightDisplay parent, Bord bord, SpelerStatus spelerStatus, Button rolButton) {
        this.parent = parent;

        this.bord = bord;
        this.spelerStatus = spelerStatus;

        this.rolButton = rolButton;

        beurt = 0;
    }

    public void setSpelers(Speler[] spelers) {
        this.spelers = spelers;

        spelersAmount = (int) Arrays.stream(spelers).filter(Objects::nonNull).count();
    }

    public void handleRoll(List<Integer> result) {
        // remove from jail if double roll
        boolean freshOutOfJail = false;
        if (spelers[beurt].isInJail() && result.getFirst().equals(result.getLast())) {
            spelers[beurt].setInJail(false);
            freshOutOfJail = true;
        }

        if (!spelers[beurt].isInJail()) {
            // change position
            int prevPos = spelers[beurt].getPos();
            int pos = prevPos + result.stream().mapToInt(i -> i).sum();
            spelers[beurt].setLastRoll(pos - prevPos);

            if (pos >= 40)
                pos -= 40;

            spelers[beurt].setPos(pos);
            bord.setPos(spelers[beurt], pos);
            updateSpelerStatus(beurt);

            // do the tile action
            if (pos < prevPos && pos != 0) {
                bord.getTiles()[0].handleTileAction(spelers[beurt], spelers);
                updateSpelerStatus(beurt);
            }
            bord.getTiles()[pos].handleTileAction(spelers[beurt], spelers);
            updateSpelerStatus();

            // check for game over
            if (spelers[beurt].getBalance() < 0)
                gameOver();

            // no double roll
            if (!result.getFirst().equals(result.getLast())) {
                amountOfDoubleRollsAfterEachOther = 0;
                beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;

                parent.setSpelerBeurtBox(spelers[beurt].getLabel(), false);
            }
            // double roll
            else if (!freshOutOfJail) {
                amountOfDoubleRollsAfterEachOther++;
                if (amountOfDoubleRollsAfterEachOther >= 3)
                    bord.getTiles()[30].handleTileAction(spelers[beurt], spelers); // handles the GoToJailTile-action to put them in jail

                parent.setSpelerBeurtBox(spelers[beurt].getLabel(), true);
            }
        } else
            bord.getTiles()[10].handleTileAction(spelers[beurt], spelers);

        // enable rolButton
        rolButton.setDisable(false);
    }

    public void updateSpelerStatus() {
        for (int i = 0; i < spelersAmount; i++)
            spelerStatus.updateTab(i, spelers[i]);
    }

    public void updateSpelerStatus(int i) {
        spelerStatus.updateTab(i, spelers[i]);
    }

    public void gameOver() {
        // zoek winnaar
        Speler winnaar = spelers[beurt];
        for (Speler s : spelers)
            if (s != null && winnaar.getBalance() < s.getBalance())
                winnaar = s;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("GAME OVER\nwinnaar: " + winnaar.getName());

        alert.showAndWait().ifPresent(response -> System.exit(0));
    }
}
