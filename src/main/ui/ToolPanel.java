package ui;

import model.Greenhouse;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class ToolPanel extends JPanel {
    private Greenhouse gh;
    JButton buyCactusButton;
    JButton buyFlowerButton;
    JButton buyPotsButton;
    JButton payDebtButton;
    JButton saveGameButton;

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
        if (this.gh.isDebtPaidOff()) {
            payDebtButton.setEnabled(false);
        }

        saveGameButton = new JButton("Save Game");
        saveGameButton.addActionListener((ActionEvent ae) ->
                this.saveGameCommand()
        );
        add(buyCactusButton);
        add(buyFlowerButton);
        add(buyPotsButton);
        add(payDebtButton);
        add(saveGameButton);
    }


    public void buyCactusCommand() {
        try {
            this.gh.buyCactus(this.getRandomString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buyFlowerCommand() {
        try {
            this.gh.buyFlower(this.getRandomString());
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
            String userInput = JOptionPane.showInputDialog("Enter amount you want to pay:", null);
            if (userInput != null) {
                this.gh.payDebt(Integer.parseInt((userInput)));
                if (this.gh.isDebtPaidOff()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "You have won the game, it took you " + this.gh.getDay() + " in-game days to pay your debt!",
                            "Congrats",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    payDebtButton.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveGameCommand() {
        try {
            String savePath = "./data/" + gh.getOwner() + ".json";
            JsonWriter jsonWriter = new JsonWriter(savePath);
            jsonWriter.open();
            jsonWriter.write(gh);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved " + gh.getOwner() + " to " + savePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private String getRandomString() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
