package be.ugent.objprog.pokepoly.rightdisplay;

import be.ugent.objprog.dice.DicePanel;
import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RightDisplay extends VBox {

    private Bord bord;

    private SpelerStatus spelerStatus;
    private Logs logs;

    private VBox dice;
    private DicePanel dicePanel;
    private Button rolButton;
    private RollHandler rollHandler;

    private HBox spelerBeurtBox;

    private double width;
    private double height;

    public RightDisplay(double width, double height, Bord bord) {
        // variable assignment
        this.bord = bord;

        this.width = width;
        this.height = height;

        // configurations
        setMinSize(width, height);
        setMaxSize(width, height);
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgreen");
        setAlignment(Pos.CENTER);
        setSpacing(20);

        // status
        spelerStatus = new SpelerStatus(this, bord, 15);

        // logs
        logs = new Logs(this, 13);

        // dice
        dicePanel = new DicePanel();

        rolButton = new Button();
        rolButton.setText("ROLL");
        rolButton.setFont(new Font(15));
        rolButton.setPrefSize(100, 50);
        rolButton.setDisable(true);

        rollHandler = new RollHandler(this, bord, spelerStatus, logs, rolButton);
        rolButton.setOnAction(e -> {
            rolButton.setDisable(true);
            dicePanel.roll(r -> rollHandler.handleRoll(r));
        });

        dice = new VBox();
        dice.getChildren().addAll(rolButton, dicePanel);
        dice.setAlignment(Pos.CENTER);
    }

    public void setSpelers(Speler[] spelers) {
        rollHandler.setSpelers(spelers);

        spelerStatus.addTabs(spelers);

        // spelersbeurt
        Text txt = new Text("turn: ");
        txt.setFont(new Font(15));
        spelerBeurtBox = new HBox(txt, spelers[0].getLabel());
        spelerBeurtBox.setAlignment(Pos.CENTER);
        spelerBeurtBox.setSpacing(10);
        spelerBeurtBox.setMaxWidth(width - 10);

        // add everything to the display
        getChildren().addAll(spelerStatus, logs, spelerBeurtBox, dice);

        // enable rol button
        dice.getChildren().getFirst().setDisable(false);

        for (Tile t : bord.getTiles())
            t.setLogsAndSpelers(logs, spelers);
    }

    public void setSpelerBeurtBox(Speler speler, boolean isDouble, int spelerIndex) {
        Text t = new Text(isDouble ? "rolled a double!" : "turn:");
        t.setFont(new Font(15));

        // clear and add
        spelerBeurtBox.getChildren().clear();
        if (!isDouble) {
            spelerBeurtBox.getChildren().add(t);
            spelerStatus.getSelectionModel().select(spelerIndex);
        }
        spelerBeurtBox.getChildren().add(speler.getLabel());
        if (isDouble)
            spelerBeurtBox.getChildren().add(t);
    }
}
