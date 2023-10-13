package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a greenhouse with a wallet, seeds, and list of plants.

public class Greenhouse {
    List<Plant> plants;
    int wallet;
    int seeds;

    // MODIFIES: this
    //   EFFECT: creates Greenhouse object with plants being an empty list, wallet with 100 dollars, and 0 seeds.
    public Greenhouse() {
        this.plants = new ArrayList<>();
        this.wallet = 100;
        this.seeds = 0;
    }

    // MODIFIES: this
    //   EFFECT: if wallet has more than 10 dollars, add 1 to seeds and subtract 10 from wallet
    public boolean buySeed() {
        if (wallet >= 10) {
            seeds += 1;
            wallet -= 10;
            return true;
        }
        return false;
    }

    // REQUIRES: name is non-empty string and is not a plant name in plants list, time > 0
    // MODIFIES: this
    //   EFFECT: creates a Plant object with a name and timePlanted equal to name and timeCurrent and adds it to Plants
    //           and returns true. If there is a plant in plants with the same name, the newly created plant will not
    //           be added to plants and the operation returns false.
    public boolean plantSeed(String name, int currentTime) {
        if (this.seeds > 0 && Objects.isNull(this.getPlant(name))) {
            this.plants.add(new Plant(name, currentTime));
            seeds -= 1;
            return true;
        }
        return false;
    }

    // REQUIRES: time >= timePlanted and timeHydrated for all plant objects in plants
    // MODIFIES: this
    //   EFFECT: executes grow method on each plant in plants using currentTime
    public void updatePlants(int currentTime) {
        for (Plant plant : plants) {
            plant.grow(currentTime);
        }
    }

    // REQUIRES: name is the name of a plant in plants list, time >= timeHydrated
    // MODIFIES: this
    //   EFFECT: finds plant based on given name in plants list and executes waterPlant method on it using currentTime
    //           and returns true. If no plant exists with the given name in plants list, the method returns false.
    public boolean waterPlant(String name, int currentTime) {
        Plant plant = getPlant(name);
        if (!Objects.isNull(plant)) {
            plant.waterPlant(currentTime);
        }
        return false;
    }

    // REQUIRES: name is the name of a plant in plants list
    // MODIFIES: this
    //   EFFECT: if the name matches a plan with the same name, the plant is removed from the Greenhouse, sale price is
    //           calculated based on plant's age, and added to wallet, and method returns true. If no plant with given
    //           name exists in plants list, the method returns false.
    public boolean sellPlant(String name) {
        Plant plant = this.getPlant(name);
        if (this.plants.remove(plant)) {
            wallet += 20 * plant.getAge() + 10;
            return true;
        }
        return false;
    }

    // REQUIRES: name is the name of a plant in plants list
    //   EFFECT: Returns Plant object in plants list with matching name, if no plant of that name exists, returns null.
    public Plant getPlant(String name) {
        for (Plant plant : this.plants) {
            if (name.equals(plant.getName())) {
                return plant;
            }
        }
        return null;
    }

    public int getWallet() {
        return this.wallet;
    }

    public int getSeeds() {
        return this.seeds;
    }

    public List<Plant> getPlants() {
        return this.plants;
    }
}
