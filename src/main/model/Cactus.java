package model;

public class Cactus extends Plant {
    public Cactus(String name, int currentTime) {
        super(name, currentTime);
        this.type = "Cactus";
        this.growthRate = 180;
        this.dehydrationRate = 10;
        this.minAgeToSell = 6;
    }

    public Cactus(String name, String type, int timePlanted, int timeHydrated, int age, int hydration) {
        super(name, type, timePlanted, timeHydrated, age, hydration);
        this.type = "Cactus";
        this.growthRate = 180;
        this.dehydrationRate = 10;
        this.minAgeToSell = 6;
    }
}
