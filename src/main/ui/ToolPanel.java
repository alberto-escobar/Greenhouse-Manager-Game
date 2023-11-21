package ui;

import model.Greenhouse;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToolPanel extends JPanel {
    private Greenhouse gh;
    JButton buyCactusButton;
    JButton buyFlowerButton;
    JButton buyPotsButton;
    JButton payDebtButton;

    public ToolPanel(Greenhouse gh) {
        this.gh = gh;
        buyCactusButton = new JButton("Buy Cactus");
        buyCactusButton.addActionListener((ActionEvent ae) ->
                this.buyCactusCommand()
        );

        buyFlowerButton = new JButton("Buy Flower");
        buyFlowerButton.addActionListener((ActionEvent ae) ->
                this.buyFlowerCommand()
        );

        buyPotsButton = new JButton("Buy Pot");
        buyPotsButton.addActionListener((ActionEvent ae) ->
                this.buyPotsCommand()
        );

        payDebtButton = new JButton("Pay Debt");
        payDebtButton.addActionListener((ActionEvent ae) ->
                this.payDebtCommand()
        );
        add(buyCactusButton);
        add(buyFlowerButton);
        add(buyPotsButton);
        add(payDebtButton);
    }


    public void buyCactusCommand() {
        try {
            this.gh.buyCactus("Cactus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buyFlowerCommand() {
        try {
            this.gh.buyFlower("Flower");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buyPotsCommand() {
        try {
            this.gh.buyPots();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



    public void payDebtCommand() {
        try {
            String userInput = JOptionPane.showInputDialog(null, "Enter amount you want to pay:");
            if (userInput.length() > 0) {
                this.gh.payDebt(Integer.parseInt((userInput)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
