package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Flower;
import model.Greenhouse;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;

// Greenhouse manager console game
public class ConsoleGame {
    private Scanner input;
    Greenhouse greenhouse;
    private String savePath;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    //   EFFECT: creates Game object, initializes scanner for keyboard commands, and opens the welcome prompt.
    public ConsoleGame() {
        this.input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Welcome to Greenhouse Manger 2023! \n Press s to start new game! press l to load save!");
        String command = input.next();
        if (command.equals("s")) {
            System.out.println("Please input a name for your Greenhouse:");
            String name = input.next();

            savePath = "./data/" + name + ".json";
            jsonWriter = new JsonWriter(savePath);

            long currentTime = System.currentTimeMillis();
            greenhouse = new Greenhouse(name, currentTime);
            run();
        } else if (command.equals("l")) {
            System.out.println("Please input a name of saved greenhouse");
            String name = input.next();
            savePath = "./data/" + name + ".json";
            jsonWriter = new JsonWriter(savePath);
            loadCommand(savePath);
            run();
        } else {
            System.out.println("good bye!");
        }
    }

    // MODIFIES: this
    //   EFFECT: runs main game and waits for user input to process command. If user enters "q", game quits.
    public void run() {
        boolean runGame = true;
        String command = null;

        while (runGame) {
            printGreenhouseCommand();
            command = input.next();
            if (command.equals("q")) {
                runGame = false;
            } else {
                this.processCommand(command);
            }
            if (this.greenhouse.isDebtPaidOff()) {
                runGame = false;
                System.out.println("You paid off your load, you have won!");
            }
        }
        System.out.println("good bye!");
    }

    // REQUIRES: String to be a string
    // MODIFIES: this
    //   EFFECT: Processes user command.
    public void processCommand(String command) {
        if (command.equals("")) {
            updateTime();
        } else if (command.equals("p")) {
            buyPlantCommand();
        } else if (command.equals("b")) {
            buyPotsCommand();
        } else if (command.equals("s")) {
            sellPlantCommand();
        } else if (command.equals("w")) {
            waterPlantCommand();
        } else if (command.equals("save")) {
            saveCommand();
        } else if (command.equals("d")) {
            payDebtCommand();
        } else if (command.equals("h")) {
            System.out.println(
                    "press enter to update game, "
                            + "b = buy pots, p = buy plant, s = sell plant, w = water plant, save = save game, "
                            + "d = pay debt");
        } else {
            System.out.println("invalid command, enter h for help");
        }
    }

    // MODIFIES: this
    //   EFFECT: updates current game time and updates plants in Greenhouse to current game time
    public void updateTime() {
        long currentTime = System.currentTimeMillis();
        greenhouse.updateTime(currentTime);
    }

    //   EFFECT: outputs wallet amount, seed amount, and list of plants along with their age and hydration level to
    //           terminal.
    public void printGreenhouseCommand() {
        String stats = "Day: " + greenhouse.getDay()
                + " Debt: $" + greenhouse.getDebt()
                + " Wallet: $" + greenhouse.getWallet() + " AvailablePots: " + greenhouse.availablePots();
        System.out.println(stats);
        String plants = "";
        if (greenhouse.getPlants().isEmpty()) {
            plants = "No plants";
        }
        for (Plant plant : greenhouse.getPlants()) {


            plants = plants + plant.getName()
                    + ": ("
                    + "Type: " + plant.getType()
                    + "     Age: " + plant.getAge()
                    + "     Hydration: " +  plant.getHydration()
                    + "     Sale Price: " +  plant.salePrice();
            if (plant.getType().equals("Flower")) {
                Flower flower = (Flower) plant;
                plants = plants
                        + "     colour: " +  (flower.getColour());
            }
            plants = plants + ")\n";

        }
        System.out.println(plants);

    }

    // MODIFIES: this
    //   EFFECT: Buys pots for Greenhouse.
    public void buyPotsCommand() {
        try {
            greenhouse.buyPots();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    //   EFFECT: Buys baby plant in Greenhouse.
    public void buyPlantCommand() {
        System.out.println("Do you want to buy a cactus or a flower?");
        String type = input.next();
        System.out.println(type);
        try {
            if (type.equals("cactus")) {
                this.buyCactusCommand();
            } else if (type.equals("flower")) {
                this.buyFlowerCommand();
            } else {
                System.out.println("Unknown plant.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    //   EFFECT: Buys baby cactus in Greenhouse.
    public void buyCactusCommand() {
        System.out.println("Enter name for new cactus:");
        String name = input.next();
        try {
            greenhouse.buyCactus(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    //   EFFECT: Buys baby flower in Greenhouse.
    public void buyFlowerCommand() {
        System.out.println("Enter name for new flower:");
        String name = input.next();
        try {
            greenhouse.buyFlower(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    //   EFFECT: asks user for name of plant to sell and sells plant.
    public void sellPlantCommand() {
        System.out.println("Which plant would you like to sell?");
        String name = input.next();
        try {
            greenhouse.sellPlant(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // MODIFIES: this
    //   EFFECT: asks user for plant name to water and waters plant
    public void waterPlantCommand() {
        System.out.println("Enter name of plant to water:");
        String name = input.next();
        try {
            greenhouse.waterPlant(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //   EFFECT: Saves current game to json file.
    public void saveCommand() {
        try {
            jsonWriter.open();
            jsonWriter.write(greenhouse);
            jsonWriter.close();
            System.out.println("Saved " + greenhouse.getOwner() + " to " + savePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + savePath);
        }
    }

    // MODIFIES: this
    //   EFFECT: loads game from json file located at savePath.
    public void loadCommand(String savePath) {
        try {
            jsonReader = new JsonReader(savePath);
            this.greenhouse = jsonReader.read();
            System.out.println("Loaded " + greenhouse.getOwner() + " from " + savePath);
            this.updateTime();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + savePath);
        }
    }

    // MODIFIES: this
    //   EFFECT: asks user for amount of money to pay debt in greenhouse.
    public void payDebtCommand() {
        try {
            System.out.println("How much debt do you want to pay off?");
            String amount = input.next();
            this.greenhouse.payDebt(Integer.valueOf(amount));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ConsoleGame();
    }
}
