package ui;

import model.Greenhouse;

import javax.swing.*;

public class ScorePanel extends JPanel {
    private static final String TIME = "Time passed: ";
    private Greenhouse gh;
    private JLabel timeLabel;

    public ScorePanel(Greenhouse gh) {
        this.gh = gh;
        timeLabel = new JLabel(TIME + Integer.toString(gh.getTime()));
        add(timeLabel);
    }

    public void update() {
        timeLabel.setText(TIME + Integer.toString(gh.getTime()));
        repaint();
    }
}
