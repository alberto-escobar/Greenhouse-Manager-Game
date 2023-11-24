package ui;

import model.Greenhouse;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Green house manager GUI game
public class GuiGame extends JFrame {
    Greenhouse gh;
    JPanel mainPanel;
    ScorePanel sp;
    ToolPanel tp;
    PlantsPanel pp;

    // MODIFIES: this
    //  EFFECTS: sets up window in which Green House game will be played
    public GuiGame() {
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
        this.setTheme();
    }

    // MODIFIES: this
    //  EFFECTS: sets up JFrame to have default theme used in windows.
    private void setTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    //  EFFECTS: asks user for a name and creates a new Greenhouse and sets up associated game panels.
    private void newGameCommand() {
        String name = JOptionPane.showInputDialog(null, "Please enter name for new game:");
        long currentTime = System.currentTimeMillis();
        gh = new Greenhouse(name, currentTime);
        mainPanel.removeAll();
        createGamePanels(gh);
    }

    // MODIFIES: this
    //  EFFECTS: asks user for a name of save file and load Greenhouse file and sets up associated game panels.

    private void loadGameCommand() {
        String name = JOptionPane.showInputDialog(null, "Please enter name of game save to load:");
        String savePath = "./data/" + name + ".json";
        try {
            JsonReader jsonReader = new JsonReader(savePath);
            this.gh = jsonReader.read();

            mainPanel.removeAll();
            createGamePanels(gh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load following file:\n" + savePath);
        }
    }

    // MODIFIES: this
    //  EFFECTS: Creates game panels based on Greenhouse
    private void createGamePanels(Greenhouse gh) {
        sp = new ScorePanel(gh);
        tp = new ToolPanel(gh);
        pp = new PlantsPanel(gh);

        mainPanel.add(sp);
        mainPanel.add(tp);
        mainPanel.add(pp);
        addTimer();

        this.pack();
    }

    // MODIFIES: this
    //  EFFECTS: updates game panels.
    private void updateGamePanels() {
        long currentTime = System.currentTimeMillis();
        gh.updateTime(currentTime);
        sp.update();
        pp.update();
    }

    // MODIFIES: none
    //  EFFECTS:  initializes a timer that updates greenhouse and associated game panels every 100 milliseconds.
    private void addTimer() {
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateGamePanels();
            }
        });

        t.start();
    }

    public static void main(String[] args) {
        new GuiGame();
    }
}
