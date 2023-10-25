package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.Greenhouse;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;

// Greenhouse manager game
public class Game {
    private Scanner input;
    Greenhouse greenhouse;
    private String savePath;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    //   EFFECT: creates Game object, initializes scanner for keyboard commands, and opens the welcome prompt.
    public Game() {
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
            plantSeedCommand();
        } else if (command.equals("b")) {
            buySeedCommand();
        } else if (command.equals("s")) {
            sellPlantCommand();
        } else if (command.equals("w")) {
            waterPlantCommand();
        } else if (command.equals("save")) {
            saveCommand();
        } else if (command.equals("h")) {
            System.out.println(
                    "press enter to update game, "
                            + "b = buy seeds, p = plant seeds, s = sell plant, w = water plant, save = save game");
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
        String stats = "Wallet: $" + greenhouse.getWallet() + " Seed: " + greenhouse.getSeeds();
        System.out.println(stats);
        String plants = "";
        if (greenhouse.getPlants().isEmpty()) {
            plants = "No plants";
        }
        for (Plant plant : greenhouse.getPlants()) {
            plants = plants + plant.getName()
                    + "(Age: " + plant.getAge() + " Hydration: " +  plant.getHydration() + ")   ";
        }
        System.out.println(plants);

    }

    // MODIFIES: this
    //   EFFECT: Asks user for new plant name and plants seed in Greenhouse.
    public void plantSeedCommand() {
        System.out.println("Enter name for new plant:");
        String name = input.next();
        if (!greenhouse.plantSeed(name)) {
            System.out.println("Error, either not enough seeds or duplicate name");
        }
    }

    // MODIFIES: this
    //   EFFECT: Buys seed in Greenhouse.
    public void buySeedCommand() {
        if (!greenhouse.buySeed()) {
            System.out.print("Not enough money");
        }
    }

    // MODIFIES: this
    //   EFFECT: asks user for name of plant to sell and sells plant.
    public void sellPlantCommand() {
        System.out.println("Which plant would you like to sell?");
        String name = input.next();
        if (!greenhouse.sellPlant(name)) {
            System.out.println("No plant with that name");
        }
    }

    // MODIFIES: this
    //   EFFECT: asks user for plant name to water and waters plant
    public void waterPlantCommand() {
        System.out.println("Enter name of plant to water:");
        String name = input.next();
        if (!greenhouse.waterPlant(name)) {
            System.out.println("Plant does not exist");
        }
    }

    public void saveCommand() {
        try {
            jsonWriter.open();
            jsonWriter.write(greenhouse);
            jsonWriter.close();
            System.out.println("Saved " + greenhouse.getName() + " to " + savePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + savePath);
        }
    }

    public void loadCommand(String savePath) {
        try {
            jsonReader = new JsonReader(savePath);
            this.greenhouse = jsonReader.read();
            System.out.println("Loaded " + greenhouse.getName() + " from " + savePath);
            this.updateTime();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + savePath);
        }
    }
}
