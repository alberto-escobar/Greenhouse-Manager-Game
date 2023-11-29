package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a flower which extends Plant class has one additional field that dictates colour of flower when it blooms.
// Colour of Flower influences sale price.

public class Flower extends Plant {
    private String colour;
    private final List<String> colourList;

    // REQUIRES: name is a non-empty string, and currentTime >= 0
    // MODIFIES: this
    //   EFFECT: creates a Flower object with name, 100% hydration, and 0 age, and Colour = to "None". Growing
    //           parameters are changed to make plant age quickly but require more frequent watering.
    public Flower(String name, int currentTime) {
        super(name, currentTime);
        this.type = "Flower";
        this.growthRate = 90;
        this.dehydrationRate = 3;
        this.minAgeToSell = 4;
        this.colour = "None";
        this.colourList = generateColourList();
    }

    // REQUIRES: jsonObject has to have the same schema as described in toJson method
    // MODIFIES: this
    //   EFFECT: creates a Flower object with relevant fields equal to the data in jsonObject.
    public Flower(JSONObject jsonObject) {
        super(jsonObject);
        this.type = "Flower";
        this.growthRate = 90;
        this.dehydrationRate = 3;
        this.minAgeToSell = 4;
        this.colour = jsonObject.getString("colour");
        this.colourList = generateColourList();
    }

    //   EFFECT: Method returns Plant.salePrice() multiplied by a factor depending on the colour field.
    @Override
    public int salePrice() {
        int salePrice = super.salePrice();
        if (!this.colour.equals("None")) {
            return (salePrice * (this.colourList.indexOf(this.colour) + 1));
        } else {
            return salePrice;
        }
    }

    //   EFFECT: Same method as Plant.grow() except when Flower age reaches minAgeOfSale, it runs bloom method.
    @Override
    public void grow(int currentTime) {
        super.grow(currentTime);
        if (this.age >= this.minAgeToSell && this.colour.equals("None")) {
            this.bloom();
        }
    }

    //   EFFECT: Creates a list of possible blooming colors from order of least to most rare.
    private List<String> generateColourList() {
        List<String> list = new ArrayList<>();
        list.add("Red");
        list.add("Yellow");
        list.add("Pink");
        list.add("Black");
        return list;
    }

    // REQUIRES: age >= minAgeToSell
    // MODIFIES: this
    //   EFFECT: Randomizes colour for Flower based on available colours in colourList.
    private void bloom() {
        //random number generator
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        //if statement for setting color
        if (0 < randomNumber && randomNumber <= 5) {
            this.colour = this.colourList.get(3);
        } else if (5 < randomNumber && randomNumber <= 15) {
            this.colour = this.colourList.get(2);
        } else if (15 < randomNumber && randomNumber <= 30) {
            this.colour = this.colourList.get(1);
        } else {
            this.colour = this.colourList.get(0);
        }
        EventLog.getInstance().logEvent(new Event(this.name + " " + this.type + " has bloomed into a "
                + this.colour + " " + this.type));
    }

    //   EFFECT: returns Flower object as JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("type", this.type);
        json.put("age", this.age);
        json.put("hydration", this.hydration);
        json.put("timePlanted", this.timePlanted);
        json.put("timeHydrated", this.timeHydrated);
        json.put("colour", this.colour);
        return json;
    }

    public String getColour() {
        return colour;
    }

    public List<String> getColourList() {
        return colourList;
    }
}
