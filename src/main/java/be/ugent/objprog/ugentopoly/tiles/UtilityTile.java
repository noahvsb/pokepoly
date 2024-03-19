package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class UtilityTile implements Tile {
    private String id;
    private int width;
    private int height;
    private Text name;
    private ImageView imageView;

    private int cost;
    private int util;
    private String owner;

    private HBox hbox;
    private VBox vbox;

    private boolean mouseToggle;
    private boolean mouseClickBlock;
    private Bord bord;
    private InfoTile infoTile;

    public UtilityTile(String id, int util, int cost, Bord bord, InfoTile infoTile) throws IOException {
        this.id = id;
        this.util = util;

        this.width = n * 2;
        this.height = n;

        this.cost = cost;
        owner = "<te koop>";

        mouseToggle = true;
        mouseClickBlock = false;
        this.bord = bord;
        this.infoTile = infoTile;

        createTile();
    }

    @Override
    public void createTile() throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/be/ugent/objprog/ugentopoly/ugentopoly.deel1.properties"));

        // name text
        name = new Text();
        name.setText(props.getProperty(id).replaceAll(" ", "\n"));
        name.setFont(new Font(fontSize));
        name.setTextAlignment(TextAlignment.CENTER);

        Text nameCopy = new Text(name.getText());
        nameCopy.setFont(name.getFont());
        nameCopy.setTextAlignment(name.getTextAlignment());

        // boxes
        hbox = new HBox(name);
        vbox = new VBox(nameCopy);

        hbox.setPrefSize(width, height);
        hbox.setMaxSize(width, height);
        hbox.setMinSize(width, height);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setOnMouseClicked(e -> tilePressed());
        hbox.setStyle(normalStyle);

        vbox.setPrefSize(height, width);
        vbox.setMaxSize(height, width);
        vbox.setMinSize(height, width);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setOnMouseClicked(e -> tilePressed());
        vbox.setStyle(normalStyle);

        // image
        imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 2.0);
        imageView.setFitHeight(Math.max(width, height) / (util == 1 ? 6.0 : 4.0));

        ImageView imageViewCopy = new ImageView();
        imageViewCopy.setImage(imageView.getImage());
        imageViewCopy.setFitWidth(imageView.getFitWidth());
        imageViewCopy.setFitHeight(imageView.getFitHeight());

        ImageView imageViewCopy2 = new ImageView();
        imageViewCopy2.setImage(imageView.getImage());
        imageViewCopy2.setFitWidth(imageView.getFitWidth());
        imageViewCopy2.setFitHeight(imageView.getFitHeight());

        hbox.getChildren().add(imageViewCopy);
        hbox.setSpacing(12);

        vbox.getChildren().add(imageViewCopy2);
        vbox.setSpacing(12);
        if (name.getText().equals("Schamper")) {
            vbox.setSpacing(30);
        }
    }

    @Override
    public void tilePressed() {
        if (mouseToggle && !mouseClickBlock) {
            // display info
            imageView.setFitWidth(100);
            imageView.setFitHeight(util == 1 ? 33.333 : 50);

            Text huur1 = new Text("Huur met 1:     worp x €4");
            huur1.setFont(new Font(13));

            Text huur2 = new Text("Huur met 2:   worp x €10");
            huur2.setFont(new Font(13));

            Text costPrice = new Text("Kostprijs:   €" + cost);
            costPrice.setFont(new Font(13));

            Text currentOwner = new Text("Huidige eigenaar\n" + owner);
            currentOwner.setFont(new Font(13));
            currentOwner.setTextAlignment(TextAlignment.CENTER);

            infoTile.setup(20, imageView, huur1, huur2, costPrice, currentOwner);

            // change box look
            hbox.setStyle(highlightStyle);
            vbox.setStyle(highlightStyle);

            // block other tiles so they can't get clicked
            bord.changeMouseClickBlock(this);

            // change mouseToggle
            mouseToggle = !mouseToggle;
        } else if (!mouseClickBlock) {
            // reset info
            infoTile.reset();

            // change box look
            hbox.setStyle(normalStyle);
            vbox.setStyle(normalStyle);

            // unblock other tiles
            bord.changeMouseClickBlock(this);

            // change mouse toggle
            mouseToggle = !mouseToggle;
        }
    }

    @Override
    public void changeMouseClickBlock() {
        mouseClickBlock = !mouseClickBlock;
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
        return "/be/ugent/objprog/ugentopoly/assets/utility" + util + ".png";
    }

    @Override
    public HBox getHBox() {
        return hbox;
    }

    @Override
    public VBox getVBox() {
        return vbox;
    }
}
