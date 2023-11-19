package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flower extends Plant {
    private String colour;
    private List<String> colourList;

    public Flower(String name, int currentTime) {
        super(name, currentTime);
        this.type = "Flower";
        this.growthRate = 90;
        this.dehydrationRate = 3;
        this.minAgeToSell = 4;
        this.colour = "None";
        this.colourList = generateColourList();
    }

    public Flower(String name, String type, int timePlanted, int timeHydrated, int age, int hydration, String colour) {
        super(name, type, timePlanted, timeHydrated, age, hydration);
        this.type = "Flower";
        this.growthRate = 90;
        this.dehydrationRate = 3;
        this.minAgeToSell = 4;
        this.colour = colour;
        this.colourList = generateColourList();
    }

    @Override
    public int salePrice() {
        if (this.colour != "None") {
            return (super.salePrice() * (this.colourList.indexOf(this.colour) + 1));
        } else {
            return super.salePrice();
        }
    }

    @Override
    public void grow(int currentTime) {
        super.grow(currentTime);
        if (this.age >= minAgeToSell && this.colour == "None") {
            this.bloom();
        }
    }

    //   EFFECT: Creates a list of possible blooming colors from order of least to most rare.
    private List<String> generateColourList() {
        List<String> list = new ArrayList<String>();
        list.add("Red");
        list.add("Yellow");
        list.add("Pink");
        list.add("Black");
        return list;
    }

    // REQUIRES: age >= minAgeToSell
    // MODIFIES: this
    //   EFFECT: Randomizes color for flower
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
    }

    //   EFFECT: returns plant object as JSON object
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
