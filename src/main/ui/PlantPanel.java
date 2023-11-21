package ui;

import model.Flower;
import model.Greenhouse;
import model.Plant;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.PrivateKey;

public class PlantPanel extends JPanel {
    private Plant plant;
    private Greenhouse gh;

    static final String HYDRATION = "Hydration: ";
    static final String AGE = "Age: ";
    static final String SALE_PRICE = "Sale Price: ";

    private JLabel typeLabel;
    private JLabel hydrationLabel;
    private JLabel ageLabel;
    private JLabel salePriceLabel;

    public PlantPanel(Plant p, Greenhouse gh) {
        this.plant = p;
        this.gh = gh;

        typeLabel = new JLabel(getPlantType());
        hydrationLabel = new JLabel(HYDRATION + plant.getHydration());
        ageLabel = new JLabel(AGE + plant.getAge());
        salePriceLabel = new JLabel(SALE_PRICE + plant.salePrice());

        add(typeLabel);
        add(hydrationLabel);
        add(ageLabel);
        add(salePriceLabel);
        addPopupMenu();
    }

    public void update() {
        typeLabel.setText(getPlantType());
        hydrationLabel.setText(HYDRATION + plant.getHydration());
        ageLabel.setText(AGE + plant.getAge());
        salePriceLabel.setText(SALE_PRICE + plant.salePrice());
        repaint();
    }

    public boolean checkHydration() {
        return plant.getHydration() > 0;
    }

    private String getPlantType() {
        if (plant.getType().equals("Flower")) {
            Flower flower = (Flower) plant;
            if (!flower.getColour().equals("None")) {
                return flower.getColour() + " " + plant.getType();
            } else {
                return plant.getType();
            }
        } else {
            return plant.getType();
        }

    }

    private void addPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem waterPlantOption  = new JMenuItem("Water Plant");
        waterPlantOption.addActionListener(e -> {
            this.gh.waterPlant(plant.getName());
        });

        JMenuItem sellPlantOption = new JMenuItem("Sell Plant");
        sellPlantOption.addActionListener(e -> {
            this.gh.sellPlant(plant.getName());
        });

        popupMenu.add(waterPlantOption);
        popupMenu.add(sellPlantOption);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}
