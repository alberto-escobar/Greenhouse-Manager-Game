package model;

// Represents a plant with a name, type, age, and hydration level. Other fields are timePlanted and timeHydrated which
// help determine age and hydration level. More fields are used to determine how quickly plant ages, dehydrates, and
// changes in value over time.

import org.json.JSONObject;
import persistence.Writable;

public class Plant implements Writable {
    protected String name;
    protected String type;
    protected int age;
    protected int hydration;

    protected int timePlanted;
    protected int timeHydrated;

    protected int growthRate = 120;
    protected int dehydrationRate = 5;
    protected int minAgeToSell = 2;
    protected int maxAgeToValue = 10;

    // REQUIRES: name is a non-empty string, and currentTime >= 0
    // MODIFIES: this
    //   EFFECT: creates a Plant object with name, 100% hydration, and 0 age.
    public Plant(String name, int currentTime) {
        this.name = name;
        this.type = "Plant";
        this.timePlanted = currentTime;
        this.timeHydrated = currentTime;
        this.age = 0;
        this.hydration = 100;
    }

    // REQUIRES: jsonObject has to have the same schema as described in toJson method
    // MODIFIES: this
    //   EFFECT: creates a Plant object with relevant fields equal to the data in jsonObject.
    public Plant(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.type = jsonObject.getString("type");
        this.timePlanted = jsonObject.getInt("timePlanted");
        this.timeHydrated = jsonObject.getInt("timeHydrated");
        this.age = jsonObject.getInt("age");
        this.hydration = jsonObject.getInt("hydration");
    }

    // REQUIRES: currentTime >= timePlanted and currentTime >= timeHydrated
    // MODIFIES: this
    //   EFFECT: age increases by 1 for every growthRate increment since plant was created (timePlanted).
    //           Hydration level reduces by 1 for every dehydrationRate increment since plant was last watered
    //           (timeHydrated).
    public void grow(int currentTime) {
        int timePassed = currentTime - this.timePlanted;
        this.age = timePassed / growthRate;

        int timeSinceLastWater = currentTime - this.timeHydrated;
        this.hydration = 100 - timeSinceLastWater / this.dehydrationRate;
        if (this.hydration < 0) {
            this.hydration = 0;
        }
    }

    // REQUIRES: currentTime >= timeHydrated
    // MODIFIES: this
    //   EFFECT: timeHydrated updated to currentTime and hydration level set to 100.
    public void waterPlant(int currentTime) {
        this.timeHydrated = currentTime;
        this.hydration = 100;
    }

    //   EFFECT: returns the sale price of the plant. The sale price depends on the age of the plant and if the plant
    //           is at or above its minimum age of sale (minAgeOfSale) and below the maxAgeOfValue.
    public int salePrice() {
        if (this.age < this.minAgeToSell) {
            return 10;
        } else if (this.age > this.maxAgeToValue) {
            return 20 * this.maxAgeToValue + 10;
        } else {
            return 20 * this.age + 10;
        }
    }

    //   EFFECT: returns plant object as JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("type", this.type);
        json.put("age", this.age);
        json.put("hydration", this.hydration);
        json.put("timePlanted", this.timePlanted);
        json.put("timeHydrated", this.timeHydrated);
        return json;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public int getHydration() {
        return hydration;
    }

    public int getTimePlanted() {
        return timePlanted;
    }

    public int getTimeHydrated() {
        return timeHydrated;
    }

    public int getGrowthRate() {
        return growthRate;
    }

    public int getDehydrationRate() {
        return dehydrationRate;
    }

    public int getMinAgeToSell() {
        return minAgeToSell;
    }
}
