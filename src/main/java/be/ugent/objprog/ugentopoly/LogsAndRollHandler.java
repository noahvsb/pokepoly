package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.dice.DicePanel;
import be.ugent.objprog.ugentopoly.tiles.Tile;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class LogsAndRollHandler extends VBox {
    private int amountOfDoubleRollsAfterEachOther;
    private Bord bord;
    private TabPane spelerStatus;
    private VBox dice;
    private DicePanel dicePanel;
    private Button rolButton;
    private Speler[] spelers;
    private int spelersAmount;
    private HBox spelerBeurt;
    private int beurt;

    private double width;
    private double height;

    public LogsAndRollHandler(double width, double height, VBox dice, Bord bord) {
        // variable assignment
        this.dice = dice;
        this.bord = bord;

        this.width = width;
        this.height = height;

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        // configurations
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray");

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(25);

        // status
        spelerStatus = new TabPane();
        spelerStatus.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        spelerStatus.setMaxWidth(width - 20); spelerStatus.setMaxHeight(height / 2 + spelerStatus.getTabMinHeight());
        spelerStatus.setMinWidth(width - 20); spelerStatus.setMinHeight(height / 2 + spelerStatus.getTabMinHeight());

        // logs
        // TODO: implements logs

        // dice
        dicePanel = (DicePanel) dice.getChildren().getLast(); // last child == DicePanel

        rolButton = (Button) dice.getChildren().getFirst(); // first child == Button
        rolButton.setOnAction(e -> {
            rolButton.setDisable(true);
            dicePanel.roll(this::handleRoll);
        });
        rolButton.setDisable(true);
    }

    public void handleRoll(List<Integer> result) {
        if (!spelers[beurt].isInJail()){
            // change position
            boolean langsStart = false;
            int pos = spelers[beurt].getPos();
            for (int i : result) {
                pos += i;
                if (pos >= 40) {
                    langsStart = true;
                    pos -= 40;
                }
            }

            spelers[beurt].setLastRoll(pos < spelers[beurt].getPos() ? pos + 40 - spelers[beurt].getPos() : pos - spelers[beurt].getPos());
            spelers[beurt].setPos(pos);
            bord.getTiles()[pos].getPlayerBox().getChildren().add(spelers[beurt].getIcon());

            // update status
            updateSpelerStatus();

            // do the tile action
            if (langsStart && pos != 0) {
                bord.getTiles()[0].handleTileAction(spelers[beurt], spelers);
                // update status
                updateSpelerStatus();
            }
            bord.getTiles()[pos].handleTileAction(spelers[beurt], spelers);
            if (spelers[beurt].getBalance() < 0) {
                // zoek winnaar
                Speler winnaar = spelers[beurt];
                for (Speler s : spelers)
                    if (s != null && winnaar.getBalance() < s.getBalance())
                        winnaar = s;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GAME OVER");
                alert.setHeaderText("GAME OVER\nwinnaar: " + winnaar.getName());

                alert.showAndWait().ifPresent(response -> System.exit(0));
                // TODO: change possibly
            }
            // update status
            updateSpelerStatus();
        }

        // update beurt if not a double roll
        if (!result.getFirst().equals(result.getLast())) {
            amountOfDoubleRollsAfterEachOther = 0;
            beurt = beurt == spelersAmount - 1 ? 0 : beurt + 1;

            Text t = new Text("Aan de beurt: ");
            t.setFont(new Font(15));

            spelerBeurt.getChildren().clear();
            spelerBeurt.getChildren().addAll(t, spelers[beurt].getLabel());
        } else if (!spelers[beurt].isInJail()) {
            amountOfDoubleRollsAfterEachOther++;
            if (amountOfDoubleRollsAfterEachOther >= 3)
                bord.getTiles()[30].handleTileAction(spelers[beurt], spelers);

            // laat duidelijk weten dat de speler dubbel heeft gegooid
            Text t = new Text("heeft dubbel gegooid!");
            t.setFont(new Font(15));

            spelerBeurt.getChildren().clear();
            spelerBeurt.getChildren().addAll(spelers[beurt].getLabel(), t);
        } else {
            spelers[beurt].setInJail(false);
            handleRoll(result);
        }

        // enable rolButton
        rolButton.setDisable(false);
    }

    public void setSpelers(Speler[] spelers) {
        this.spelers = spelers;

        spelersAmount = 0;
        for (Speler speler : spelers)
            if (speler != null) {
                spelersAmount++;

                // spelerStatus
                spelerStatus.getTabs().add(new Tab(speler.getName(), getTabContent(speler)));
            }

        // spelersbeurt
        Text t = new Text("Aan de beurt: ");
        t.setFont(new Font(15));
        spelerBeurt = new HBox(t, spelers[beurt].getLabel());
        spelerBeurt.setAlignment(Pos.CENTER);
        spelerBeurt.setSpacing(10);
        spelerBeurt.setMaxWidth(width - 10);

        // add everything to the logs
        getChildren().addAll(spelerStatus, spelerBeurt, dice);

        // enable rol button
        dice.getChildren().getFirst().setDisable(false);
    }

    public void updateSpelerStatus() {
        for (int i = 0; i < spelersAmount; i++)
            spelerStatus.getTabs().get(i).setContent(getTabContent(spelers[i]));
    }

    public Node getTabContent(Speler speler) {
        VBox tabContent = new VBox(20);

        tabContent.setAlignment(Pos.TOP_LEFT);
        tabContent.setStyle("-fx-background-color: white");

        Text balance = new Text("Balance: €" + speler.getBalance());
        balance.setFont(new Font(15));

        Text positie = new Text("Positie: " + bord.getTiles()[speler.getPos()].getName() + " (" + speler.getPos() + ")");
        positie.setFont(new Font(15));
        positie.setWrappingWidth(width - 25);

        // optionele inJail text
        Text inJail = new Text();
        if (speler.isInJail()) {
            inJail.setText("Speler zit in de overpoort");
            inJail.setFont(Font.font("System", FontWeight.BOLD, 15));
        }

        // optionele GetOutOfJailCards text
        Text getOutOfJailCards = new Text();
        if (speler.getAmountOfGetOutOfJailCards() > 0) {
            getOutOfJailCards.setText("Aantal kaarten om onmiddellijk de overpoort te verlaten: " +
                    speler.getAmountOfGetOutOfJailCards());
            getOutOfJailCards.setWrappingWidth(width - 25);
            getOutOfJailCards.setFont(Font.font("System", FontWeight.BOLD, 11));
        }

        ScrollPane eigendommen = new ScrollPane();
        eigendommen.setMaxSize(width - 20, height / 4);
        eigendommen.setMinSize(width - 20, height / 4);
        eigendommen.setStyle("-fx-border-color: black; -fx-border-width: 1");

        VBox eigendommenVBox = new VBox();
        eigendommen.setContent(eigendommenVBox);

        for (Tile t : speler.getEigendommen()) {
            Label eigendom = new Label(t.getName(), t.createGraphic(15));
            eigendom.setFont(new Font(15));

            eigendommenVBox.getChildren().add(eigendom);
        }

        tabContent.getChildren().addAll(speler.getLabel(), balance, positie);
        if (speler.isInJail())
            tabContent.getChildren().add(inJail);
        else if (speler.getAmountOfGetOutOfJailCards() > 0)
            tabContent.getChildren().add(getOutOfJailCards);
        tabContent.getChildren().add(eigendommen);

        return tabContent;
    }
}
