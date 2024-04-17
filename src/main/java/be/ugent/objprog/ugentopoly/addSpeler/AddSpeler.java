package be.ugent.objprog.ugentopoly.addSpeler;

import be.ugent.objprog.ugentopoly.Speler;
import be.ugent.objprog.ugentopoly.StartSpel;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.List;

public class AddSpeler extends VBox {

    private StartSpel startSpel;

    private String name;
    private ImageView icon;
    private int iconIndex;

    private TextField nameField;
    private ComboBox<String> pawnComboBox;
    private Button voegToeButton;

    private static final StringConverter<IconAndString> CONVERTER = new IconConverter();

    public AddSpeler(int width, int height, StartSpel startSpel, List<Integer> usedIconIndexes) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        this.startSpel = startSpel;

        // name
        Text name = new Text("Naam: ");
        name.setFont(new Font(20));

        nameField = new TextField();
        nameField.setFont(new Font(20));
        nameField.setOnKeyTyped(e -> nameFieldUsed());

        HBox nameBox = new HBox(20, name, nameField);
        nameBox.setAlignment(Pos.CENTER);

        // pawn
        Text pawn = new Text("Pion: ");
        pawn.setFont(new Font(20));

        pawnComboBox = new PawnComboBox(usedIconIndexes);
        pawnComboBox.setOnAction(e -> comboBoxUsed());

        HBox pawnBox = new HBox(20, pawn, pawnComboBox);
        pawnBox.setAlignment(Pos.CENTER);

        // buttons
        voegToeButton = new Button("Voeg toe");
        voegToeButton.setFont(new Font(20));
        voegToeButton.setOnAction(e -> startSpel.addSpeler(new Speler(this.name, this.icon, this.iconIndex), false));
        voegToeButton.setDisable(true);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setFont(new Font(20));
        cancelButton.setOnAction(e -> startSpel.addSpeler(null, true));

        HBox buttons = new HBox(20, cancelButton, voegToeButton);
        buttons.setAlignment(Pos.CENTER);

        // put them together
        setSpacing(35);
        setAlignment(Pos.CENTER);
        getChildren().addAll(nameBox, pawnBox, buttons);
    }

    public void nameFieldUsed() {
        name = nameField.getText();

        voegToeButton.setDisable(icon == null || name.isEmpty());
    }

    public void comboBoxUsed() {
        iconIndex = pawnComboBox.getSelectionModel().getSelectedIndex();
        icon = CONVERTER.fromString(pawnComboBox.getSelectionModel().getSelectedItem()).icon();

        voegToeButton.setDisable(name == null || name.isEmpty());
    }
}
