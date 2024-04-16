package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.DicePanel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.concurrent.TimeUnit;

public class Logs extends VBox {

    private Bord bord;
    private VBox dice;
    private Speler[] spelers;
    private int spelersAmount;
    private HBox spelerBeurt;
    private int beurt;

    public Logs(VBox dice, Bord bord) {
        this.dice = dice;
        this.bord = bord;

        setPrefSize(260, 845);
        setMinSize(260, 845);
        setMaxSize(260, 845);

        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray");

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(25);

        Button btn = (Button) dice.getChildren().getFirst(); // first child == button

        btn.setOnMouseClicked(e -> rolled());
        btn.setDisable(true);
    }

    public void rolled() {
        final int[] result = {2, 2};

        // set result to roll result
        // TODO
        ((DicePanel) dice.getChildren().getLast()).roll(System.out::println); // last child == DicePanel

        // wait until roll has finished
        // TODO

        int pos = spelers[beurt].getPos();
        for (int i : result)
            pos += i;
        while (pos >= 40)
            pos -= 40;

        spelers[beurt].setPos(pos);
        bord.getTiles()[pos].getPlayerbox().getChildren().add(spelers[beurt].getIcon());

        beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;
        spelerBeurt.getChildren().set(spelerBeurt.getChildren().size() - 1,
                spelers[beurt].getLabel());
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

        getChildren().addAll(spelerBeurt, dice);

        // enable rol button
        dice.getChildren().getFirst().setDisable(false);
    }
}
