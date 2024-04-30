package be.ugent.objprog.ugentopoly.tiles.card;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck extends ArrayList<Element> {
    private int cardCounter;
    private Element getOutOfJailCard;

    public Deck(Element deckElement) {
        addAll(deckElement.getChildren());
        Collections.shuffle(this);

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
}
