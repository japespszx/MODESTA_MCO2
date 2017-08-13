package ph.edu.dlsu.modesta;

/**
 * Created by patricktobias on 31/07/2017.
 */
public class Card {

	public static final int MAX_NUMBER = 13;

	private Suit suit;
	private int number;

	public Card(Suit suit, int number) {
		this.suit = suit;
		this.number = number;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
