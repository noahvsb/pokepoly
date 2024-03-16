package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Properties;

public class StreetTile implements Tile {
    private String id;
    private int width;
    private int height;
    private Text name;

    private String colour;
    private int cost;
    private int houseCost;
    private int[] rents;

    public StreetTile(String id, String colour, int width, int height, int cost,  int houseCost, int... rents) throws IOException {
        this.id = id;
        this.colour = colour;

        this.width = width;
        this.height = height;

        this.cost = cost;
        this.houseCost = houseCost;
        this.rents = rents;

        createTile();
    }


    @Override
    public void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("ugentopoly.deel1.properties"));

        // name text
        name = new Text();
        name.setText(props.getProperty(id).replaceAll(" ", "\n"));
        name.setFont(new Font(9));
        name.setTextAlignment(TextAlignment.CENTER);

        Text nameCopy = new Text(name.getText());
        nameCopy.setFont(name.getFont());
        nameCopy.setTextAlignment(name.getTextAlignment());

        // boxes
        hbox.getChildren().add(name);
        vbox.getChildren().add(nameCopy);

        hbox.setPrefSize(width, height);
        hbox.setMaxSize(width, height);
        hbox.setMinSize(width, height);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setOnMousePressed(e -> showInfo());
        hbox.setOnMouseReleased(e -> mouseReleased());
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");

        vbox.setPrefSize(height, width);
        vbox.setMaxSize(height, width);
        vbox.setMinSize(height, width);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setOnMousePressed(e -> showInfo());
        vbox.setOnMouseReleased(e -> mouseReleased());
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");

        // stripe (areaColour)
        hbox.getChildren().add(new Stripe(colour, 25, 65 ));
        hbox.setSpacing(30);

        vbox.getChildren().add(new Stripe(colour, 65, 25));
        vbox.setSpacing(30);
    }

    @Override
    public void showInfo() {
        // display info
        new TileInfo(id);

        // change box look
        hbox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
        vbox.setStyle("-fx-border-color: lightblue; -fx-border-width: 1.5; -fx-background-color: white");
    }

    @Override
    public void mouseReleased() {
        // change box look
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1.5; -fx-background-color: white");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name.getText();
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public HBox getHbox() {
        return hbox;
    }

    @Override
    public VBox getVBox() {
        return vbox;
    }
}
