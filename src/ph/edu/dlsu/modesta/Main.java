package ph.edu.dlsu.modesta;

public class Main {

    public static void main(String[] args) {

        CardList deck = new CardList();
        CardList hand;

        // START TRIAL
        for (int i = 0; i < 10000; i++) {

            System.out.println("===== TRIAL " + (i+1) + " =====");

            hand = new CardList();
            deck.resetCards();

            hand.addCard(deck.drawCard());
            hand.addCard(deck.drawCard());
            hand.addCard(deck.drawCard());
            hand.addCard(deck.drawCard());
            hand.addCard(deck.drawCard());

            hand.print();
        }
    }
}
