package ui;

import model.Flower;
import model.Plant;

import javax.swing.*;
import java.security.PrivateKey;

public class PlantPanel extends JPanel {
    private Plant plant;

    static final String HYDRATION = "Hydration: ";
    static final String AGE = "Age: ";
    static final String SALE_PRICE = "Sale Price: ";

    private JLabel typeLabel;
    private JLabel hydrationLabel;
    private JLabel ageLabel;
    private JLabel salePriceLabel;

    public PlantPanel(Plant p) {
        this.plant = p;

        if (plant.getType().equals("Flower")) {
            Flower flower = (Flower) plant;
            if (!flower.getColour().equals("None")) {
                typeLabel = new JLabel(flower.getColour() + " " + plant.getType());
            } else {
                typeLabel = new JLabel(plant.getType());
            }
        } else {
            typeLabel = new JLabel(plant.getType());
        }

        typeLabel = new JLabel(plant.getType());
        hydrationLabel = new JLabel(HYDRATION + plant.getHydration());
        ageLabel = new JLabel(AGE + plant.getAge());
        salePriceLabel = new JLabel(SALE_PRICE + plant.salePrice());

        add(typeLabel);
        add(hydrationLabel);
        add(ageLabel);
        add(salePriceLabel);
    }

    public void update() {
        if (plant.getType().equals("Flower")) {
            Flower flower = (Flower) plant;
            if (!flower.getColour().equals("None")) {
                typeLabel.setText(flower.getColour() + " " + plant.getType());
            } else {
                typeLabel.setText(plant.getType());
            }
        } else {
            typeLabel.setText(plant.getType());
        }
        hydrationLabel.setText(HYDRATION + plant.getHydration());
        ageLabel.setText(AGE + plant.getAge());
        salePriceLabel.setText(SALE_PRICE + plant.salePrice());
        repaint();
    }

    public boolean checkHydration() {
        return plant.getHydration() > 0;
    }
}
