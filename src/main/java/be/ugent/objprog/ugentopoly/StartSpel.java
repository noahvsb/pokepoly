package be.ugent.objprog.ugentopoly;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartSpel extends VBox {

    private Speler[] spelersArr;

    private VBox spelersVBox;
    private Button addSpelerButton;
    private Button startSpelButton;

    private Scene addSpelerScene;
    private Stage addSpelerStage;

    public StartSpel(int width, int height) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        spelersArr = new Speler[4];

        spelersVBox = new VBox(20, new Label("<Speler 1>"), new Label("<Speler 2>"), new Label("<Speler 3>"), new Label("<Speler 4>"));
        for (Node label : spelersVBox.getChildren())
            ((Label) label).setFont(new Font(20));

        addSpelerScene = new Scene(new AddSpeler(400, 300, this), 400, 300);

        addSpelerStage = new Stage();
        addSpelerStage.setTitle("Speler toevoegen");
        addSpelerStage.setScene(addSpelerScene);

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
    }

    public void startSpel() {
        // TODO
    }

    public void addSpeler(Speler speler) {
        addSpelerStage.close();
        addSpelerScene.setRoot(new AddSpeler(400, 300, this));

        int i = 0;
        boolean stop = false;
        while (i < spelersArr.length && !stop) {
            if (spelersArr[i] == null) {
                spelersArr[i] = speler;

                spelersVBox.getChildren().set(i, speler.getLabel());

                stop = true;
            }
            i++;
        }

        if (i == 1) {
            startSpelButton.setDisable(false);
        }

        if (i == 4) {
            addSpelerButton.setDisable(true);
        }
    }

}
