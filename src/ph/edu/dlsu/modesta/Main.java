package ph.edu.dlsu.modesta;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		//initialize Rserve connection

		CardList deck = new CardList();

		CardDrawView cardDrawView = new CardDrawView(deck);

//		runTrials("withR", "withoutR", deck, 5, 100000);

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

	private static void runTrials(String wr, String wor, CardList deck, int handSize, int trialSize) throws IOException {
		int targetTotal = handSize * 13;
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

//				System.out.println("===== TRIAL " + (j + 1) + " =====");

				CardList hand = new CardList();
				deck.resetCards();

				for (int k = 0; k < handSize; k++) {
					Card card = hand.addCard(deck.drawCard());

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

//				System.out.println("===== Without replacement =====");
//				hand.print();

				csvLine.add(hand.getTotal() + "");

				if (i + 1 == hand.getTotal()) {
					totalTally_without++;
					csvLine.add("YES");
				} else {
					csvLine.add("NO");
				}

				try {
					CSVUtils.writeLine(writer_wo, csvLine);
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

//				System.out.println("===== With replacement =====");
//				hand.print();

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

		generateActualProbability(trialSize);
	}

	private static void generateActualProbability(double trialSize) throws IOException {
		CSVReader csvReader;
		List<String[]> stringList;
		CSVWriter csvWriter;


		csvReader = new CSVReader(new FileReader("worData.csv"));
		stringList = csvReader.readAll();
		csvWriter = new CSVWriter(new FileWriter("actualWorProbability.csv"));
		csvWriter.writeNext(new String[]{"Total", "Probability"});
		for (int j = 1; j < stringList.size(); j++) {
			BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(stringList.get(j)[1]) / trialSize);
			csvWriter.writeNext(new String[]{j + "", bd + ""});
		}
		csvReader.close();
		csvWriter.close();

		csvReader = new CSVReader(new FileReader("wrData.csv"));
		stringList = csvReader.readAll();
		csvWriter = new CSVWriter(new FileWriter("actualWrProbability.csv"));
		csvWriter.writeNext(new String[]{"Total", "Probability"});
		for (int j = 1; j < stringList.size(); j++) {
			BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(stringList.get(j)[1]) / trialSize);
			csvWriter.writeNext(new String[]{j + "", bd + ""});
		}
		csvReader.close();
		csvWriter.close();
	}
}
