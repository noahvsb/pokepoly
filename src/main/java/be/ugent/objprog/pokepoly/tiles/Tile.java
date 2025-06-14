package be.ugent.objprog.pokepoly.tiles;

import be.ugent.objprog.pokepoly.rightdisplay.Logs;
import be.ugent.objprog.pokepoly.Speler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class Tile {
    protected static class Texts {
        public static Text title(String name) {
            Text txt = new Text(name);
            txt.setFont(Font.font("System", FontWeight.BOLD, INFO_TILE_FONT_SIZE));
            return txt;
        }
        public static Text rent(int rent) {
            Text txt = new Text("Rent:       €" + rent);
            txt.setFont(new Font(INFO_TILE_FONT_SIZE));
            return txt;
        }
        public static Text cost(int cost) {
            Text txt = new Text("Cost:       €" + cost);
            txt.setFont(new Font(INFO_TILE_FONT_SIZE));
            return txt;
        }
        public static Text owner(Speler owner) {
            Text txt = new Text("Current owner\n" + (owner == null ? "<for sale>" : owner.getShortendName(30)));
            txt.setFont(new Font(INFO_TILE_FONT_SIZE));
            txt.setTextAlignment(TextAlignment.CENTER);
            return txt;
        }
        public static Text description(String desc) {
            Text txt = new Text(desc);
            txt.setFont(new Font(INFO_TILE_FONT_SIZE));
            txt.setWrappingWidth(180);
            txt.setTextAlignment(TextAlignment.CENTER);
            return txt;
        }
        public static Text description(String desc, double fontSize) {
            Text txt = new Text(desc);
            txt.setFont(new Font(fontSize));
            txt.setWrappingWidth(180);
            txt.setTextAlignment(TextAlignment.CENTER);
            return txt;
        }
    }

    // final variables
    protected static final double FONT_SIZE = 11;
    protected static final int N = 65; // used for WIDTH and HEIGHT
    protected static final double BORDER_WIDTH = 1;
    protected static final double INFO_TILE_FONT_SIZE = 13;

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

    protected Logs logs;
    protected Speler[] spelers;

    public void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/be/ugent/objprog/pokepoly/pokepoly.properties"));

        // name text
        name = props.getProperty(id);

        // boxes
        hbox = new HBox(createName());
        vbox = new VBox(createName());
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
        name.setFont(new Font(FONT_SIZE));
        name.setWrappingWidth(60);
        name.setTextAlignment(TextAlignment.CENTER);

        return name;
    }

    public Node createGraphic(boolean orientation) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / getImageSizeDivider());
        imageView.setFitHeight(Math.max(width, height) / getImageSizeDivider());
        imageView.setPreserveRatio(true);

        hbox.setSpacing(getHBoxSpacing());
        vbox.setSpacing(getVBoxSpacing());

        return imageView;
    }

    public Node createGraphic(double size) {
        ImageView imageView = (ImageView) createGraphic(true);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    public void setLogsAndSpelers(Logs logs, Speler[] spelers) {
        this.logs = logs;
        this.spelers = spelers;
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

    public void handleTileAction(Speler speler) {
        Alert alert = new Alert(getAlertType(speler));

        if (alert.getAlertType().equals(Alert.AlertType.CONFIRMATION))
            alert.setTitle("Purchase");
        else
            alert.setTitle("Report");

        alert.setHeaderText(getAlertDescription(speler));

        ImageView iconCopy = new ImageView(speler.getIcon().getImage());
        iconCopy.setFitWidth(speler.getIcon().getFitWidth()); iconCopy.setFitHeight(speler.getIcon().getFitHeight());
        iconCopy.setPreserveRatio(true);
        alert.setGraphic(iconCopy);

        // show infoTile
        mouseToggle = true;
        tilePressed();

        alert.showAndWait().ifPresent(response -> {
            tilePressed();
            if (response == ButtonType.OK)
                responseWasOk(speler);
        });
    }

    public abstract Alert.AlertType getAlertType(Speler speler);
    public abstract String getAlertDescription(Speler speler);
    public abstract void responseWasOk(Speler speler);

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImagePath() {
        return "/be/ugent/objprog/pokepoly/assets/" + imageName + ".png";
    }
    public String getNormalStyle() {
        return normalStyle;
    }

    public double getImageSizeDivider() {
        return 3;
    }

    public double getHBoxSpacing() {
        return 12;
    }
    public double getVBoxSpacing() {
        return 12;
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