package ui;

import model.Greenhouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiGame extends JFrame {
    Greenhouse gh;
    ScorePanel sp;
    ToolPanel tp;
    PlantsPanel pp;

    // Constructs main window
    // effects: sets up window in which Green House game will be played
    public GuiGame() {
        //Create and set up the window.
        super("HelloWorldSwing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        long currentTime = System.currentTimeMillis();
        gh = new Greenhouse("test", currentTime);
        sp = new ScorePanel(gh);
        tp = new ToolPanel(gh);
        pp = new PlantsPanel(gh);

        //Add the ubiquitous "Hello World" label.
        mainPanel.add(sp);
        mainPanel.add(tp);
        mainPanel.add(pp);
        add(mainPanel);


        //Display the window.
        this.pack();
        this.setVisible(true);
        addTimer();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                long currentTime = System.currentTimeMillis();
                gh.updateTime(currentTime);
                sp.update();
                pp.update();
            }
        });

        t.start();
    }

    public static void main(String[] args) {
        new GuiGame();
    }
}
