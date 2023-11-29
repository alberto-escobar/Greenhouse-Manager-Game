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
    List<Plant> ghPlants;
    List<PlantPanel> panelList;

    // REQUIRES: gh is non-null
    // MODIFIES: this
    //  EFFECTS: Creates a PlantsPanel based on the information from gh
    public PlantsPanel(Greenhouse gh) {
        this.gh = gh;
        this.ghPlants = gh.getPlants();
        setLayout(new GridLayout(3,3,0,0));
        constructPanel();
        setPreferredSize(new Dimension(1000, 1000));
    }

    // MODIFIES: this
    //  EFFECTS: Updates all the PlantPanel components contained in this panel.
    public void update() {
        if (this.ghPlants.size() != this.panelList.size()) {
            removeAll();
            constructPanel();
        } else {
            for (PlantPanel panel : panelList) {
                panel.update();
            }
        }
    }

    // MODIFIES: this
    //  EFFECTS: Remakes the PlantsPanel
    public void constructPanel() {
        panelList = new ArrayList<>();
        for (Plant p : ghPlants) {
            PlantPanel panel = new PlantPanel(p,gh);
            panelList.add(panel);
            add(panel);
        }
        repaint();
    }
}
