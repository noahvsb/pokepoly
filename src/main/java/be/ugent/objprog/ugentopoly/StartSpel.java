package be.ugent.objprog.ugentopoly;

import be.ugent.objprog.ugentopoly.addSpeler.AddSpeler;
import be.ugent.objprog.ugentopoly.tiles.Tile;
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
    private LogsAndRollHandler logsAndRollHandler;
    private StageController stageController;

    private Speler[] spelersArr;
    private List<Integer> usedIconIndexes;

    private VBox spelersVBox;
    private Button addSpelerButton;
    private Button startSpelButton;

    private Scene addSpelerScene;
    private Stage addSpelerStage;

    public StartSpel(int width, int height, Bord bord, LogsAndRollHandler logsAndRollHandler, StageController stageController) {
        this.bord = bord;
        this.logsAndRollHandler = logsAndRollHandler;
        this.stageController = stageController;

        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        // spelersArr and -VBox
        spelersArr = new Speler[4];
        usedIconIndexes = new ArrayList<>();

        spelersVBox = new VBox(20, new Label("<Speler 1>"), new Label("<Speler 2>"), new Label("<Speler 3>"), new Label("<Speler 4>"));
        for (Node label : spelersVBox.getChildren())
            ((Label) label).setFont(new Font(20));
        spelersVBox.setAlignment(Pos.CENTER);

        // addSpelersStage
        addSpelerScene = new Scene(new AddSpeler(400, 300, this, usedIconIndexes), 400, 300);

        addSpelerStage = new Stage();
        addSpelerStage.setTitle("Speler toevoegen");
        addSpelerStage.setScene(addSpelerScene);
        addSpelerStage.initStyle(StageStyle.UTILITY);

        stageController.addStages(addSpelerStage);

        // buttons
        addSpelerButton = new Button("Speler toevoegen");
        addSpelerButton.setOnAction(e -> addSpelerStage.show());
        addSpelerButton.setFont(new Font(20));

        startSpelButton = new Button("Start spel");
        startSpelButton.setOnAction(e -> startSpel());
        startSpelButton.setFont(new Font(20));
        startSpelButton.setDisable(true);

        HBox buttons = new HBox(100, addSpelerButton, startSpelButton);
        buttons.setAlignment(Pos.CENTER);

        getChildren().addAll(spelersVBox, buttons);
        setSpacing(75);
        setAlignment(Pos.CENTER);
    }

    public void startSpel() {
        Tile startTile = bord.getTiles()[0];

        for (Speler speler : spelersArr)
            if (speler != null) {
                speler.setBalance(bord.getStartBalance());
                speler.setPos(0);
                startTile.getPlayerBox().getChildren().add(speler.getIcon());
            }

        logsAndRollHandler.setSpelers(spelersArr);

        stageController.closeStage("Start spel");
    }

    public void addSpeler(Speler speler, boolean cancel) {
        if (!cancel) {
            usedIconIndexes.add(speler.getIconIndex());

            int i = 0;
            boolean stop = false;
            while (i < spelersArr.length && !stop) {
                if (spelersArr[i] == null) {
                    spelersArr[i] = speler;

                    spelersVBox.getChildren().set(i, speler.getLabel());
                    ((Label) spelersVBox.getChildren().get(i)).setFont(new Font(20));

                    stop = true;
                }
                i++;
            }

            if (i == 2) {
                startSpelButton.setDisable(false);
            }

            if (i == 4) {
                addSpelerButton.setDisable(true);
            }
        }

        addSpelerStage.close();
        addSpelerScene.setRoot(new AddSpeler(400, 300, this, usedIconIndexes));
    }

}
