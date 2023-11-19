package ui;

import model.Greenhouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIGame extends JFrame {
    Greenhouse gh;
    ScorePanel sp;

    // Constructs main window
    // effects: sets up window in which Green House game will be played
    public GUIGame() {
        //Create and set up the window.
        super("HelloWorldSwing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        long currentTime = System.currentTimeMillis();
        gh = new Greenhouse("test", currentTime);
        sp = new ScorePanel(gh);

        //Add the ubiquitous "Hello World" label.
        add(sp);

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
                //update plant panel
            }
        });

        t.start();
    }

    public static void main(String[] args) {
        new GUIGame();
    }
}
