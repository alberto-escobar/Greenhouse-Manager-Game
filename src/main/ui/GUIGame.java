package ui;

import javax.swing.*;

public class GUIGame extends JFrame {
    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public GUIGame() {
        //Create and set up the window.
        super("HelloWorldSwing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        this.getContentPane().add(label);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GUIGame();
    }
}
