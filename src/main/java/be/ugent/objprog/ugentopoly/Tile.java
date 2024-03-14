package be.ugent.objprog.ugentopoly;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class Tile {

    private Button button;
    private ImageView imageView;

    private Text tileName;
    private String imageName;

    private int width;
    private int height;

    public Tile(String tileName, String imageName, int width, int height) {
        this.tileName = new Text(tileName);
        this.tileName.setFont(new Font(11));

        this.imageName = imageName;
        this.width = width;
        this.height = height;

        if (this.imageName != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/" + this.imageName + ".png")).toExternalForm()));
            imageView.setFitWidth(this.width / 3);
            imageView.setFitHeight(this.height / 3);

            button = new Button(this.tileName.getText(), imageView);
        } else {
            button = new Button(this.tileName.getText());
        }

        button.setAlignment(Pos.CENTER);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        button.setRotate(0);
        button.setPrefSize(this.width, this.height);
    }

    public Button getButton() {
        return button;
    }

    public String getName() {
        return tileName.getText();
    }

    public String getImagePath() {
        return "assets/" + imageName + ".png";
    }
}
