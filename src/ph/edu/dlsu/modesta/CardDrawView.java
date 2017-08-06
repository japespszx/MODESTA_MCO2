package ph.edu.dlsu.modesta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by patricktobias on 05/08/2017.
 */
public class CardDrawView {
    private JComboBox handSize;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        handSize.addItem("1");
        handSize.addItem("2");
        handSize.addItem("3");
        handSize.addItem("4");
        handSize.addItem("5");

        addListenerToDrawBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int totalTally_with = 0;
                int totalTally_without = 0;

                for (int i = 0; i < Integer.parseInt(iterations.getText()); i++) {

                    System.out.println("===== TRIAL " + (i + 1) + " =====");

                    CardList hand = new CardList();
                    deck.resetCards();

                    for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++ ) {
                        hand.addCard(deck.drawCard());
                    }

                    System.out.println("===== Without replacement =====");
                    hand.print();

                    if (Integer.parseInt(targetTotal.getText()) == hand.getTotal())
                        totalTally_without++;

                    hand = new CardList();

                    for (int j = 0; j < (Integer.parseInt((String) handSize.getSelectedItem())); j++ ) {
                        hand.addCard(deck.drawCardAndReturn());
                    }

                    if (Integer.parseInt(targetTotal.getText()) == hand.getTotal())
                        totalTally_with++;

                    System.out.println("===== With replacement =====");
                    hand.print();
                }

                System.out.println("Target total obtained with replacement: " + totalTally_with);
                System.out.println("Target total obtained without replacement: " + totalTally_without);
            }
        });
    }

    public void addListenerToDrawBtn (ActionListener actionListener) {
        DRAWButton.addActionListener(actionListener);
    }
}
