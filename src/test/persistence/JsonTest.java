package persistence;

import model.Greenhouse;
import model.Plant;

import static org.junit.jupiter.api.Assertions.*;

// Citation: JsonSerialization Demon
public class JsonTest {
    protected void checkGreenhouse(String name, int wallet, int debt, int pots,
                                   int greenhouseTime, int numOfPlants, Greenhouse gh) {
        assertEquals(name, gh.getOwner());
        assertEquals(wallet, gh.getWallet());
        assertEquals(debt, gh.getDebt());
        assertEquals(pots, gh.getPots());
        assertEquals(greenhouseTime, gh.getTime());
        assertEquals(numOfPlants, gh.getPlants().size());
    }

    protected void checkPlant(String name, String type, int age,
                              int hydration, int timePlanted, int timeHydrated, Plant p) {
        assertEquals(name, p.getName());
        assertEquals(type, p.getType());
        assertEquals(age, p.getAge());
        assertEquals(hydration, p.getHydration());
        assertEquals(timePlanted, p.getTimePlanted());
        assertEquals(timeHydrated, p.getTimeHydrated());
    }
}
