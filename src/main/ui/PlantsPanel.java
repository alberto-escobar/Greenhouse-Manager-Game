package ui;

import model.Greenhouse;
import model.Plant;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Represents a panel that contains all PlantPanel objects
public class PlantsPanel extends JPanel {
    Greenhouse gh;
    List<PlantPanel> panelList;

    // REQUIRES: gh is non-null
    // MODIFIES: this
    //  EFFECTS: Creates a PlantsPanel based on the information from gh
    public PlantsPanel(Greenhouse gh) {
        this.gh = gh;
        List<Plant> ghPlants = gh.getPlants();
        panelList = new ArrayList<>();

        JPanel mainPanel = new JPanel();

        for (Plant p : ghPlants) {
            PlantPanel panel = new PlantPanel(p,gh);
            panelList.add(panel);
            mainPanel.add(panel);
        }
        setPreferredSize(new Dimension(200, 200));
        add(mainPanel);
    }

    // MODIFIES: this
    //  EFFECTS: Updates all the PlantPanel components contained in this panel.
    public void update() {
        List<Plant> ghPlants = gh.getPlants();
        if (ghPlants.size() != panelList.size()) {
            remakePanel();
        } else {
            for (PlantPanel panel : panelList) {
                panel.update();
            }
        }
    }

    // MODIFIES: this
    //  EFFECTS: Remakes the PlantsPanel
    public void remakePanel() {
        removeAll();
        List<Plant> ghPlants = gh.getPlants();
        panelList = new ArrayList<>();
        JPanel mainPanel = new JPanel();

        for (Plant p : ghPlants) {
            PlantPanel panel = new PlantPanel(p,gh);
            panelList.add(panel);
            mainPanel.add(panel);
        }

        add(mainPanel);
        repaint();
    }
}
