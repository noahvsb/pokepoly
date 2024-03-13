package be.ugent.objprog.ugentopoly;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class Tile {

    private Button button;
    private ImageView imageView;

    private String name;
    private String image;

    private int width;
    private int height;

    public Tile(String tileName, String imageName, int width, int height) {
        name = tileName;
        image = imageName;
        this.width = width;
        this.height = height;

        if (image != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResource("assets/" + image + ".png").toExternalForm()));
            imageView.setFitWidth(this.width - 50);
            imageView.setFitHeight(this.height - 50);

            button = new Button(name, imageView);
        } else {
            button = new Button(name);
        }

        button.setPrefSize(this.width, this.height);
        button.setFont(new Font(15));
    }

    public Button getButton() {
        return button;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return "assets/" + image + ".png";
    }
}
