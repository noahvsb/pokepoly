package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class RailwayTile implements Tile {
    private String id;
    private String nameStr;
    private int width;
    private int height;
    private Text name;
    private ImageView imageView;

    private int cost;
    private int rent;
    private String owner;

    private HBox hbox;
    private VBox vbox;

    private boolean mouseToggle;
    private boolean mouseClickBlock;
    private Bord bord;
    private InfoTile infoTile;

    public RailwayTile(String id, int cost, int rent, Bord bord, InfoTile infoTile) throws IOException {
        this.id = id;

        width = n * 2;
        height = n;

        this.cost = cost;
        this.rent = rent;
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
        nameStr = props.getProperty(id);

        name = new Text();
        name.setText(nameStr.replaceAll(" ", "\n"));
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
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setOnMouseClicked(e -> tilePressed());
        vbox.setStyle(normalStyle);

        // image
        imageView = new ImageView();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(getImagePath())).toExternalForm()));
        imageView.setFitWidth(Math.max(width, height) / 3.0);
        imageView.setFitHeight(Math.max(width, height) / 3.0);

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
    }

    @Override
    public void tilePressed() {
        if (mouseToggle && !mouseClickBlock) {
            // display info
            Text title = new Text(nameStr);
            title.setFont(Font.font("System", FontWeight.BOLD, 13));

            Text huur1 = new Text("Huur met 1:          €" + rent);
            huur1.setFont(new Font(13));

            Text huur2 = new Text("Huur met 2:        €" + (rent * 2));
            huur2.setFont(new Font(13));

            Text huur3 = new Text("Huur met 3:        €" + (rent * 3));
            huur3.setFont(new Font(13));

            Text huur4 = new Text("Huur met 4:        €" + (rent * 4));
            huur4.setFont(new Font(13));

            Text costPrice = new Text("Kostprijs:   €" + cost);
            costPrice.setFont(new Font(13));

            Text currentOwner = new Text("Huidige eigenaar\n" + owner);
            currentOwner.setFont(new Font(13));
            currentOwner.setTextAlignment(TextAlignment.CENTER);

            infoTile.setup(13, imageView, title, huur1, huur2, huur3, huur4, costPrice, currentOwner);

            // change box look
            hbox.setStyle(highlightStyle);
            vbox.setStyle(highlightStyle);

            // block other tiles so they can't get clicked
            bord.changeMouseClickBlock(this);

            // change mouseToggle
            mouseToggle = !mouseToggle;
        } else if (!mouseClickBlock) {
            // reset infoTile
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
        return "/be/ugent/objprog/ugentopoly/assets/railway.png";
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
