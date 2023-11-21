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
            //message popup
        }
    }

    public void buyFlowerCommand() {
        try {
            this.gh.buyFlower("Flower");
        } catch (Exception e) {
            //message popup
        }
    }

    public void buyPotsCommand() {
        try {
            this.gh.buyPots();
        } catch (Exception e) {
            //message popup
        }
    }



    public void payDebtCommand() {
        try {
            this.gh.payDebt(100);
        } catch (Exception e) {
            //message popup
        }
    }
}
