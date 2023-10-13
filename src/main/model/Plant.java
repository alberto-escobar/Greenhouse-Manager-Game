package model;

// Represents a plant with a name, age (in months), and hydration level.

public class Plant {
    String name;
    int age;
    int hydration;

    private int timePlanted;
    private int timeHydrated;

    static final int GROWTH_RATE = 60;
    static final int DEHYDRATION_RATE = 10;

    // REQUIRES: name is a non-empty string, and currentTime >= 0
    // MODIFIES: this
    //   EFFECT: creates a Plant object with name, 100% hydration, and 0 months of age.
    public Plant(String name, int currentTime) {
        this.name = name;
        this.timePlanted = currentTime;
        this.timeHydrated = currentTime;
        this.age = 0;
        this.hydration = 100;
    }

    // REQUIRES: currentTime >= timePlanted and currentTime >= timeHydrated
    // MODIFIES: this
    //   EFFECT: age increase by 1 month for every 60 second increment since plant was created
    //           hydration level reduces by 1 for every 10 second increment since plant was watered
    public void grow(int currentTime) {
        int timePassed = currentTime - this.timePlanted;
        this.age = timePassed / GROWTH_RATE;

        int timeSinceLastWater = currentTime - this.timeHydrated;
        this.hydration = 100 - timeSinceLastWater / DEHYDRATION_RATE;
        if (this.hydration < 0) {
            this.hydration = 0;
        }
    }

    // REQUIRES: currentTime >= timeHydrated
    // MODIFIES: this
    //   EFFECT: timeHydrated updated to currentTime and hydration level set to 100
    public void waterPlant(int currentTime) {
        this.timeHydrated = currentTime;
        this.hydration = 100;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getHydration() {
        return this.hydration;
    }
}
