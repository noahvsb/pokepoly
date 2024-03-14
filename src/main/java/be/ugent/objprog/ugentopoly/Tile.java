package be.ugent.objprog.ugentopoly;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class Tile {

    private HBox tileHBox;
    private VBox tileVBox;
    private Button button;
    private ImageView imageView;

    private Text tileName;
    private String imageName;

    private int width;
    private int height;

    public Tile(String tileName, String imageName, int width, int height) {
        this.tileName = new Text(tileName);
        this.tileName.setFont(new Font(11));
        this.tileName.setTextAlignment(TextAlignment.CENTER);

        this.imageName = imageName;
        this.width = width;
        this.height = height;

        tileHBox = new HBox(this.tileName);
        tileHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        tileHBox.setRotate(0);
        tileHBox.setPrefSize(this.width, this.height);
        tileHBox.setAlignment(Pos.CENTER);
        tileHBox.setSpacing(5);
        tileHBox.setOnMouseClicked(e -> showInfo());
        tileHBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: lightgreen");

        tileVBox = new VBox(this.tileName);
        tileVBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        tileVBox.setRotate(0);
        tileVBox.setPrefSize(this.width, this.height);
        tileVBox.setAlignment(Pos.CENTER);
        tileVBox.setSpacing(5);
        tileVBox.setOnMouseClicked(e -> showInfo());
        tileVBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: lightgreen");



        if (this.imageName != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/" + this.imageName + ".png")).toExternalForm()));
            imageView.setFitWidth(this.width / 3);
            imageView.setFitHeight(this.height / 3);

            tileHBox.getChildren().add(imageView);

            tileVBox.getChildren().add(imageView);

            button = new Button(this.tileName.getText(), imageView);
        } else {
            button = new Button(this.tileName.getText());
        }

        button.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        button.setRotate(0);
        button.setPrefSize(this.width, this.height);


    }

    private void showInfo() {
        System.out.println("info");
    }

    public HBox getHBox() {
        return tileHBox;
    }
    public VBox getVBox() {
        return tileVBox;
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
