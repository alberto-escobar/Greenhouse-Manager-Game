package model;


import model.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a greenhouse with an owner, wallet, debt, pots, plants, and time fields

public class Greenhouse implements Writable {
    private String owner;

    private int wallet;
    private int debt;

    private int pots;
    private List<Plant> plants;

    private int greenhouseTime;
    private long referenceTime;


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

    public Greenhouse(String owner, int wallet, int debt, int pots, int greenhouseTime, long referenceTime, List<Plant> plants) {
        this.owner = owner;
        this.wallet = wallet;
        this.debt = debt;
        this.pots = pots;
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
    //   EFFECT: if wallet has more than 10 dollars, add 1 to seeds and subtract 10 from wallet.
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

    // MODIFIES: this
    //   EFFECT: creates a Plant object with a name and timePlanted equal to name and greenhouseTime and adds it to
    //           Plants.
    //           Throws InsufficientFundsException if wallet < 10.
    //           Throws DuplicatePlantException if there is already a plant in plants with the same name as parameter
    //           Throws InsufficientSpaceException if this.availablePots() equals 0
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

    // REQUIRES: nothing
    // MODIFIES: this
    //   EFFECT: executes grow method on each plant in plants using greenhouseTime
    public void updatePlants() {
        for (Plant plant : plants) {
            plant.grow(this.greenhouseTime);
        }
    }

    // MODIFIES: this
    //   EFFECT: finds plant based on given name in plants list and executes waterPlant method using greenhouseTime
    //           and returns true. If no plant exists with the given name in plants list, the method returns false.
    public boolean waterPlant(String name) {
        Plant plant = getPlant(name);
        if (!Objects.isNull(plant)) {
            plant.waterPlant(this.greenhouseTime);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    //   EFFECT: if the name matches a plant with the same name, the plant is removed from the Greenhouse, sale price is
    //           calculated based on plant's age, and added to wallet, and method returns true. If no plant with given
    //           name exists in plants list, the method returns false.
    public boolean sellPlant(String name) {
        Plant plant = this.getPlant(name);
        if (this.plants.remove(plant)) {
            wallet += plant.salePrice();
            return true;
        }
        return false;
    }

    //   EFFECT: Returns Plant object in plants list with matching name returns null, if no plant of that name exists.
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

    //   EFFECT: Returns false if debt is greater than 0, otherwise return true.
    public boolean isDebtPaidOff() {
        if (this.debt > 0) {
            return false;
        }
        return true;
    }

    //   EFFECTS: returns greenhouse object as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("owner", this.owner);
        json.put("wallet", this.wallet);
        json.put("debt", this.debt);
        json.put("pots", this.pots);
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

    public String getOwner() {
        return this.owner;
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

    public void setTime(int time) {
        this.greenhouseTime = time;
    }
}
