package ph.edu.dlsu.modesta;

/**
 * Created by patricktobias on 31/07/2017.
 */
public enum Suit {

    CLUBS ("CLUBS"),
    SPADES ("SPADES"),
    HEARTS ("HEARTS"),
    DIAMONDS ("DIAMONDS");

    private String name;

    Suit(String name) {
        this.name = name;
    }

    public String getSuit () {
        return this.name;
    }

}
