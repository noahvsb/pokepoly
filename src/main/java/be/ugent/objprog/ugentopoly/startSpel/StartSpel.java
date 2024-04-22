package be.ugent.objprog.ugentopoly.startSpel;

import be.ugent.objprog.ugentopoly.Bord;
import be.ugent.objprog.ugentopoly.rightDisplay.RightDisplay;
import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StageController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class StartSpel extends VBox {

    private Bord bord;
    private RightDisplay rightDisplay;
    private StageController stageController;

    private Speler[] spelersArr;
    private List<Integer> usedIconIndexes;
    private List<Integer> usedColourIndexes;

    private VBox spelersVBox;
    private Button addSpelerButton;
    private Button startSpelButton;

    private Scene addSpelerScene;
    private Stage addSpelerStage;

    public StartSpel(int width, int height, Bord bord, RightDisplay rightDisplay, StageController stageController) {
        // variables
        this.bord = bord;
        this.rightDisplay = rightDisplay;
        this.stageController = stageController;

        // configurations
        setMinSize(width, height);
        setMaxSize(width, height);
        setStyle("-fx-background-color: lightblue; -fx-border-color: white");
        setSpacing(75);
        setAlignment(Pos.CENTER);

        // already used indexes
        usedIconIndexes = new ArrayList<>();
        usedColourIndexes = new ArrayList<>();

        // spelersArr and -VBox
        spelersArr = new Speler[4];

        spelersVBox = new VBox(20, new Label("<Speler 1>"), new Label("<Speler 2>"), new Label("<Speler 3>"), new Label("<Speler 4>"));
        for (Node label : spelersVBox.getChildren())
            ((Label) label).setFont(new Font(20));
        spelersVBox.setAlignment(Pos.CENTER);

        // addSpelersStage
        addSpelerScene = new Scene(new AddSpeler(400, 250, this, usedIconIndexes, usedColourIndexes), 400, 250);

        addSpelerStage = new Stage();
        addSpelerStage.setTitle("Speler toevoegen");
        addSpelerStage.setScene(addSpelerScene);
        addSpelerStage.initStyle(StageStyle.UTILITY);

        stageController.addStages(addSpelerStage);

        // buttons
        addSpelerButton = new Button("Speler toevoegen");
        addSpelerButton.setOnAction(e -> {addSpelerStage.close();addSpelerStage.show();});
        addSpelerButton.setFont(new Font(20));

        startSpelButton = new Button("Start spel");
        startSpelButton.setOnAction(e -> start());
        startSpelButton.setFont(new Font(20));
        startSpelButton.setDisable(true);

        HBox buttons = new HBox(100, addSpelerButton, startSpelButton);
        buttons.setAlignment(Pos.CENTER);

        // add all
        getChildren().addAll(spelersVBox, buttons);
    }

    public void start() {
        for (Speler speler : spelersArr)
            if (speler != null) {
                speler.setBalance(bord.getStartBalance());
                speler.setPos(0);
                bord.setPos(speler, 0);
            }

        rightDisplay.setSpelers(spelersArr);

        stageController.closeStage("Start spel");
    }

    public void addSpeler(Speler speler) {
        usedIconIndexes.add(speler.getIconIndex());
        usedColourIndexes.add(speler.getColourIndex());

        int i = 0;
        boolean stop = false;
        while (i < spelersArr.length && !stop) {
            if (spelersArr[i] == null) {
                spelersArr[i] = speler;

                spelersVBox.getChildren().set(i, speler.getLabel(20));

                stop = true;
            }
            i++;
        }

        startSpelButton.setDisable(i == 1);
        addSpelerButton.setDisable(i == 4);

        addSpelerStage.close();
        addSpelerScene.setRoot(new AddSpeler(400, 300, this, usedIconIndexes, usedColourIndexes));
    }

    public void cancelAddSpeler() {
        addSpelerStage.close();
        addSpelerScene.setRoot(new AddSpeler(400, 300, this, usedIconIndexes, usedColourIndexes));
    }

}
