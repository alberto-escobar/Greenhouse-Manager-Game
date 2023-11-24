package ui;

import model.Greenhouse;

import javax.swing.*;

// Represents a panel that displays greenhouse score.
public class ScorePanel extends JPanel {
    private static final String OWNER = "Owner: ";
    private static final String TIME = "Day ";
    private static final String DEBT = "Debt: $";
    private static final String WALLET = "Wallet: $";
    private static final String POTS = "Available pots:  ";
    private Greenhouse gh;
    private JLabel ownerLabel;
    private JLabel timeLabel;
    private JLabel debtLabel;
    private JLabel walletLabel;
    private JLabel potsLabel;

    // REQUIRES: gh is non-null
    // MODIFIES: this
    //  EFFECTS: Creates a ScorePanel based on the information from gh
    public ScorePanel(Greenhouse gh) {
        this.gh = gh;
        ownerLabel = new JLabel(OWNER + gh.getOwner());
        timeLabel = new JLabel(TIME + gh.getDay());
        debtLabel = new JLabel(DEBT + gh.getDebt());
        walletLabel = new JLabel(WALLET + gh.getWallet());
        potsLabel = new JLabel(POTS + gh.availablePots());
        add(timeLabel);
        add(debtLabel);
        add(walletLabel);
        add(potsLabel);
    }



    // MODIFIES: this
    //  EFFECTS: Updates all components in this panel with information from gh
    public void update() {
        ownerLabel.setText(OWNER + gh.getOwner());
        timeLabel.setText(TIME + gh.getDay());
        debtLabel.setText(DEBT + gh.getDebt());
        walletLabel.setText(WALLET + gh.getWallet());
        potsLabel.setText(POTS + gh.availablePots());
        repaint();
    }
}
