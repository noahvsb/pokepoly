package be.ugent.objprog.ugentopoly.addSpeler;

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
            put("WINA", 1); put("VTK", 2); put("Chemica", 3); put("Filologica", 4);
            put("Geologica", 5); put("VBK", 6); put("VEK", 7); put("VLK", 8);
        }};

        // graphic
        ImageView icon =
                new ImageView(
                        new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/be/ugent/objprog/ugentopoly/assets/token" + nameToTokenNr.get(string) + ".png"))));

        return new IconAndString(icon, string);
    }
}
