package ph.edu.dlsu.modesta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by patricktobias on 05/08/2017.
 */
public class CardDrawView {
	private JComboBox<String> handSize;
	private JPanel PanelRoot;
	private JTextField targetTotal;
	private JTextField iterations;
	private JButton DRAWButton;
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

		cardsDisplay = new ArrayList<>();

		ImageIcon image = new ImageIcon(getClass().getResource(prefix_images + "others/Back.PNG"));

		cardsDisplay.add(card5);
		cardsDisplay.add(card4);
		cardsDisplay.add(card3);
		cardsDisplay.add(card2);
		cardsDisplay.add(card1);

		card1.setIcon(optimizeSize(image));
		card2.setIcon(optimizeSize(image));
		card3.setIcon(optimizeSize(image));
		card4.setIcon(optimizeSize(image));
		card5.setIcon(optimizeSize(image));

		frame.repaint();

		addListenerToDrawBtn(e -> {

			card1.setIcon(optimizeSize(image));
			card2.setIcon(optimizeSize(image));
			card3.setIcon(optimizeSize(image));
			card4.setIcon(optimizeSize(image));
			card5.setIcon(optimizeSize(image));

			String wReplace_Log = "wReplace_log.csv";
			String woReplace_Log = "withoutReplace_log.csv";
			FileWriter writer_w = null;
			FileWriter writer_wo = null;

			ArrayList<String> csvLine = new ArrayList<>();

			csvLine.add("Trial Number");

			assert handSize.getSelectedItem() != null;
			for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
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
			int[] countWr, countWor;
			switch (Integer.parseInt(handSize.getSelectedItem().toString())) {
				case 1:
					countWr = new int[14];
					countWor = new int[14];
					break;
				case 2:
					countWr = new int[27];
					countWor = new int[27];
					break;
				case 3:
					countWr = new int[40];
					countWor = new int[40];
					break;
				case 4:
					countWr = new int[53];
					countWor = new int[53];
					break;
				case 5:
					countWr = new int[66];
					countWor = new int[65];
					break;
				default:
					countWr = new int[0];
					countWor = new int[0];
			}

			String card_filename;

			for (int i = 0; i < Integer.parseInt(iterations.getText()); i++) {

				csvLine = new ArrayList<>();

				csvLine.add(i + "");

				System.out.println("===== TRIAL " + (i + 1) + " =====");

				CardList hand = new CardList();
				deck.resetCards();

				for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
					Card card = hand.addCard(deck.drawCard());

					card_filename = card.getSuit().getSuit() + "-" + card.getNumber() + ".PNG";

					cardsDisplay.get(j).setIcon(optimizeSize(
							new ImageIcon(getClass().getResource(
									prefix_images + card.getSuit().getSuit() + "/" + card_filename
							))));

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== Without replacement =====");
				hand.print();


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

				for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
					Card card = hand.addCard(deck.drawCardAndReturn());

					card_filename = card.getSuit().getSuit() + "-" + card.getNumber() + ".PNG";

					cardsDisplay.get(j).setIcon(optimizeSize(
							new ImageIcon(getClass().getResource(
									prefix_images + card.getSuit().getSuit() + "/" + card_filename
							))));

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== With replacement =====");
				hand.print();

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
		});
	}

	public void addListenerToDrawBtn(ActionListener actionListener) {
		DRAWButton.addActionListener(actionListener);
	}

	private ImageIcon optimizeSize (ImageIcon imageIcon) {
		Image imageObject = imageIcon.getImage(); // transform it
		Image newimg = imageObject.getScaledInstance(126, 183,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

		return new ImageIcon(newimg);
	}
}
