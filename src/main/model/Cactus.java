package model;

import org.json.JSONObject;

// Represents a cactus which extends Plant class.
public class Cactus extends Plant {

    // REQUIRES: name is a non-empty string, and currentTime >= 0
    // MODIFIES: this
    //   EFFECT: creates a Cactus object with name, 100% hydration, and 0 age, and Colour = to "None". Growing
    //           parameters are changed to make plant age slowly and require less frequent watering.
    public Cactus(String name, int currentTime) {
        super(name, currentTime);
        this.type = "Cactus";
        this.growthRate = 180;
        this.dehydrationRate = 10;
        this.minAgeToSell = 6;
    }

    // REQUIRES: jsonObject has to have the same schema as described in toJson method
    // MODIFIES: this
    //   EFFECT: creates a Cactus object with relevant fields equal to the data in jsonObject.
    public Cactus(JSONObject jsonObject) {
        super(jsonObject);
        this.type = "Cactus";
        this.growthRate = 180;
        this.dehydrationRate = 10;
        this.minAgeToSell = 6;
    }
}
