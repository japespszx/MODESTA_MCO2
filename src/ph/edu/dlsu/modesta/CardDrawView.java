package ph.edu.dlsu.modesta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
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
	private JLabel card1;
	private JLabel card2;
	private JLabel card3;
	private JLabel card4;
	private JLabel card5;

	private CardList deck;

	public CardDrawView(CardList deck) {
		JFrame frame = new JFrame("CardDrawView");
		this.deck = deck;

		frame.setContentPane(PanelRoot);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		handSize.addItem("1");
		handSize.addItem("2");
		handSize.addItem("3");
		handSize.addItem("4");
		handSize.addItem("5");

		addListenerToDrawBtn(e -> {

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

			for (int i = 0; i < Integer.parseInt(iterations.getText()); i++) {

				csvLine = new ArrayList<>();

				csvLine.add(i + "");

				System.out.println("===== TRIAL " + (i + 1) + " =====");

				CardList hand = new CardList();
				deck.resetCards();

				for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++) {
					Card card = hand.addCard(deck.drawCard());

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== Without replacement =====");
				hand.print();

				csvLine.add(hand.getTotal() + "");

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

					csvLine.add(card.getSuit().getSuit() + ":" + card.getNumber());
				}

				System.out.println("===== With replacement =====");
				hand.print();

				csvLine.add(hand.getTotal() + "");

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
}
