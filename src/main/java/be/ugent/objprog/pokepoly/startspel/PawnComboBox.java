package be.ugent.objprog.pokepoly.startspel;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.util.List;

public class PawnComboBox extends ComboBox<String> {
    private static final StringConverter<IconAndString> CONVERTER = new IconConverter();

    public PawnComboBox(List<Integer> usedIconIndexes) {
        setCellFactory(item -> new PawnListCell());
        getItems().addAll("Vaporeon", "Jolteon", "Flareon","Espeon", "Umbreon", "Leafon", "Glaceon", "Sylveon");
        for (int i : usedIconIndexes)
            getItems().remove(i);
    }

    public static class PawnListCell extends ListCell<String> {

        public PawnListCell() {
            setContentDisplay(ContentDisplay.LEFT);
        }

        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            }
            else {
                ImageView graphic = CONVERTER.fromString(item).icon();
                graphic.setFitWidth(20);
                graphic.setFitHeight(20);
                graphic.setPreserveRatio(true);

                setGraphic(graphic);
                setText(item);
            }
        }

    }
}
