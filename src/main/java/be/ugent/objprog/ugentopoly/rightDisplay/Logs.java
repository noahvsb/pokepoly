package be.ugent.objprog.ugentopoly.rightDisplay;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Logs extends ScrollPane {
    private VBox box;
    private double fontSize;

    public Logs(RightDisplay parent, double fontSize) {
        // variables
        this.fontSize = fontSize;

        // configurations
        setMinSize(parent.getMinWidth() - 20, parent.getMinHeight() / 5);
        setMaxSize(parent.getMinWidth() - 20, parent.getMinHeight() / 5);
        setStyle("-fx-border-color: black; -fx-border-width: 1");

        box = new VBox(5);
        vvalueProperty().bind(box.heightProperty());

        setContent(box);
    }

    public void add(String str) {
        Text txt = new Text(str);
        txt.setFont(new Font(fontSize));

        box.getChildren().add(txt);
    }
}
