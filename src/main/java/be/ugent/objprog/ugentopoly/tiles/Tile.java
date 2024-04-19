package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Speler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    protected static final int N = 65; // used for WIDTH and HEIGHT
    protected static final double BORDER_WIDTH = 1;

    // other variables
    protected String id;
    protected String name;
    protected String imageName;
    protected double width;
    protected double height;

    protected HBox hbox;
    protected VBox vbox;
    protected HBox playerBox;
    protected String normalStyle = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: white";
    protected String highlightStyle = "-fx-border-color: black; -fx-border-width: " + BORDER_WIDTH + "; -fx-background-color: lightgray";

    protected InfoTile infoTile;
    protected boolean mouseToggle; // for showing or closing the tile

    public void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/be/ugent/objprog/ugentopoly/ugentopoly.properties"));

        // name text
        name = props.getProperty(id);

        // boxes
        hbox = new HBox(12, createName());
        vbox = new VBox(12, createName());
        playerBox = new HBox(-20); // negative spacing, because there is not enough space to display all 4 icons on one tile

        hbox.setMaxSize(width, height); hbox.setMinSize(width, height);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle(normalStyle);

        vbox.setMaxSize(height, width); vbox.setMinSize(height, width);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle(normalStyle);

        playerBox.setAlignment(Pos.CENTER);
        playerBox.setOnMouseClicked(e -> tilePressed());

        // graphic
        hbox.getChildren().add(createGraphic(true));

        vbox.getChildren().add(createGraphic(false));
    }

    public Text createName() {
        Text name = new Text(this.name);
        name.setWrappingWidth(60);
        name.setFont(new Font(FONT_SIZE));
        name.setTextAlignment(TextAlignment.CENTER);

        // ik ben niet content genoeg met de setWrappingWidth oplossing voor deze 2 gevallen,
        // de woorden worden niet volgens de norm gesplitst
        if (name.getText().equals("Hoveniersberg"))
            name.setText("Hoveniers-\nberg");
        else if (name.getText().equals("Boekentoren"))
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

    public Node createGraphic(double height) {
        ImageView imageView = (ImageView) createGraphic(true);
        imageView.setFitWidth(height);
        imageView.setFitHeight(height);

        return imageView;
    }

    public void tilePressed() {
        // set active
        if (mouseToggle) {
            // if info of another tile is currently being displayed, reset it
            Tile currentActive = infoTile.getCurrentActive();
            if (currentActive != null) {
                currentActive.getHBox().setStyle(currentActive.getNormalStyle());
                currentActive.getVBox().setStyle(currentActive.getNormalStyle());

                currentActive.changeMouseToggle();

                infoTile.reset();
            }
            // change box look
            hbox.setStyle(highlightStyle);
            vbox.setStyle(highlightStyle);

            // display info
            setupInfoTile();
        }
        // set inactive
        else {
            // change box look
            hbox.setStyle(normalStyle);
            vbox.setStyle(normalStyle);

            // reset infoTile
            infoTile.reset();
        }
        // change mouse toggle
        mouseToggle = !mouseToggle;
    }

    public abstract void setupInfoTile();

    public void changeMouseToggle() {
        mouseToggle = !mouseToggle;
    }

    public void handleTileAction(Speler speler, Speler[] spelers) {
        Alert alert = new Alert(getAlertType(speler));
        alert.setTitle(alert.getAlertType().equals(Alert.AlertType.CONFIRMATION) ? "Aankoop" : "Melding");
        alert.setHeaderText(getAlertDescription(speler));

        // show infoTile
        mouseToggle = true;
        tilePressed();

        alert.showAndWait().ifPresent(response -> {
            tilePressed();
            if (response == ButtonType.OK)
                responseWasOk(speler, spelers);
        });
    }

    public abstract Alert.AlertType getAlertType(Speler speler);

    public abstract String getAlertDescription(Speler speler);

    public abstract void responseWasOk(Speler speler, Speler[] spelers);

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImagePath() {
        return "/be/ugent/objprog/ugentopoly/assets/" + imageName + ".png";
    }
    public String getNormalStyle() {
        return normalStyle;
    }

    public StackPane getTileWithHBox() {
        return new StackPane(hbox, playerBox);
    }
    public HBox getHBox() {
        return hbox;
    }
    public StackPane getTileWithVBox() {
        return new StackPane(vbox, playerBox);
    }
    public VBox getVBox() {
        return vbox;
    }
    public HBox getPlayerBox() {
        return playerBox;
    }
}