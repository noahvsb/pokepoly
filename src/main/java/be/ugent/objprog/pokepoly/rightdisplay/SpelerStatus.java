package be.ugent.objprog.pokepoly.rightdisplay;

import be.ugent.objprog.pokepoly.Bord;
import be.ugent.objprog.pokepoly.Speler;
import be.ugent.objprog.pokepoly.tiles.possession.PossessionTile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SpelerStatus extends TabPane {
    private Bord bord;

    private double parentWidth;
    private double parentHeight;
    private double fontSize;

    public SpelerStatus(VBox parent, Bord bord, double fontSize) {
        // variables
        this.bord = bord;
        this.parentWidth = parent.getMinWidth();
        this.parentHeight = parent.getMinHeight();
        this.fontSize = fontSize;

        // configurations
        setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setMaxWidth(parentWidth - 20); setMaxHeight(parentHeight / 2 + getTabMinHeight());
        setMinWidth(parentWidth - 20); setMinHeight(parentHeight / 2 + getTabMinHeight());
    }

    public void addTab(Speler speler) {
        getTabs().add(new Tab(speler.getName(), getTabContent(speler)));
    }

    public void addTabs(Speler[] spelers) {
        for (Speler speler : spelers)
            if (speler != null)
                addTab(speler);
    }

    public void updateTab(int i, Speler speler) {
        getTabs().get(i).setContent(getTabContent(speler));
    }

    private Node getTabContent(Speler speler) {
        VBox tabContent = new VBox(20);
        tabContent.setAlignment(Pos.BOTTOM_CENTER);
        tabContent.setStyle("-fx-background-color: E3F1FF");

        // balance text
        Text balance = new Text("Balance: €" + speler.getBalance());
        balance.setFont(new Font(fontSize));

        // position text
        Text position = new Text("Position: " + bord.getTiles()[speler.getPos()].getName() + " (" + speler.getPos() + ")");
        position.setFont(new Font(fontSize));
        position.setWrappingWidth(parentWidth - 25);
        position.setTextAlignment(TextAlignment.CENTER);

        // optionele inJail text
        Text inJail = new Text();
        if (speler.isInJail()) {
            inJail.setText("Player is captured by Team Rocket");
            inJail.setFont(Font.font("System", FontWeight.BOLD, fontSize - 1));
        }

        // optionele GetOutOfJailCards text
        Text getOutOfJailCards = new Text();
        if (speler.getGetOutOfJailCards() != 0) {
            getOutOfJailCards.setText("Escape Ropes: " + Math.abs(speler.getGetOutOfJailCards()));
            getOutOfJailCards.setFont(Font.font("System", FontWeight.BOLD, fontSize - 3));
        }

        // bezittingen scrollPane
        ScrollPane bezittingen = new ScrollPane();
        bezittingen.setMaxSize(parentWidth - 20, parentHeight / 4);
        bezittingen.setMinSize(parentWidth - 20, parentHeight / 4);
        bezittingen.setStyle("-fx-border-color: black; -fx-border-width: 1");

        VBox bezittingenLView = new VBox();
        bezittingen.setContent(bezittingenLView);

        for (PossessionTile t : speler.getBezittingen()) {
            Label bezitting = new Label(t.getName(), t.createGraphic(15));
            bezitting.setFont(new Font(fontSize));

            bezittingenLView.getChildren().add(bezitting);
        }

        // addAll
        tabContent.getChildren().addAll(speler.getLabel(), balance, position);
        if (speler.isInJail())
            tabContent.getChildren().add(inJail);
        else if (speler.getGetOutOfJailCards() != 0)
            tabContent.getChildren().add(getOutOfJailCards);
        tabContent.getChildren().add(bezittingen);

        return tabContent;
    }
}
