package be.ugent.objprog.ugentopoly;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Tile extends HBox {

    private ImageView imageView;
    private Text name;
    private VBox vbox;

    private String id;
    private String imageName;
    private String colour;

    private int width;
    private int height;

    private int amount;
    private int houseCost;
    private int[] rents;

    // 4 corner tiles and chest + chance
    public Tile(String id, String imageName, int width, int height) throws IOException {
        // variables
        this.id = id;
        this.imageName = imageName;

        this.width = width;
        this.height = height;

        createTile();
    }

    // tax, railway and utility
    public Tile(String id, String imageName, int width, int height, int amount) throws IOException {
        // variables
        this.id = id;
        this.imageName = imageName;

        this.width = width;
        this.height = height;

        this.amount = amount;

        createTile();
    }

    // street tiles
    public Tile(String id, String colour, int width, int height, int cost,  int houseCost, int... rents) throws IOException {
        // variables
        this.id = id;
        this.colour = colour;

        this.width = width;
        this.height = height;

        this.houseCost = houseCost;
        this.rents = rents;

        createTile();
    }

    private void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("ugentopoly.deel1.properties"));


        // name text
        this.name = new Text(props.getProperty(this.id));
        this.name.setFont(new Font(9));
        this.name.setTextAlignment(TextAlignment.CENTER);

        Text nameCopy = new Text(this.name.getText());
        nameCopy.setFont(this.name.getFont());
        nameCopy.setTextAlignment(this.name.getTextAlignment());

        // boxes
        this.getChildren().add(this.name);
        vbox = new VBox(nameCopy);

        setPrefSize(this.width, this.height);
        setMaxSize(this.width, this.height);
        setMinSize(this.width, this.height);
        setSpacing(20);
        setAlignment(Pos.CENTER_RIGHT);
        setOnMousePressed(e -> showInfo());
        setOnMouseReleased(e -> mouseReleased());
        setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");

        vbox.setPrefSize(this.height, this.width);
        vbox.setMaxSize(this.height, this.width);
        vbox.setMinSize(this.height, this.width);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setOnMousePressed(e -> showInfo());
        vbox.setOnMouseReleased(e -> mouseReleased());
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");

        // image
        if (this.imageName != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/" + this.imageName + ".png")).toExternalForm()));
            imageView.setFitWidth(Math.max(this.width, this.height) / 3.0);
            imageView.setFitHeight(Math.max(this.width, this.height) / 3.0);

            ImageView imageViewCopy = new ImageView();
            imageViewCopy.setImage(imageView.getImage());
            imageViewCopy.setFitWidth(imageView.getFitWidth());
            imageViewCopy.setFitHeight(imageView.getFitHeight());

            getChildren().add(imageView);

            vbox.getChildren().add(imageViewCopy);
        } else if (colour != null){
            getChildren().add(new Stripe(colour, 25, 65 ));

            vbox.getChildren().add(new Stripe(colour, 65, 25));
        }
    }

    private void showInfo() {
        // display info
        new TileInfo(id);

        // change box look
        setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
        vbox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
    }

    private void mouseReleased() {
        // change box look
        setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
    }

    public String getName() {
        return name.getText();
    }

    public String getImagePath() {
        return "assets/" + imageName + ".png";
    }

    public VBox getVBox() {
        return vbox;
    }
}
