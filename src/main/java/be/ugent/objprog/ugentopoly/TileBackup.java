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

public class TileBackup {

    private HBox tileHBox;
    private VBox tileVBox;
    private ImageView imageView;

    private String id;
    private Text name;
    private String imageName;
    private String colour;

    private int width;
    private int height;
    private boolean or;

    private int amount;
    private int houseCost;
    private int[] rents;

    // 4 corner tiles and chest + chance
    public TileBackup(String id, String imageName, int width, int height, boolean orientation) throws IOException {
        // variables
        this.id = id;
        this.imageName = imageName;

        this.width = width;
        this.height = height;
        or = orientation;

        createTile();
    }

    // tax, railway and utility
    public TileBackup(String id, String imageName, int width, int height, boolean orientation, int amount) throws IOException {
        // variables
        this.id = id;
        this.imageName = imageName;

        this.width = width;
        this.height = height;
        or = orientation;

        this.amount = amount;

        createTile();
    }

    // street tiles
    public TileBackup(String id, String colour, int width, int height, boolean orientation, int cost,  int houseCost, int... rents) throws IOException {
        // variables
        this.id = id;
        this.colour = colour;

        this.width = width;
        this.height = height;
        or = orientation;

        this.houseCost = houseCost;
        this.rents = rents;

        createTile();
    }

    private void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("ugentopoly.deel1.properties"));


        // name text
        this.name = new Text(props.getProperty(this.id));
        this.name.setFont(new Font(5));
        this.name.setTextAlignment(TextAlignment.CENTER);

        // box
        tileHBox = new HBox(this.name);
        tileVBox = new VBox();

        if (or) {
            tileHBox.setPrefSize(this.width, this.height);
            tileHBox.setSpacing(20);
            tileHBox.setAlignment(Pos.CENTER_RIGHT);
            tileHBox.setOnMousePressed(e -> showInfo());
            tileHBox.setOnMouseReleased(e -> mouseReleased());
            tileHBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        } else {
            tileVBox.getChildren().add(this.name);
            tileVBox.setPrefSize(this.width, this.height);
            tileVBox.setSpacing(20);
            tileVBox.setAlignment(Pos.BOTTOM_CENTER);
            tileVBox.setOnMousePressed(e -> showInfo());
            tileVBox.setOnMouseReleased(e -> mouseReleased());
            tileVBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        }

        // image
        if (this.imageName != null) {
            imageView = new ImageView();
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("assets/" + this.imageName + ".png")).toExternalForm()));
            imageView.setFitWidth(Math.max(this.width, this.height) / 5.0);
            imageView.setFitHeight(Math.max(this.width, this.height) / 5.0);

            if (or)
                tileHBox.getChildren().add(imageView);
            else
                tileVBox.getChildren().add(imageView);
        } else if (colour != null){
            tileHBox.getChildren().add(new Stripe(colour, 25, 65 ));

            tileVBox.getChildren().add(new Stripe(colour, 65, 25 ));
        }
    }

    private void showInfo() {
        // display info
        new TileInfo(id);

        // change box look
        tileHBox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
        tileVBox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
    }

    private void mouseReleased() {
        // change box look
        tileHBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        tileVBox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
    }

    public HBox getHBox() {
        return tileHBox;
    }
    public VBox getVBox() {
        return tileVBox;
    }

    public String getName() {
        return name.getText();
    }

    public String getImagePath() {
        return "assets/" + imageName + ".png";
    }
}
