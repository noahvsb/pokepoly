package be.ugent.objprog.ugentopoly.tiles;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class Tile {

    // final variables
    protected static final double FONT_SIZE = 11;

    protected static final int N = 65;

    protected static final double BORDER_WIDTH = 1;

    protected static final String NORMAL_STYLE = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: white";
    protected static final String HIGHLIGHT_STYLE = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: #E3F1FF";

    // other variables
    protected String id;
    protected String nameStr;
    protected String imageName;
    protected int width;
    protected int height;

    protected HBox hbox;
    protected VBox vbox;

    protected InfoTile infoTile;

    protected boolean mouseToggle;

    public void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/be/ugent/objprog/ugentopoly/ugentopoly.deel1.properties"));

        // name text
        nameStr = props.getProperty(id);

        // boxes
        hbox = new HBox(createName());
        vbox = new VBox(createName());

        hbox.setPrefSize(width, height);
        hbox.setMaxSize(width, height);
        hbox.setMinSize(width, height);
        hbox.setSpacing(12);
        hbox.setAlignment(Pos.CENTER);
        hbox.setOnMouseClicked(e -> tilePressed());
        hbox.setStyle(NORMAL_STYLE);

        vbox.setPrefSize(height, width);
        vbox.setMaxSize(height, width);
        vbox.setMinSize(height, width);
        vbox.setSpacing(12);
        vbox.setAlignment(Pos.CENTER);
        vbox.setOnMouseClicked(e -> tilePressed());
        vbox.setStyle(NORMAL_STYLE);

        // graphic
        hbox.getChildren().add(createGraphic(true));

        vbox.getChildren().add(createGraphic(false));
    }

    public Text createName() {
        Text name = new Text();
        name.setText(nameStr);
        name.setWrappingWidth(60);
        name.setFont(new Font(FONT_SIZE));
        name.setTextAlignment(TextAlignment.CENTER);

        // aangezien er hier maar 2 uitzondering zijn, vond ik het nog doenbaar voor deze cheese
        if (name.getText().equals("Hoveniersberg"))
            name.setText("Hoveniers-\nberg");
        if (name.getText().equals("Boekentoren"))
            name.setText("Boeken-\ntoren");

        return name;
    }

    public Node createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 3.0);
        imageView.setFitHeight(Math.max(width, height) / 3.0);

        return imageView;
    }

    public void tilePressed() {
        Tile currentActive = infoTile.getCurrentActive();

        // set active
        if (mouseToggle) {
            if (currentActive != null) {
                currentActive.getHBox().setStyle(NORMAL_STYLE);
                currentActive.getVBox().setStyle(NORMAL_STYLE);

                currentActive.changeMouseToggle();

                infoTile.reset();
            }

            // display info
            setupInfoTile();

            // change box look
            hbox.setStyle(HIGHLIGHT_STYLE);
            vbox.setStyle(HIGHLIGHT_STYLE);

            // change mouseToggle
            mouseToggle = !mouseToggle;
        }
        // set inactive
        else {
            // reset infoTile
            infoTile.reset();

            // change box look
            hbox.setStyle(NORMAL_STYLE);
            vbox.setStyle(NORMAL_STYLE);

            // change mouse toggle
            mouseToggle = !mouseToggle;
        }
    }

    public abstract void setupInfoTile();

    public void changeMouseToggle() {
        mouseToggle = !mouseToggle;
    }

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return "/be/ugent/objprog/ugentopoly/assets/" + imageName + ".png";
    }

    public HBox getHBox() {
        return hbox;
    }

    public VBox getVBox() {
        return vbox;
    }
}