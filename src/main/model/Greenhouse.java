package model;


import model.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a greenhouse with an owner, wallet, debt, pots, plants, and various time fields.

public class Greenhouse implements Writable {
    private final String owner;

    private int wallet;
    private int debt;

    private int pots;
    private final List<Plant> plants;

    private int greenhouseTime;
    private final long referenceTime;
    private int day;

    private int gameSpeed = 100;

    // REQUIRES: owner is a non-empty string, and currentTime >= 0
    // MODIFIES: this
    //   EFFECT: creates Greenhouse object with plants being an empty list, wallet with 100 dollars, and 0 seeds.
    public Greenhouse(String name, long currentTime) {
        this.owner = name;
        this.wallet = 100;
        this.debt = 10000;
        this.pots = 3;
        this.plants = new ArrayList<>();
        this.greenhouseTime = 0;
        this.referenceTime = currentTime;

    }

    // REQUIRES: owner is a non-empty string, all int values are >= 0, plants has to be a List that uses type Plant
    // MODIFIES: this
    //   EFFECT: creates Greenhouse object based on parameters obtained from jsonObject.
    public Greenhouse(String owner,
                      int wallet, int debt, int pots, int greenhouseTime, long referenceTime, List<Plant> plants) {
        this.owner = owner;
        this.wallet = wallet;
        this.debt = debt;
        this.pots = pots;
        this.greenhouseTime = greenhouseTime;
        this.referenceTime = referenceTime - ((long) greenhouseTime * gameSpeed);
        this.plants = plants;
    }

    // REQUIRES: currentTime > greenhouseTime
    // MODIFIES: this
    //   EFFECT: updates the epoch time of the green house by taking the difference between the current epoch time in
    //           milliseconds and the reference epoch time in milliseconds and dividing by gameSpeed
    public void updateTime(long currentTime) {
        greenhouseTime = (int) (currentTime - referenceTime) / gameSpeed;
        this.day = this.greenhouseTime / 10;
        this.updatePlants();
    }

    // MODIFIES: this
    //   EFFECT: if wallet has more than or equal to 100 dollars, add 1 to pots and subtract 100 from wallet.
    //           Throws InsufficientFundsException if wallet < 100.
    public void buyPots() throws InsufficientFundsException {
        if (wallet >= 100) {
            pots += 1;
            wallet -= 100;
        } else {
            throw new InsufficientFundsException();
        }
    }

