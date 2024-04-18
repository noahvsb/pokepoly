package be.ugent.objprog.ugentopoly.tiles;

import be.ugent.objprog.ugentopoly.Speler;
import org.jdom2.Element;

public class Card {
    private String type;
    private String id;
    public Card(Element card) {
        type = card.getAttributeValue("type");
        id = card.getAttributeValue("id");
    }

    public void handleCardAction(Speler speler) {
        // TODO
    }
}
