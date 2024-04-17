package be.ugent.objprog.ugentopoly.addSpeler;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StartSpel;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.List;

public class AddSpeler extends VBox {

    private StartSpel startSpel;

    private String name;
    private ImageView icon;
    private String colour;
    private int iconIndex;
    private int colourIndex;

    private TextField nameField;
    private ComboBox<String> pawnComboBox;
    private ComboBox<String> colourComboBox;
    private Button voegToeButton;

    private static final StringConverter<IconAndString> CONVERTER = new IconConverter();

    public AddSpeler(int width, int height, StartSpel startSpel, List<Integer> usedIconIndexes, List<Integer> usedColourIndexes) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        setStyle("-fx-background-color: lightgray; -fx-border-color: white");

        this.startSpel = startSpel;

        // name
        Text name = new Text("Naam:");
        name.setFont(new Font(20));

        nameField = new TextField();
        nameField.setFont(new Font(20));
        nameField.setOnKeyTyped(e -> nameFieldUsed());

        HBox nameBox = new HBox(20, name, nameField);
        nameBox.setAlignment(Pos.CENTER);

        // pawn
        Text pawn = new Text("Pion:");
        pawn.setFont(new Font(20));

        pawnComboBox = new PawnComboBox(usedIconIndexes);
        pawnComboBox.setOnAction(e -> pawnComboBoxUsed());

        HBox pawnBox = new HBox(20, pawn, pawnComboBox);
        pawnBox.setAlignment(Pos.CENTER);

        // colour
        Text colour = new Text("Kleur:");
        colour.setFont(new Font(20));

        colourComboBox = new ColourComboBox(usedColourIndexes);
        colourComboBox.setOnAction(e -> colourComboBoxUsed());

        HBox colourBox = new HBox(20, colour, colourComboBox);
        colourBox.setAlignment(Pos.CENTER);

        // buttons
        voegToeButton = new Button("Voeg toe");
        voegToeButton.setFont(new Font(20));
        voegToeButton.setOnAction(e -> startSpel.addSpeler(new Speler(this.name, this.icon, this.colour, this.iconIndex, this.colourIndex), false));
        voegToeButton.setDisable(true);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setFont(new Font(20));
        cancelButton.setOnAction(e -> startSpel.addSpeler(null, true));

        HBox buttons = new HBox(20, cancelButton, voegToeButton);
        buttons.setAlignment(Pos.CENTER);

        // put them together
        setSpacing(30);
        setAlignment(Pos.CENTER);
        getChildren().addAll(nameBox, pawnBox, colourBox, buttons);
    }

    public void nameFieldUsed() {
        name = nameField.getText();

        voegToeButton.setDisable(name.isEmpty() || icon == null || colour == null);
    }

    public void pawnComboBoxUsed() {
        iconIndex = pawnComboBox.getSelectionModel().getSelectedIndex();
        icon = CONVERTER.fromString(pawnComboBox.getSelectionModel().getSelectedItem()).icon();

        voegToeButton.setDisable(name == null || name.isEmpty() || colour == null);
    }

    public void colourComboBoxUsed() {
        colourIndex = colourComboBox.getSelectionModel().getSelectedIndex();
        colour = colourComboBox.getSelectionModel().getSelectedItem();

        voegToeButton.setDisable(icon == null || name == null || name.isEmpty());
    }
}
