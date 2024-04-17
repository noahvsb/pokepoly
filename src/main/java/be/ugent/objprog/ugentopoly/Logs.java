package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.DicePanel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Logs extends VBox {

    private Bord bord;
    private VBox dice;
    private DicePanel dicePanel;
    private Button rolButton;
    private Speler[] spelers;
    private int spelersAmount;
    private HBox spelerBeurt;
    private int beurt;

    private double width;
    private double height;

    public Logs(double width, double height, VBox dice, Bord bord) {
        this.dice = dice;
        this.bord = bord;

        this.width = width;
        this.height = height;

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray");

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(25);

        dicePanel = (DicePanel) dice.getChildren().getLast(); // last child == DicePanel

        rolButton = (Button) dice.getChildren().getFirst(); // first child == Button
        rolButton.setOnAction(e -> {
            rolButton.setDisable(true);
            dicePanel.roll(this::handleRoll);
        });
        rolButton.setDisable(true);
    }

    public void handleRoll(List<Integer> result) {
        // change position
        int pos = spelers[beurt].getPos();
        for (int i : result) {
            pos += i;
            pos -= pos >= 40 ? 40 : 0;
        }

        spelers[beurt].setPos(pos);
        bord.getTiles()[pos].getPlayerBox().getChildren().add(spelers[beurt].getIcon());

        // do the tile action
        bord.getTiles()[pos].handleTileAction(spelers[beurt]);

        // update logs
        update();

        // update beurt if not a double roll
        if (!result.getFirst().equals(result.getLast())) {
            beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;

            Text t = new Text("Aan de beurt: ");
            t.setFont(new Font(15));

            spelerBeurt.getChildren().clear();
            spelerBeurt.getChildren().addAll(t, spelers[beurt].getLabel());
        } else {
            // laat duidelijk weten dat de speler dubbel heeft gegooid
            Text t = new Text("heeft dubbel gegooid!");
            t.setFont(new Font(15));

            spelerBeurt.getChildren().clear();
            spelerBeurt.getChildren().addAll(spelers[beurt].getLabel(), t);
        }

        // enable rolButton
        rolButton.setDisable(false);
    }

    public void setSpelers(Speler[] spelers) {
        this.spelers = spelers;

        spelersAmount = 0;
        for (Speler speler : spelers)
            if (speler != null)
                spelersAmount++;

        Text t = new Text("Aan de beurt: ");
        t.setFont(new Font(15));
        spelerBeurt = new HBox(t, spelers[beurt].getLabel());
        spelerBeurt.setAlignment(Pos.CENTER);
        spelerBeurt.setSpacing(10);
        spelerBeurt.setMaxWidth(width - 10);

        getChildren().addAll(spelerBeurt, dice);

        // enable rol button
        dice.getChildren().getFirst().setDisable(false);
    }

    public void update() {
        // TODO
    }
}
