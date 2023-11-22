package ui;

import model.Greenhouse;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GuiGame extends JFrame {
    Greenhouse gh;
    JPanel mainPanel;
    ScorePanel sp;
    ToolPanel tp;
    PlantsPanel pp;

    // Constructs main window
    // effects: sets up window in which Green House game will be played
    public GuiGame() {
        //Create and set up the window.
        super("Greenhouse Manager 2023");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener((ActionEvent ae) ->
                this.newGameCommand()
        );
        mainPanel.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener((ActionEvent ae) ->
                this.loadGameCommand()
        );
        mainPanel.add(loadGameButton);
        add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private void newGameCommand() {
        String name = JOptionPane.showInputDialog(null, "Please enter name for new game:");
        long currentTime = System.currentTimeMillis();
        gh = new Greenhouse(name, currentTime);
        mainPanel.removeAll();
        createGamePanel(gh);
    }

    private void loadGameCommand() {
        String name = JOptionPane.showInputDialog(null, "Please enter name of game save to load:");
        String savePath = "./data/" + name + ".json";
        try {
            JsonReader jsonReader = new JsonReader(savePath);
            this.gh = jsonReader.read();

            mainPanel.removeAll();
            createGamePanel(gh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load following file:\n" + savePath);
        }
    }

    private void createGamePanel(Greenhouse gh) {
        //create game panels
        sp = new ScorePanel(gh);
        tp = new ToolPanel(gh);
        pp = new PlantsPanel(gh);

        //Add game panels to main panel
        mainPanel.add(sp);
        mainPanel.add(tp);
        mainPanel.add(pp);
        addTimer();

        //resize window to fit panels
        this.pack();
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
