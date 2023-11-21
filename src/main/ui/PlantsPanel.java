package ui;

import model.Greenhouse;
import model.Plant;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlantsPanel extends JPanel {
    Greenhouse gh;
    List<PlantPanel> panelList;

    public PlantsPanel(Greenhouse gh) {
        this.gh = gh;
        List<Plant> ghPlants = gh.getPlants();
        panelList = new ArrayList<>();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Plant p : ghPlants) {
            PlantPanel panel = new PlantPanel(p,gh);
            panelList.add(panel);
            mainPanel.add(panel);
        }
        setPreferredSize(new Dimension(200, 200));
        add(mainPanel);
    }

    public void update() {
        //have a way to check if new plant is bought and then add panel
        List<Plant> ghPlants = gh.getPlants();
        if (ghPlants.size() != panelList.size()) {
            remakePanel();
        } else {
            for (PlantPanel panel : panelList) {
                panel.update();
            }
        }
    }

    public void remakePanel() {
        removeAll();
        List<Plant> ghPlants = gh.getPlants();
        panelList = new ArrayList<>();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Plant p : ghPlants) {
            PlantPanel panel = new PlantPanel(p,gh);
            panelList.add(panel);
            mainPanel.add(panel);
        }

        add(mainPanel);
        repaint();
    }
}
