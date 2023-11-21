package ui;

import model.Greenhouse;

import javax.swing.*;

public class ScorePanel extends JPanel {
    private static final String TIME = "Day ";
    private static final String DEBT = "Debt: $";
    private static final String WALLET = "Wallet: $";
    private static final String POTS = "Available pots:  ";
    private Greenhouse gh;
    private JLabel timeLabel;
    private JLabel debtLabel;
    private JLabel walletLabel;
    private JLabel potsLabel;

    public ScorePanel(Greenhouse gh) {
        this.gh = gh;
        timeLabel = new JLabel(TIME + gh.getDay());
        debtLabel = new JLabel(DEBT + gh.getDebt());
        walletLabel = new JLabel(WALLET + gh.getWallet());
        potsLabel = new JLabel(POTS + gh.availablePots());
        add(timeLabel);
        add(debtLabel);
        add(walletLabel);
        add(potsLabel);
    }

    public void update() {
        timeLabel.setText(TIME + gh.getDay());
        debtLabel.setText(DEBT + gh.getDebt());
        walletLabel.setText(WALLET + gh.getWallet());
        potsLabel.setText(POTS + gh.availablePots());
        repaint();
    }
}
