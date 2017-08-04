package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import ph.edu.dlsu.modesta.R.*;

public class Main {

	public static void main(String[] args) {
		//initialize Rserve connection
		RConnection connection = Rserve.getConnection();

//		CardList deck = new CardList();
//		CardList hand;
//
//		// START TRIAL
//		for (int i = 0; i < 10000; i++) {
//
//			System.out.println("===== TRIAL " + (i + 1) + " =====");
//
//			hand = new CardList();
//			deck.resetCards();
//
//			hand.addCard(deck.drawCard());
//			hand.addCard(deck.drawCard());
//			hand.addCard(deck.drawCard());
//			hand.addCard(deck.drawCard());
//			hand.addCard(deck.drawCard());
//
//			hand.print();
//		}

		try {
			//Sample code
			String vector = "c(1,2,3,4)";
			connection.eval("meanVal=mean(" + vector + ")");
			double mean = connection.eval("meanVal").asDouble();
			System.out.println(mean);
		} catch (RserveException | REXPMismatchException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.close();
		}
	}
}
