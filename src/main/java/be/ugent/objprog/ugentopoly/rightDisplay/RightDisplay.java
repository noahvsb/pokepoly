package be.ugent.objprog.ugentopoly.rightDisplay;

import be.ugent.objprog.dice.DicePanel;
import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.Speler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RightDisplay extends VBox {

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
        rolButton.setText("ROL");
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
        Text t = new Text("Aan de beurt: ");
        t.setFont(new Font(15));
        spelerBeurtBox = new HBox(t, spelers[0].getLabel());
        spelerBeurtBox.setAlignment(Pos.CENTER);
        spelerBeurtBox.setSpacing(10);
        spelerBeurtBox.setMaxWidth(width - 10);

        // add everything to the display
        getChildren().addAll(spelerStatus, logs, spelerBeurtBox, dice);

        // enable rol button
        dice.getChildren().getFirst().setDisable(false);
    }

    public void setSpelerBeurtBox(Label label, boolean isDouble) {
        Text t = new Text(isDouble ? "heeft dubbel gegooid!" : "Aan de beurt:");
        t.setFont(new Font(15));

        // clear and add
        spelerBeurtBox.getChildren().clear();
        if (!isDouble)
            spelerBeurtBox.getChildren().add(t);
        spelerBeurtBox.getChildren().add(label);
        if (isDouble)
            spelerBeurtBox.getChildren().add(t);
    }
}
