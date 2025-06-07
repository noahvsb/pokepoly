package be.ugent.objprog.pokepoly.startspel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.Objects;

public class IconConverter extends StringConverter<IconAndString> {
    @Override
    public String toString(IconAndString object) {
        return object.name();
    }

    @Override
    public IconAndString fromString(String string) {
        HashMap<String, Integer> nameToTokenNr = new HashMap<>() {{
            put("Vaporeon", 1); put("Jolteon", 2); put("Flareon", 3); put("Espeon", 4);
            put("Umbreon", 5); put("Leafon", 6); put("Glaceon", 7); put("Sylveon", 8);
        }};

        // graphic
        ImageView icon =
                new ImageView(
                        new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/be/ugent/objprog/pokepoly/assets/token" + nameToTokenNr.get(string) + ".png"))));

        return new IconAndString(icon, string);
    }
}
