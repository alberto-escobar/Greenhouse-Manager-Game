package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a greenhouse with a wallet, seeds, and list of plants.

public class Greenhouse implements Writable {
    String name;
    List<Plant> plants;
    int wallet;
    int seeds;
    int greenhouseTime;
    long referenceTime;

    // MODIFIES: this
    //   EFFECT: creates Greenhouse object with plants being an empty list, wallet with 100 dollars, and 0 seeds.
    public Greenhouse(String name, long currentTime) {
        this.name = name;
        this.plants = new ArrayList<>();
        this.wallet = 100;
        this.seeds = 0;
        this.greenhouseTime = 0;
        this.referenceTime = currentTime;
    }

    public Greenhouse(String name, int wallet, int seeds, int greenhouseTime, long referenceTime, List<Plant> plants) {
        this.name = name;
        this.wallet = wallet;
        this.seeds = seeds;
        this.greenhouseTime = greenhouseTime;
        this.referenceTime = referenceTime - (greenhouseTime * 1000);
        this.plants = plants;
    }

    // REQUIRES: currentTime > greenhouseTime
    // MODIFIES: this
    //   EFFECT: updates the epoch time of the green house by taking the difference between the current epoch time in
    //           milliseconds and the reference epoch time in milliseconds and dividing by 1000
    public void updateTime(long currentTime) {
        greenhouseTime = (int) (currentTime - referenceTime) / 1000;
        this.updatePlants();
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

    // REQUIRES: name is non-empty string and is not a plant name in plants list
    // MODIFIES: this
    //   EFFECT: creates a Plant object with a name and timePlanted equal to name and timeCurrent and adds it to Plants
    //           and returns true. If there is a plant in plants with the same name, the newly created plant will not
    //           be added to plants and the operation returns false.
    public boolean plantSeed(String name) {
        if (this.seeds > 0 && Objects.isNull(this.getPlant(name))) {
            this.plants.add(new Plant(name, this.greenhouseTime));
            seeds -= 1;
            return true;
        }
        return false;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: executes grow method on each plant in plants using currentTime
    public void updatePlants() {
        for (Plant plant : plants) {
            plant.grow(this.greenhouseTime);
        }
    }

    // REQUIRES: name is the name of a plant in plants list
    // MODIFIES: this
    //   EFFECT: finds plant based on given name in plants list and executes waterPlant method on it using currentTime
    //           and returns true. If no plant exists with the given name in plants list, the method returns false.
    public boolean waterPlant(String name) {
        Plant plant = getPlant(name);
        if (!Objects.isNull(plant)) {
            plant.waterPlant(this.greenhouseTime);
            return true;
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

    //   EFFECTS:
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("wallet", this.wallet);
        json.put("seeds", this.seeds);
        json.put("plants", plantsToJson());
        json.put("greenhouseTime", this.greenhouseTime);
        return json;
    }

    //   EFFECTS: returns plants in this greenhouse as a JSON array
    private JSONArray plantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Plant plant : plants) {
            jsonArray.put(plant.toJson());
        }

        return jsonArray;
    }

    public String getName() {
        return this.name;
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

    public int getTime() {
        return this.greenhouseTime;
    }
}
