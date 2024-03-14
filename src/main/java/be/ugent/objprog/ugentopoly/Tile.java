package be.ugent.objprog.ugentopoly;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private ImageView imageView;
    private Stripe stripe;

    private Text tileName;
    private String imageName;

    private int width;
    private int height;
    private boolean or;

    public Tile(String name, String imageName, String colour, int width, int height, boolean orientation) {
        // variables
        tileName = new Text(name);
        tileName.setFont(new Font(11));
        tileName.setTextAlignment(TextAlignment.CENTER);

        this.imageName = imageName;
        this.width = width;
        this.height = height;
        or = orientation;

        // box
        if (or) {
            tileHBox = new HBox(tileName);
            tileHBox.setPrefSize(this.width, this.height);
            tileHBox.setAlignment(Pos.CENTER);
            tileHBox.setSpacing(5);
            tileHBox.setOnMousePressed(e -> showInfo());
            tileHBox.setOnMouseReleased(e -> clickedAgain());
            tileHBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        } else {
            tileVBox = new VBox(tileName);
            tileVBox.setPrefSize(this.width, this.height);
            tileVBox.setAlignment(Pos.CENTER);
            tileVBox.setSpacing(5);
            tileVBox.setOnMousePressed(e -> showInfo());
            tileVBox.setOnMouseReleased(e -> clickedAgain());
            tileVBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        }

        // image
        if (this.imageName != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/" + this.imageName + ".png")).toExternalForm()));
            imageView.setFitWidth(this.width / 3);
            imageView.setFitHeight(this.height / 3);

            if (or)
                tileHBox.getChildren().add(imageView);
            else
                tileVBox.getChildren().add(imageView);
        } else if (colour != null){
            if (or) {
                stripe = new Stripe(colour, 25, 65 );
                tileHBox.getChildren().add(stripe);
            }
            else {
                stripe = new Stripe(colour, 65, 25 );
                tileVBox.getChildren().add(stripe);
            }
        }



    }

    private void showInfo() {
        // display info
        System.out.println("info");

        // change box look
        if (or)
            tileHBox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
        else
            tileVBox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
    }

    private void clickedAgain() {
        System.out.println("test");
        // change box look
        if (or)
            tileHBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        else
            tileVBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
    }

    public HBox getHBox() {
        return tileHBox;
    }
    public VBox getVBox() {
        return tileVBox;
    }

    public String getName() {
        return tileName.getText();
    }

    public String getImagePath() {
        return "assets/" + imageName + ".png";
    }
}
