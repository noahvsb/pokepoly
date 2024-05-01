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
        Collections.shuffle(this);

        this.type = type;
        cardCounter = 0;
        getOutOfJailCard = deckElement.getChildren().getFirst();
    }

    public Element getTopCard() {
        cardCounter++;
        if (cardCounter == size())
            cardCounter = 1;
        return get(cardCounter - 1);
    }

    public boolean removeGetOutOfJailCard() {
        if (getFirst().equals(getOutOfJailCard)) {
            removeFirst();
            return true;
        }
        return false;
    }

    public boolean addGetOutOfJailCard() {
        if (!getFirst().equals(getOutOfJailCard)) {
            addFirst(getOutOfJailCard);
            return true;
        }
        return false;
    }

    public int getType() {
        return type;
    }
}
