package be.ugent.objprog.pokepoly.startspel;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class ColourComboBox extends ComboBox<String> {

    public ColourComboBox(List<Integer> usedColours) {
        setCellFactory(item -> new ColourListCell());
        getItems().addAll("red", "yellow", "green", "blue");
        for (int i : usedColours)
            getItems().remove(i);
    }

    public static class ColourListCell extends ListCell<String> {

        public ColourListCell() {
            setContentDisplay(ContentDisplay.LEFT);
        }

        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            }
            else {
                Circle graphic = new Circle(10, Color.web(item));

                setGraphic(graphic);
                setText(item);
            }
        }

    }
}