    //   EFFECT: returns the difference of pots minus the number of plants.
    public int availablePots() {
        return this.pots - this.plants.size();
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    //   EFFECT: creates a Plant object with a name and timePlanted equal to name and greenhouseTime and adds it to
    //           Plants.
    //           Throws InsufficientFundsException if wallet < 10.
    //           Throws DuplicatePlantException if there is already a plant in plants with the same name as parameter.
    //           Throws InsufficientSpaceException if this.availablePots() equals 0.
    public void buyPlant(String name)
            throws InsufficientFundsException, InsufficientSpaceException, DuplicatePlantException {
        if (this.wallet >= 10) {
            if (!Objects.isNull(this.getPlant(name))) {
                throw new DuplicatePlantException();
            }
            if (this.availablePots() == 0) {
                throw new InsufficientSpaceException();
            }
            this.plants.add(new Plant(name, this.greenhouseTime));
            this.wallet -= 10;
        } else {
            throw new InsufficientFundsException();
        }
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    //   EFFECT: creates a Cactus object with a name and timePlanted equal to name and greenhouseTime and adds it to
    //           Plants.
    //           Throws InsufficientFundsException if wallet < 10.
    //           Throws DuplicatePlantException if there is already a plant in plants with the same name as parameter.
    //           Throws InsufficientSpaceException if this.availablePots() equals 0.
    public void buyCactus(String name)
            throws InsufficientFundsException, InsufficientSpaceException, DuplicatePlantException {
        if (this.wallet >= 10) {
            if (!Objects.isNull(this.getPlant(name))) {
                throw new DuplicatePlantException();
            }
            if (this.availablePots() == 0) {
                throw new InsufficientSpaceException();
            }
            this.plants.add(new Cactus(name, this.greenhouseTime));
            this.wallet -= 10;
        } else {
            throw new InsufficientFundsException();
        }
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: this
    //   EFFECT: creates a Flower object with a name and timePlanted equal to name and greenhouseTime and adds it to
    //           Plants.
    //           Throws InsufficientFundsException if wallet < 50.
    //           Throws DuplicatePlantException if there is already a plant in plants with the same name as parameter.
    //           Throws InsufficientSpaceException if this.availablePots() equals 0.
    public void buyFlower(String name)
            throws InsufficientFundsException, InsufficientSpaceException, DuplicatePlantException {
        if (this.wallet >= 50) {
            if (!Objects.isNull(this.getPlant(name))) {
                throw new DuplicatePlantException();
            }
            if (this.availablePots() == 0) {
                throw new InsufficientSpaceException();
            }
            this.plants.add(new Flower(name, this.greenhouseTime));
            this.wallet -= 50;
        } else {
            throw new InsufficientFundsException();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: executes grow method on each plant in plants list. If a plant has a hydration level of zero, it is
    //           removed from plants list.
    public void updatePlants() {
        List<Plant> plantsToRemove = new ArrayList<>();
        for (Plant plant : this.plants) {
            if (plant.getHydration() == 0) {
                plantsToRemove.add(plant);
            } else {
                plant.grow(this.greenhouseTime);
            }
        }
        this.plants.removeAll(plantsToRemove);
    }

    // MODIFIES: this
    //   EFFECT: finds plant based on given name in plants list and executes waterPlant method.
    //           Throws NonexistentPlantException if no plant with given name exists in plants list.
    public void waterPlant(String name) throws NonexistentPlantException {
        Plant plant = getPlant(name);
        if (plant == null) {
            throw new NonexistentPlantException();
        }
        plant.waterPlant(this.greenhouseTime);
    }

    // MODIFIES: this
    //   EFFECT: if the name matches a plant with the same name, the plant is removed from plants list, sale price is
    //           calculated, and added to wallet.
    //           Throws NonexistentPlantException if no plant with given name exists in plants list.
    public void sellPlant(String name) throws NonexistentPlantException {
        Plant plant = this.getPlant(name);
        if (plant == null) {
            throw new NonexistentPlantException();
        }
        this.plants.remove(plant);
        wallet += plant.salePrice();
    }

    //   EFFECT: Returns Plant object in plants list with matching name. Returns null, if no plant of that name exists.
    public Plant getPlant(String name) {
        for (Plant plant : this.plants) {
            if (name.equals(plant.getName())) {
                return plant;
            }
        }
        return null;
    }

    // MODIFIES: this
    //   EFFECT: Pays off debt using money from wallet.
    //           Throws InvalidAmountException if negative value is passed for amount parameter.
    //           Throws InsufficientFundsException if amount parameter is greater than wallet.
    public void payDebt(int amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException();
        }
        if (amount > this.wallet) {
            throw new InsufficientFundsException();
        } else {
            if (this.debt < amount) {
                this.wallet -= this.debt;
                this.debt = 0;
            } else {
                this.wallet -= amount;
                this.debt -= amount;
            }
        }
    }

    //   EFFECT: Returns false if debt is greater than 0, otherwise returns true.
    public boolean isDebtPaidOff() {
        return this.debt <= 0;
    }

    //   EFFECTS: returns greenhouse object as JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("owner", this.owner);
        json.put("wallet", this.wallet);
        json.put("debt", this.debt);
        json.put("pots", this.pots);
        json.put("plants", this.plantsToJson());
        json.put("greenhouseTime", this.greenhouseTime);
        return json;
    }

    //   EFFECTS: returns plants list as a JSON array.
    private JSONArray plantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Plant plant : this.plants) {
            jsonArray.put(plant.toJson());
        }

        return jsonArray;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getDay() {
        return day;
    }

    public int getWallet() {
        return this.wallet;
    }

    public void setWallet(int amount) {
        this.wallet = amount;
    }

    public int getDebt() {
        return this.debt;
    }

    public int getPots() {
        return this.pots;
    }

    public List<Plant> getPlants() {
        return this.plants;
    }

    public int getNumPlants() {
        return this.plants.size();
    }

    public int getTime() {
        return this.greenhouseTime;
    }
}
