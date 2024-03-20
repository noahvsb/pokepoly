package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Bord;
import javafx.geometry.Pos;
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
    private String nameStr;
    private int width;
    private int height;
    private Text name;

    private String colour;
    private int cost;
    private int houseCost;
    private int[] rents;
    private String owner;

    private HBox hbox;
    private VBox vbox;

    private boolean mouseToggle;
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
        owner = "<te koop>";

        mouseToggle = true;
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
        if (name.getText().equals("Hoveniersberg"))
            name.setText("Hoveniers-\nberg");
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

        // stripe (areaColour)
        hbox.getChildren().add(new Stripe(colour, 25, 65 ));
        hbox.setSpacing(30);

        vbox.getChildren().add(new Stripe(colour, 65, 25));
        vbox.setSpacing(30);
    }

    @Override
    public void tilePressed() {
        Tile currentActive = infoTile.getCurrentActive();

        // set active
        if (mouseToggle) {
            if (currentActive != null) {
                currentActive.getHBox().setStyle(normalStyle);
                currentActive.getVBox().setStyle(normalStyle);

                currentActive.changeMouseToggle();

                infoTile.reset();
            }

            // display info
            Text title = new Text(nameStr);
            title.setFont(Font.font("System", FontWeight.BOLD, 13));

            Text rent = new Text("Huur:           €" + rents[0]);
            rent.setFont(new Font(13));

            Text price = new Text("Kostprijs:     €" + cost);
            price.setFont(new Font(13));

            Text currentOwner = new Text("Huidige eigenaar\n" + owner);
            currentOwner.setFont(new Font(13));
            currentOwner.setTextAlignment(TextAlignment.CENTER);

            infoTile.setup(Pos.TOP_CENTER, 30, this, new Stripe(colour, 200, 50), title, rent, price, currentOwner);

            // change box look
            hbox.setStyle(highlightStyle);
            vbox.setStyle(highlightStyle);

            // change mouseToggle
            mouseToggle = !mouseToggle;
        }
        // set inactive
        else {
            // reset infoTile
            infoTile.reset();

            // change box look
            hbox.setStyle(normalStyle);
            vbox.setStyle(normalStyle);

            // change mouse toggle
            mouseToggle = !mouseToggle;
        }
    }

    @Override
    public void changeMouseToggle() {
        mouseToggle = !mouseToggle;
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
