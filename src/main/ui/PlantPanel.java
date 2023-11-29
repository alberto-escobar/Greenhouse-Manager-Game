package ui;

import model.Flower;
import model.Greenhouse;
import model.Plant;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


//Represents a panel that represents information of a Plant object
public class PlantPanel extends JPanel {
    private Plant plant;
    private Greenhouse gh;

    static final String HYDRATION = "Hydration: ";
    static final String AGE = "Age: ";
    static final String SALE_PRICE = "Sale Price: ";
    private JLabel imageLabel;
    private JLabel typeLabel;
    private JLabel hydrationLabel;
    private JLabel ageLabel;
    private JLabel salePriceLabel;

    // REQUIRES: p and gh are non-null
    // MODIFIES: this
    //  EFFECTS: Creates a PlantPanel based on the information from Plant.
    public PlantPanel(Plant p, Greenhouse gh) {
        this.plant = p;
        this.gh = gh;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        imageLabel = new JLabel(generateImage());
        typeLabel = new JLabel(getPlantType());
        hydrationLabel = new JLabel(HYDRATION + plant.getHydration());
        ageLabel = new JLabel(AGE + plant.getAge());
        salePriceLabel = new JLabel(SALE_PRICE + plant.salePrice());

        add(imageLabel);
        add(typeLabel);
        add(hydrationLabel);
        add(ageLabel);
        add(salePriceLabel);
        addPopupMenu();
        setPreferredSize(new Dimension(100, 100));
    }

    // MODIFIES: this
    //  EFFECTS: Updates PlantPanel components with information from Plant.
    public void update() {
        imageLabel.setIcon(generateImage());
        typeLabel.setText(getPlantType());
        hydrationLabel.setText(HYDRATION + plant.getHydration());
        ageLabel.setText(AGE + plant.getAge());
        salePriceLabel.setText(SALE_PRICE + plant.salePrice());
        if (plant.getHydration() < 15) {
            setBackground(ColorUIResource.red);
        } else {
            setBackground(UIManager.getColor("Panel.background"));
        }
        repaint();
    }

    // EFFECTS: Returns a ImageIcon based on the Plants type and age.
    private ImageIcon generateImage() {
        int imageAge = plant.getAge();
        if (imageAge > plant.getMinAgeToSell()) {
            imageAge = plant.getMinAgeToSell();
        }

        String imageColour = "";
        if (plant.getType().equals("Flower")) {
            Flower flower = (Flower) plant;
            if (!flower.getColour().equals("None")) {
                imageColour = flower.getColour();
            }
        }
        String filepath = "./resources/" + plant.getType() + imageAge + imageColour + ".png";
        int width = 100;
        int height = 100;
        try {
            ImageIcon plantIcon = new ImageIcon(filepath);
            Image image = plantIcon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            plantIcon = new ImageIcon(resizedImage);
            return plantIcon;
        } catch (Exception e) {
            return null;
        }
    }

    //  EFFECT: returns a string containing the Plant type and Colour, if present.
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

    // MODIFIES: this
    //  EFFECTS: adds popup menu to PlantPanel that is accessed when PlantPanel component is right clicked.
    private void addPopupMenu() {
        JPopupMenu popupMenu = this.createPlantPopMenu();

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

    // MODIFIES: this
    //  EFFECTS: creates a JPopupMenu with the option to water Plant and sell Plant
    private JPopupMenu createPlantPopMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem waterPlantOption  = new JMenuItem("Water Plant");
        waterPlantOption.addActionListener(a -> {
            try {
                this.gh.waterPlant(plant.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });

        JMenuItem sellPlantOption = new JMenuItem("Sell Plant");
        sellPlantOption.addActionListener(a -> {
            try {
                this.gh.sellPlant(plant.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });

        popupMenu.add(waterPlantOption);
        popupMenu.add(sellPlantOption);
        return popupMenu;
    }
}
