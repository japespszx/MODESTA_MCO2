package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import ph.edu.dlsu.modesta.R.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		//initialize Rserve connection

		CardList deck = new CardList();

		int[] x = {2,2,2};
		double[] prob = {0.5,0.25,0.125};

//		CardDrawView cardDrawView = new CardDrawView(deck);

		RUtils.dmultinom(x, prob);

		runTrials("withR", "withoutR", deck, 5, 1000, 64);
//		try {
//			//Sample code
//			String vector = "c(1,2,3,4)";
//			connection.eval("meanVal=mean(" + vector + ")");
//			double mean = connection.eval("meanVal").asDouble();
//			System.out.println(mean);
//		} catch (RserveException | REXPMismatchException e) {
//			e.printStackTrace();
//		} finally {
//			if (connection != null)
//				connection.close();
//		}
	}

	private static void runTrials(String wr, String wor, CardList deck, int handSize, int trialSize, int targetTotal) throws IOException {
		FileWriter wrData = new FileWriter("wrData.csv");
		FileWriter worData = new FileWriter("worData.csv");
		ArrayList<String> list = new ArrayList<>();
		list.add("Total");
		list.add("Frequency");
		CSVUtils.writeLine(wrData, list);
		CSVUtils.writeLine(worData, list);
		for (int i = 0; i < targetTotal; i++) {
			String wReplace_Log = wr + (i + 1) + ".csv";
			String woReplace_Log = wor + (i + 1) + ".csv";
			FileWriter writer_w = null;
			FileWriter writer_wo = null;

			ArrayList<String> csvLine = new ArrayList<>();

			csvLine.add("Trial Number");

			for (int j = 0; j < handSize; j++) {
				csvLine.add("Card" + (j + 1));
			}

			csvLine.add("Total");
			csvLine.add("Total = Desired Total");

			try {
				writer_w = new FileWriter(wReplace_Log);
				writer_wo = new FileWriter(woReplace_Log);

				CSVUtils.writeLine(writer_w, csvLine);
				CSVUtils.writeLine(writer_wo, csvLine);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			int totalTally_with = 0;
			int totalTally_without = 0;

			for (int j = 0; j < trialSize; j++) {

				csvLine = new ArrayList<>();

				csvLine.add(j + "");

				System.out.println("===== TRIAL " + (j + 1) + " =====");

				CardList hand = new CardList();
				deck.resetCards();

				for (int k = 0; k < handSize; k++) {
					Card card = hand.addCard(deck.drawCard());

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== Without replacement =====");
				hand.print();

				csvLine.add(hand.getTotal() + "");

				if (i + 1 == hand.getTotal()) {
					totalTally_without++;
					csvLine.add("YES");
				} else {
					csvLine.add("NO");
				}

				try {
					CSVUtils.writeLine(writer_wo, csvLine);
					if (i + 1 == hand.getTotal())
						System.out.print("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				csvLine = new ArrayList<>();

				csvLine.add(j + "");

				hand = new CardList();

				for (int k = 0; k < handSize; k++) {
					Card card = hand.addCard(deck.drawCardAndReturn());

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== With replacement =====");
				hand.print();

				csvLine.add(hand.getTotal() + "");

				if (i + 1 == hand.getTotal()) {
					totalTally_with++;
					csvLine.add("YES");
				} else {
					csvLine.add("NO");
				}

				try {
					CSVUtils.writeLine(writer_w, csvLine);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			System.out.println("Target total obtained with replacement: " + totalTally_with);
			list = new ArrayList<>();
			list.add((i + 1) + "");
			list.add(totalTally_with + "");
			CSVUtils.writeLine(wrData, list);

			System.out.println("Target total obtained without replacement: " + totalTally_without);
			list = new ArrayList<>();
			list.add((i + 1) + "");
			list.add(totalTally_without + "");
			CSVUtils.writeLine(worData, list);

			try {
				assert writer_w != null;
				writer_w.flush();
				assert writer_wo != null;
				writer_wo.flush();

				writer_w.close();
				writer_wo.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		wrData.close();
		worData.close();
	}


}
