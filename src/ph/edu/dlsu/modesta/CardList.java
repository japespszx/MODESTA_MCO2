package ph.edu.dlsu.modesta;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by patricktobias on 31/07/2017.
 */
public class CardList {

    private ArrayList<Card> cards;

    public CardList() {
        cards = new ArrayList<>();
    }

    public CardList(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card addCard (Card card) {
        cards.add(card);

        return card;
    }

    public Card getCard (int index) {
        return cards.get(index);
    }

    public Card drawCard () {
        Random random = new Random();

        int x = random.nextInt(size());

        return cards.remove(x);
    }

    public int getCardNumber (int index) {
        return cards.get(index).getNumber();
    }

    public String getCardSuit (int index) {
        return cards.get(index).getSuit().getSuit();
    }

    public int size () {
        return cards.size();
    }

    public void print () {
        for (int i = 0; i < size(); i++) {
            System.out.println(i + " : " + getCardSuit(i) + ", " + getCardNumber(i));
        }
    }

    public void resetCards () {
        cards = new ArrayList<>();

        for (int i = 1; i <= Card.MAX_NUMBER; i++) {
            Card card = new Card(Suit.CLUBS, i);

            addCard(card);
        }

        for (int i = 1; i <= Card.MAX_NUMBER; i++) {
            Card card = new Card(Suit.SPADES, i);

            addCard(card);
        }

        for (int i = 1; i <= Card.MAX_NUMBER; i++) {
            Card card = new Card(Suit.HEARTS, i);

            addCard(card);
        }

        for (int i = 1; i <= Card.MAX_NUMBER; i++) {
            Card card = new Card(Suit.DIAMONDS, i);

            addCard(card);
        }
    }

}
