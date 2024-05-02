package be.ugent.objprog.ugentopoly.tiles.card;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Element> {
    private int type; // 1 = chance, -1 = chest
    private int cardCounter;
    private Element getOutOfJailCard;

    public Deck(Element deckElement, int type) {
        addAll(deckElement.getChildren());

        this.type = type;
        cardCounter = -1;
        getOutOfJailCard = deckElement.getChildren().getFirst();
        Collections.shuffle(this);
    }

    public Element getTopCard() {
        cardCounter++;
        if (cardCounter == size())
            cardCounter = 0;
        return get(cardCounter);
    }

    public boolean removeGetOutOfJailCard() {
        if (getFirst().equals(getOutOfJailCard)) {
            removeFirst();
            return true;
        }
        return false;
    }

    public boolean addGetOutOfJailCard() {
        if (!contains(getOutOfJailCard)) {
            add(getOutOfJailCard);
            return true;
        }
        return false;
    }

    public int getType() {
        return type;
    }
}
