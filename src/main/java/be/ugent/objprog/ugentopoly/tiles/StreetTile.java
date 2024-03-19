package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    private HBox hbox;
    private VBox vbox;

    private boolean mouseToggle;
    private boolean mouseClickBlock;
    private Bord bord;
    private InfoTile infoTile;

    public StreetTile(String id, String colour, int cost,  int houseCost, Bord bord, InfoTile infoTile, int... rents) throws IOException {
        this.id = id;
        this.colour = colour;

        this.width = n * 2;
        this.height = n;

        this.cost = cost;
        this.houseCost = houseCost;
        this.rents = rents;

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
        if (name.getText().equals("Hoveniersberg"))
            name.setText("Hoveniers-\nberg");
        name.setFont(new Font(fontSize));
        name.setTextAlignment(TextAlignment.CENTER);

        Text nameCopy = new Text(name.getText());
        nameCopy.setFont(name.getFont());
        nameCopy.setTextAlignment(name.getTextAlignment());

        Text nameCopy2 = new Text(name.getText());
        nameCopy2.setFont(name.getFont());
        nameCopy2.setTextAlignment(name.getTextAlignment());

        // boxes
        hbox = new HBox(nameCopy);
        vbox = new VBox(nameCopy2);

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

        // stripe (areaColour)
        hbox.getChildren().add(new Stripe(colour, 25, 65 ));
        hbox.setSpacing(30);

        vbox.getChildren().add(new Stripe(colour, 65, 25));
        vbox.setSpacing(30);
    }

    @Override
    public void tilePressed() {
        if (mouseToggle && !mouseClickBlock) {
            // prepare and show infoTile
            Text title = name;
            title.setFont(Font.font("System", FontWeight.BOLD, 11));
            title.setTextAlignment(TextAlignment.CENTER);

            Text rent = new Text();
            rent.setText("Huur: €" + rents[0]);
            rent.setFont(new Font(11));
            rent.setTextAlignment(TextAlignment.CENTER);

            Text price = new Text();
            price.setText("Kostprijs: €" + cost);
            price.setFont(new Font(11));
            price.setTextAlignment(TextAlignment.CENTER);

            Text currentOwner = new Text();
            currentOwner.setText("Huidige eigenaar\n<te koop>");
            currentOwner.setFont(new Font(11));
            currentOwner.setTextAlignment(TextAlignment.CENTER);

            infoTile.setup(30, new Stripe(colour, 200, 50), title, rent, price, currentOwner);

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
        return null;
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
