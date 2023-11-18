package model;

public class Flower extends Plant {
    String color;

    public Flower(String name, int currentTime) {
        super(name, currentTime);
        this.type = "flower";
        //this.GROWTH_RATE = 100;
        //this.DEHYDRATION_RATE = 2;
        //this.MIN_AGE_TO_SELL = 4;
    }
    //overrides
    //plant constructor for json, add color parameter
    //grow, add if block where once sell age is reached, randomize a color
    //salePrice, color influences the price dramatically
    //toJSON, include color

    //add
    //get color
    //set color

}
