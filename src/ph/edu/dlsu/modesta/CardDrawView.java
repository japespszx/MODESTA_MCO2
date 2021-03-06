package ph.edu.dlsu.modesta;

import com.objectplanet.chart.BarChart;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
import ph.edu.dlsu.modesta.R.RUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by patricktobias on 05/08/2017.
 */
public class CardDrawView {
	private JComboBox<String> handSize;
	private JPanel PanelRoot;
	private JTextField targetTotal;
	private JTextField iterations;
	private JButton DRAWButton;
	private JScrollPane scrollPane;
	private JPanel innerPanel;
	private JLabel card1;
	private JLabel card2;
	private JLabel card3;
	private JLabel card4;
	private JLabel card5;

	private ArrayList<JLabel> cardsDisplay;

	private CardList deck;

	private static final String prefix_images = "/ph/edu/dlsu/modesta/assets/cards/";

	public CardDrawView(CardList deck) {
		JFrame frame = new JFrame("CardDrawView");
		this.deck = deck;

		frame.setContentPane(PanelRoot);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setSize(800, 400);

		handSize.addItem("1");
		handSize.addItem("2");
		handSize.addItem("3");
		handSize.addItem("4");
		handSize.addItem("5");

		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		frame.repaint();

		addListenerToDrawBtn(e -> {

			innerPanel.removeAll();

			String wReplace_Log = "wReplace_log.csv";
			String woReplace_Log = "withoutReplace_log.csv";

			FileWriter writer_w = null;
			FileWriter writer_wo = null;

			ArrayList<String> csvLine = new ArrayList<>();

			csvLine.add("Trial Number");

			int currentHandSize = Integer.parseInt((String) handSize.getSelectedItem());
			int currentIterations = Integer.parseInt(iterations.getText());

			assert handSize.getSelectedItem() != null;
			for (int j = 0; j < currentHandSize; j++) {
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
			double[] countWr, countWor;
			switch (currentHandSize) {
				case 1:
					countWr = new double[14];
					countWor = new double[14];
					break;
				case 2:
					countWr = new double[27];
					countWor = new double[27];
					break;
				case 3:
					countWr = new double[40];
					countWor = new double[40];
					break;
				case 4:
					countWr = new double[53];
					countWor = new double[53];
					break;
				case 5:
					countWr = new double[66];
					countWor = new double[65];
					break;
				default:
					countWr = new double[0];
					countWor = new double[0];
			}

			for (int i = 0; i < currentIterations; i++) {

				csvLine = new ArrayList<>();

				csvLine.add(i + "");

				CardList hand = new CardList();
				deck.resetCards();

				ArrayList<Card> without_replacement_cards = new ArrayList<>();

				for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
					Card card = hand.addCard(deck.drawCard());

					without_replacement_cards.add(card);

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				addCards(without_replacement_cards);


				csvLine.add(hand.getTotal() + "");

				// count each total
				countWor[hand.getTotal()]++;

				if (Integer.parseInt(targetTotal.getText()) == hand.getTotal()) {
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

				csvLine.add(i + "");

				hand = new CardList();

				ArrayList<Card> with_replacement_cards = new ArrayList<>();

				for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
					Card card = hand.addCard(deck.drawCardAndReturn());

					with_replacement_cards.add(card);

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				addCards(with_replacement_cards);

				csvLine.add(hand.getTotal() + "");

				// count each total
				countWr[hand.getTotal()]++;

				if (Integer.parseInt(targetTotal.getText()) == hand.getTotal()) {
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
			System.out.println("Target total obtained without replacement: " + totalTally_without);

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

			makeHistogram(countWor, "Count without replacements");
			makeHistogram(countWr, "Count with replacements");

			String binomActualWr_Log = "wReplace_binom_log.csv";
			String binomActualWor_Log = "withoutReplace_binom_log.csv";

			String nbinomActualWr_Log = "wReplace_nbinom_log.csv";
			String nbinomActualWor_Log = "withoutReplace_nbinom_log.csv";

			FileWriter binomWr_writer = null;
			FileWriter binomWor_writer= null;
			FileWriter nbinomWr_writer = null;
			FileWriter nbinomWor_writer = null;

			try {
				binomWr_writer = new FileWriter(binomActualWr_Log);
				binomWor_writer = new FileWriter(binomActualWor_Log);
				nbinomWr_writer = new FileWriter(nbinomActualWr_Log);
				nbinomWor_writer = new FileWriter(nbinomActualWor_Log);
			} catch (IOException e1) {
				e1.printStackTrace();
			}


			BigDecimal[] idealProbWR = new BigDecimal[1];
			BigDecimal[] idealProbWoR = new BigDecimal[1];

			try {
				idealProbWR = IdealProbabilities.get(true, currentHandSize);
				idealProbWoR = IdealProbabilities.get(false, currentHandSize);
			} catch (REXPMismatchException e1) {
				e1.printStackTrace();
			} catch (RserveException e1) {
				e1.printStackTrace();
			}

			csvLine = new ArrayList<String>();

			csvLine.add("Total");
			csvLine.add("Binomial_Prob");

			try {
				CSVUtils.writeLine(binomWr_writer, csvLine);
				CSVUtils.writeLine(binomWor_writer, csvLine);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			csvLine.remove(1);
			csvLine.add("N_Binomial_Prob");

			try {
				CSVUtils.writeLine(nbinomWr_writer, csvLine);
				CSVUtils.writeLine(nbinomWor_writer, csvLine);
			} catch (IOException e1) {

			}

			double[] binom_with = new double[countWr.length];
			double[] nbinom_with = new double[countWr.length];

			double[] binom_without = new double[countWor.length];
			double[] nbinom_without = new double[countWor.length];

			double[] hyper_with = new double[countWr.length];
			double[] hyper_without = new double[countWor.length];

			for (int x=1; x < countWor.length; x++) {
				double binom_prob_wr = RUtils.dbinom((int)countWor[x], currentIterations, idealProbWR[x].doubleValue());
				double binom_prob_wor = RUtils.dbinom((int)countWor[x], currentIterations, idealProbWoR[x].doubleValue());

				double nbinom_prob_wr = RUtils.dnbinom((int)countWor[x], currentIterations, idealProbWR[x].doubleValue());
				double nbinom_prob_wor = RUtils.dnbinom((int)countWor[x], currentIterations, idealProbWoR[x].doubleValue());

				binom_with[x] = binom_prob_wr;
				nbinom_with[x] = nbinom_prob_wr;

				binom_without[x] = binom_prob_wor;
				nbinom_without[x] = nbinom_prob_wor;

				csvLine = new ArrayList<String>();

				csvLine.add(x + "");
				csvLine.add(Double.toString(binom_prob_wr));

				try {
					CSVUtils.writeLine(binomWr_writer, csvLine);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				csvLine = new ArrayList<String>();

				csvLine.add(x + "");
				csvLine.add(Double.toString(binom_prob_wor));

				try {
					CSVUtils.writeLine(binomWor_writer, csvLine);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				csvLine = new ArrayList<String>();

				csvLine.add(x + "");
				csvLine.add(Double.toString(nbinom_prob_wr));

				try {
					CSVUtils.writeLine(nbinomWr_writer, csvLine);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				csvLine = new ArrayList<String>();

				csvLine.add(x + "");
				csvLine.add(Double.toString(nbinom_prob_wor));

				try {
					CSVUtils.writeLine(nbinomWor_writer, csvLine);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				int[] WR_Freq = IdealProbabilities.getFrequencies(true, currentHandSize);
				int[] WOR_Freq = IdealProbabilities.getFrequencies(false, currentHandSize);

				int total_WR = IdealProbabilities.getTotals(true, currentHandSize);
				int total_WOR = IdealProbabilities.getTotals(false, currentHandSize);

				hyper_with[x] = RUtils.dhyper((int)countWr[x], WR_Freq[x], total_WR-WR_Freq[x], currentIterations);
				hyper_without[x] = RUtils.dhyper((int)countWor[x], WOR_Freq[x], total_WOR-WOR_Freq[x], currentIterations);

				System.out.println(x);

				System.out.println("=== With Replacement ===");
				System.out.println("m = " + WR_Freq[x]);
				System.out.println("n = " + (total_WR - WR_Freq[x]));
				System.out.println("total = " + total_WR);
				System.out.println("=== Without Replacement ===");
				System.out.println("m = " + WOR_Freq[x]);
				System.out.println("n = " + (total_WOR - WOR_Freq[x]));
				System.out.println("total = " + total_WOR);

				System.out.println("=============");

			}

			makeHistogram(binom_with, "Binomial With Replacement");
			makeHistogram(nbinom_with, "Negative Binomial With Replacement");

			makeHistogram(binom_without, "Binomial Without Replacement");
			makeHistogram(nbinom_without, "Negative Binomial Without Replacement");

			makeHistogram(hyper_with, "HyperGeom With Replacement");
			makeHistogram(hyper_without, "HyperGeom Without Replacement");

			double[] ideal_trans_WR = new double[idealProbWR.length];
			double[] ideal_trans_WOR = new double[idealProbWoR.length];

			for (int z = 1; z < idealProbWR.length; z++) {
				ideal_trans_WR[z] = idealProbWR[z].doubleValue();
			}

			for (int z = 1; z < idealProbWoR.length; z++) {
				ideal_trans_WOR[z] = idealProbWoR[z].doubleValue();
			}

			makeHistogram(ideal_trans_WR, "Ideal Probability With Replacement");
			makeHistogram(ideal_trans_WOR, "Ideal Probability Without Replacement");

			try {
				assert binomWr_writer != null;
				binomWr_writer.flush();
				assert binomWor_writer != null;
				binomWor_writer.flush();
				assert nbinomWr_writer != null;
				binomWr_writer.flush();
				assert nbinomWor_writer != null;
				binomWor_writer.flush();

				binomWr_writer.close();
				binomWor_writer.close();
				nbinomWr_writer.close();
				nbinomWor_writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});
	}

	public void addListenerToDrawBtn(ActionListener actionListener) {
		DRAWButton.addActionListener(actionListener);
	}

	private ImageIcon optimizeSize(ImageIcon imageIcon) {
		Image imageObject = imageIcon.getImage(); // transform it
		Image newimg = imageObject.getScaledInstance(126, 183, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

		return new ImageIcon(newimg);
	}

	public void addCards(ArrayList<Card> cards) {

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());

		for (int j = 0; j < cards.size(); j++) {

			String card_filename = cards.get(j).getSuit().getSuit() + "-" + cards.get(j).getNumber() + ".PNG";

			JLabel label = new JLabel(optimizeSize(
					new ImageIcon(getClass().getResource(
							prefix_images + cards.get(j).getSuit().getSuit() + "/" + card_filename
					))));

			p.add(label);
		}

		innerPanel.add(p);


		innerPanel.updateUI();
	}

	public void makeHistogram(double[] list, String title) {
		double[] sampleValues = new double[list.length];
		for (int i = 0; i < list.length; i++)
			sampleValues[i] = list[i];

		String[] legendLabels = new String[list.length];

		for (int i = 0; i < legendLabels.length; i++) {
			legendLabels[i] = Double.toString(list[i]);
		}

		String[] sampleLabels = new String[list.length];
		for (int i = 0; i < sampleLabels.length; i++) {
			sampleLabels[i] = Integer.toString(i);
		}

		Color[] sampleColors = new Color[]{new Color(0x8AD0F5), new Color(0x8AB8F5), new Color(0x899BF4), new Color(0xAE89F4), new Color(0xE889F4), new Color(0xF58AC9), new Color(0xF68B9B), new Color(0xF69D8B), new Color(0xF6B58B), new Color(0xF6C78B), new Color(0xF6D88B), new Color(0xF6E88B), new Color(0xF6F68B), new Color(0xDCF58A), new Color(0x9AF58A), new Color(0x89F4D8)};

		double max = getMaxProbVal(list) + 0.05;


		BarChart chart = new BarChart();
		chart.setSampleCount(sampleValues.length);
		chart.setSampleValues(0, sampleValues);
		chart.setSampleColors(sampleColors);
		chart.setMultiColorOn(true);
		chart.setRange(0, max);
		chart.setFont("rangeLabelFont", new Font("Arial", Font.BOLD, 13));
		chart.setBarWidth(1);
		chart.setLegendLabels(legendLabels);
		chart.setLegendOn(true);
		chart.getBarLabels();
		chart.setBarLabelsOn(true);
		for (int i = 0; i < sampleLabels.length; i++) {
			chart.setSampleLabel(i, sampleLabels[i]);
		}
		chart.setFont("legendFont", new Font("Arial", Font.BOLD, 10));
		chart.setBackground(Color.white);
		chart.setTitle(title);
		chart.setTitleOn(true);

		com.objectplanet.chart.NonFlickerPanel p = new com.objectplanet.chart.NonFlickerPanel(new BorderLayout());
		p.add("Center", chart);
		JFrame f = new JFrame();
		f.add("Center", p);
		f.setSize(450, 320);
		f.setVisible(true);

		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public double getMaxProbVal(double[] list) {
		double max = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] > max)
				max = list[i];
		}
		return max;
	}

}
