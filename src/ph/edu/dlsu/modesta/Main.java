package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import ph.edu.dlsu.modesta.R.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	public static void main(String[] args) {
		//initialize Rserve connection
		RConnection connection = Rserve.getConnection();

		CardList deck = new CardList();
		
		CardDrawView cardDrawView = new CardDrawView(deck);

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
