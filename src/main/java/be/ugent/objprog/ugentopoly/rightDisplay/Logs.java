package be.ugent.objprog.ugentopoly.rightDisplay;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Logs extends ScrollPane {
    private ListView<Text> lView;
    private double fontSize;

    public Logs(RightDisplay parent, double fontSize) {
        // variables
        this.fontSize = fontSize;

        // configurations
        setMinSize(parent.getMinWidth() - 20, parent.getMinHeight() / 5);
        setMaxSize(parent.getMinWidth() - 20, parent.getMinHeight() / 5);
        setStyle("-fx-border-color: black; -fx-border-width: 1");

        lView = new ListView<>();
        lView.setEditable(false);
        lView.setMaxSize(parent.getMinWidth() - 24, parent.getMinHeight());
        lView.setPrefSize(parent.getMinWidth() - 24, parent.getMinHeight() / 5 - 4);
        lView.setMinSize(parent.getMinWidth() - 24, 0);

        vvalueProperty().bind(lView.heightProperty());

        setContent(lView);
    }

    public void add(String str) {
        Text txt = new Text(str);
        txt.setFont(new Font(fontSize));

        lView.getItems().add(txt);
    }
}
