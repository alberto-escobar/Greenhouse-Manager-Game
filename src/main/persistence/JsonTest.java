package persistence;

import model.Greenhouse;
import model.Plant;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkGreenhouse(String name, int wallet, int seeds, int greenhouseTime, int numOfPlants, Greenhouse gh) {
        assertEquals(name, gh.getName());
        assertEquals(wallet, gh.getWallet());
        assertEquals(seeds, gh.getSeeds());
        assertEquals(greenhouseTime, gh.getTime());
        assertEquals(numOfPlants, gh.getPlants().size());
    }

    protected void checkPlant(String name, String type, int age, int hydration, int timePlanted, int timeHydrated, Plant p) {
        assertEquals(name, p.getName());
        assertEquals(age, p.getAge());
        assertEquals(hydration, p.getHydration());
        assertEquals(timePlanted, p.getTimePlanted());
        assertEquals(timeHydrated, p.getTimeHydrated());
    }
}
