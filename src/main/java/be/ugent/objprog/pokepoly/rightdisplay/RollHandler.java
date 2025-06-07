package be.ugent.objprog.pokepoly.rightdisplay;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RollHandler {
    RightDisplay parent;

    private Bord bord;
    private SpelerStatus spelerStatus;
    private Logs logs;

    private Button rolButton;

    private Speler[] spelers;
    private int spelersAmount;

    private int beurt;
    private int amountOfDoubleRollsAfterEachOther;

    public RollHandler(RightDisplay parent, Bord bord, SpelerStatus spelerStatus, Logs logs, Button rolButton) {
        this.parent = parent;

        this.bord = bord;
        this.spelerStatus = spelerStatus;
        this.logs = logs;

        this.rolButton = rolButton;

        beurt = 0;
    }

    public void setSpelers(Speler[] spelers) {
        this.spelers = spelers;
        for (Speler s : spelers)
            if (s != null)
                s.setRollHandler(this);

        spelersAmount = (int) Arrays.stream(spelers).filter(Objects::nonNull).count();
    }

    public void handleRoll(List<Integer> result) {
        // remove from jail if double roll
        boolean freshOutOfJail = false;
        if (spelers[beurt].isInJail() && allSameIntegers(result)) {
            spelers[beurt].setInJail(false);
            freshOutOfJail = true;
            logs.add(spelers[beurt].getShortendName(10) + " rolled a double to escape the Team Rocket Hideout");
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
                bord.getTiles()[0].handleTileAction(spelers[beurt]);
                updateSpelerStatus(beurt);
            }
            bord.getTiles()[pos].handleTileAction(spelers[beurt]);
            updateSpelerStatus();

            // double roll and not fresh out of jail
            if (allSameIntegers(result) && !freshOutOfJail) {
                amountOfDoubleRollsAfterEachOther++;
                if (amountOfDoubleRollsAfterEachOther >= 3) {
                    logs.add(spelers[beurt].getShortendName(10) + " rolled double 3 times in a row.");
                    bord.getTiles()[30].handleTileAction(spelers[beurt]); // handles the GoToJailTile-action to put them in jail

                    amountOfDoubleRollsAfterEachOther = 0;
                    beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;

                    parent.setSpelerBeurtBox(spelers[beurt], false, beurt);
                } else {
                    logs.add(spelers[beurt].getShortendName(10) + " rolled double and can throw again.");

                    parent.setSpelerBeurtBox(spelers[beurt], true, beurt);
                }
            }
        } else
            bord.getTiles()[10].handleTileAction(spelers[beurt]);

        // update beurt if no double roll
        if (!allSameIntegers(result) || freshOutOfJail) {
            amountOfDoubleRollsAfterEachOther = 0;
            beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;

            parent.setSpelerBeurtBox(spelers[beurt], false, beurt);
        }

        // enable rolButton
        rolButton.setDisable(false);
    }

    private boolean allSameIntegers(List<Integer> list) {
        // ik kan ook gewoon checken of het eerste gelijk is aan het laatste, maar hiermee kan je het ook checken bij lijsten met lengte groter dan 2
        // dit is niet nodig voor dit project, maar het is gewoon zo leuk om te prutsen met streams
        List<Integer> sameAsFirst = list.stream().filter(i -> i.equals(list.getFirst())).toList();
        return sameAsFirst.size() == list.size();
    }

    private void updateSpelerStatus() {
        for (int i = 0; i < spelersAmount; i++)
            spelerStatus.updateTab(i, spelers[i]);
    }

    private void updateSpelerStatus(int i) {
        spelerStatus.updateTab(i, spelers[i]);
    }

    public void gameOver() {
        // zoek winnaar
        Speler winnaar = spelers[0];
        for (Speler s : spelers)
            if (s != null && winnaar.getBalance() < s.getBalance())
                winnaar = s;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("GAME OVER\nwinner: " + winnaar.getName() + " with €" + winnaar.getBalance() + "\n\nWant to close the game?");
        alert.setGraphic(winnaar.getIcon());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
                System.exit(0);
        });
    }
}
