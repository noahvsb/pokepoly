package be.ugent.objprog.pokepoly.startspel;

import be.ugent.objprog.pokepoly.Speler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.List;

public class AddSpeler extends VBox {
    private String name;
    private ImageView icon;
    private String colour;
    private int iconIndex;
    private int colourIndex;

    private TextField nameField;

    private ComboBox<String> pawnComboBox;
    private HBox pawnBox;

    private ComboBox<String> colourComboBox;
    private HBox colourBox;

    private Button voegToeButton;

    private final StringConverter<IconAndString> CONVERTER = new IconConverter();

    public AddSpeler(int width, int height, StartSpel startSpel, List<Integer> usedIconIndexes, List<Integer> usedColourIndexes) {
        // configurations
        setMinSize(width, height);
        setMaxSize(width, height);
        setStyle("-fx-background-color: #2e68b3; -fx-border-color: white");
        setSpacing(30);
        setAlignment(Pos.CENTER);

        // name
        Text name = new Text("Name:");
        name.setFont(new Font(20));

        nameField = new TextField();
        nameField.setFont(new Font(20));
        nameField.setOnKeyTyped(e -> nameFieldUsed());

        HBox nameBox = new HBox(20, name, nameField);
        nameBox.setAlignment(Pos.CENTER);

        // pawn
        Text pawn = new Text("Eeveelution:");
        pawn.setFont(new Font(20));

        pawnComboBox = new PawnComboBox(usedIconIndexes);
        pawnComboBox.setOnAction(e -> pawnComboBoxUsed());

        pawnBox = new HBox(20, pawn, pawnComboBox, new Circle(10, Color.web("white")));
        pawnBox.setAlignment(Pos.CENTER);

        // colour
        Text colour = new Text("Colour:");
        colour.setFont(new Font(20));

        colourComboBox = new ColourComboBox(usedColourIndexes);
        colourComboBox.setOnAction(e -> colourComboBoxUsed());

        colourBox = new HBox(20, colour, colourComboBox, new Circle(10, Color.web("white")));
        colourBox.setAlignment(Pos.CENTER);

        // buttons
        voegToeButton = new Button("Add");
        voegToeButton.setFont(new Font(20));
        voegToeButton.setOnAction(e -> startSpel.addSpeler(new Speler(this.name, this.icon, this.colour, this.iconIndex, this.colourIndex)));
        voegToeButton.setDisable(true);

        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font(20));
        cancelButton.setOnAction(e -> startSpel.cancelAddSpeler());

        HBox buttons = new HBox(20, cancelButton, voegToeButton);
        buttons.setAlignment(Pos.CENTER);

        // add all
        getChildren().addAll(nameBox, pawnBox, colourBox, buttons);
    }

    public void nameFieldUsed() {
        name = nameField.getText();

        voegToeButton.setDisable(name.isEmpty() || icon == null || colour == null);
    }

    public void pawnComboBoxUsed() {
        iconIndex = pawnComboBox.getSelectionModel().getSelectedIndex();
        icon = CONVERTER.fromString(pawnComboBox.getSelectionModel().getSelectedItem()).icon();

        ImageView pawnImage = new ImageView(icon.getImage());
        pawnImage.setFitWidth(20); pawnImage.setFitHeight(20);
        pawnImage.setPreserveRatio(true);

        pawnBox.getChildren().set(2, pawnImage);

        voegToeButton.setDisable(name == null || name.isEmpty() || colour == null);
    }

    public void colourComboBoxUsed() {
        colourIndex = colourComboBox.getSelectionModel().getSelectedIndex();
        colour = colourComboBox.getSelectionModel().getSelectedItem();

        colourBox.getChildren().set(2, new Circle(10, Color.web(colour)));

        voegToeButton.setDisable(icon == null || name == null || name.isEmpty());
    }
}
