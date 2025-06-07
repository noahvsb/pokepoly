package be.ugent.objprog.pokepoly.tiles.card;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Element> {
    private int type; // 1 = chance, -1 = chest
    private int cardCounter;
    private int jailCardPos;
    private boolean hasJailCard;

    public Deck(Element deckElement, int type) {
        addAll(deckElement.getChildren());
        Collections.shuffle(this);

        this.type = type;
        cardCounter = -1;

        jailCardPos = 0;
        for (Element e : this) {
            if (e.getAttributeValue("type").equals("JAIL"))
                break;
            jailCardPos++;
        }
        hasJailCard = true;
    }

    public Element getTopCard() {
        cardCounter++;
        if (cardCounter == size())
            cardCounter = 0;
        if (!hasJailCard && cardCounter == jailCardPos)
            cardCounter += jailCardPos == size() - 1 ? -cardCounter : 1;

        return get(cardCounter);
    }

    public void removeGetOutOfJailCard() {
        hasJailCard = false;
    }

    public void addGetOutOfJailCard() {
        hasJailCard = true;
    }

    public int getType() {
        return type;
    }
}
