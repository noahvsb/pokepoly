package be.ugent.objprog.ugentopoly;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AddSpeler extends VBox {

    private StartSpel startSpel;

    private String name;
    private String icon;

    private TextField nameField;
    private ComboBox<String> pawnComboBox;
    private Button button;

    public AddSpeler(int width, int height, StartSpel startSpel) {
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        this.startSpel = startSpel;

        // name
        Text name = new Text("Naam: ");
        name.setFont(new Font(20));

        nameField = new TextField();
        nameField.setFont(new Font(20));
        nameField.setOnAction(e -> nameFieldUsed());

        HBox nameBox = new HBox(20, name, nameField);
        nameBox.setAlignment(Pos.CENTER);

        // pawn
        Text pawn = new Text("Pion: ");
        pawn.setFont(new Font(20));

        pawnComboBox = new ComboBox<>();
        pawnComboBox.getItems().addAll("Wina", "VTK", "Chemica", "Filologica", "geologica", "VBK", "VEK", "VLK");
        pawnComboBox.setOnAction(e -> comboBoxUsed());

        HBox pawnBox = new HBox(100, pawn, pawnComboBox);
        pawnBox.setAlignment(Pos.CENTER);

        // button
        button = new Button("Voeg toe");
        button.setFont(new Font(20));
        button.setOnAction(e -> addSpeler());
        button.setDisable(true);

        // all
        setSpacing(35);
        setAlignment(Pos.CENTER);
        getChildren().addAll(nameBox, pawnBox, button);
    }

    public void addSpeler() {
        startSpel.addSpeler(new Speler(name, icon));
    }

    public void nameFieldUsed() {
        name = nameField.getText();

        if (icon != null)
            button.setDisable(false);
    }

    public void comboBoxUsed() {
        icon = "token" + (pawnComboBox.getSelectionModel().getSelectedIndex() + 1);

        if (name != null)
            button.setDisable(false);
    }
}
