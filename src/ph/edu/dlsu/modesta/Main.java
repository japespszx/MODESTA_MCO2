package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Main {

	public static void main(String[] args) {

		// Create an R vector in the form of a string.
		RConnection connection = null;

		try {
             /* Create a connection to Rserve instance running
              * on default port 6311
              */
			connection = new RConnection();
			//TODO Put code here

			CardList deck = new CardList();
			CardList hand;

			// START TRIAL
			for (int i = 0; i < 10000; i++) {

				System.out.println("===== TRIAL " + (i + 1) + " =====");

				hand = new CardList();
				deck.resetCards();

				hand.addCard(deck.drawCard());
				hand.addCard(deck.drawCard());
				hand.addCard(deck.drawCard());
				hand.addCard(deck.drawCard());
				hand.addCard(deck.drawCard());

				hand.print();
			}

			//Sample code
			String vector = "c(1,2,3,4)";
			connection.eval("meanVal=mean(" + vector + ")");
			double mean = connection.eval("meanVal").asDouble();
			System.out.println("The mean of given vector is=" + mean);
		} catch (RserveException | REXPMismatchException e) {
			e.printStackTrace();
		} finally{
			if (connection != null) {
				connection.close();
			}
		}
	}
}
