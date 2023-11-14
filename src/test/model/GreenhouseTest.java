package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GreenhouseTest {
    Greenhouse testGreenhouse;
    //set up
    @BeforeEach
    void setup() {
        testGreenhouse = new Greenhouse("Bob", 0);
    }
    //test constructor
    @Test
    void testConstructor() {
        assertEquals(100, testGreenhouse.getWallet());
        assertEquals(0, testGreenhouse.getSeeds());
        assertEquals(0, testGreenhouse.getPlants().size());
        assertEquals(0, testGreenhouse.getTime());
    }
    //test that buying seed once, multiple and when out of money works
    @Test
    void testBuySeed() {
        assertTrue(testGreenhouse.buySeed());
        assertEquals(1, testGreenhouse.getSeeds());
        assertEquals(90, testGreenhouse.getWallet());

        assertTrue(testGreenhouse.buySeed());
        assertTrue(testGreenhouse.buySeed());
        assertEquals(3, testGreenhouse.getSeeds());
        assertEquals(70, testGreenhouse.getWallet());

        testGreenhouse.wallet = 0;
        assertFalse(testGreenhouse.buySeed());
        assertEquals(3, testGreenhouse.getSeeds());
        assertEquals(0, testGreenhouse.getWallet());
    }

    //test planting with no seed, planting one seed, and planting two seeds, planting plant with the same
    @Test
    void testPlantSeed() {
        assertFalse(testGreenhouse.plantSeed("Lilly"));

        testGreenhouse.buySeed();
        assertTrue(testGreenhouse.plantSeed("Lilly"));
        List<Plant> expectedPlants = testGreenhouse.getPlants();
        assertEquals(1, expectedPlants.size());
        assertEquals(expectedPlants.get(0), testGreenhouse.getPlant("Lilly"));

        testGreenhouse.buySeed();
        assertTrue(testGreenhouse.plantSeed("Cactus"));
        expectedPlants = testGreenhouse.getPlants();
        assertEquals(2, expectedPlants.size());
        assertEquals(expectedPlants.get(0), testGreenhouse.getPlant("Lilly"));
        assertEquals(expectedPlants.get(1), testGreenhouse.getPlant("Cactus"));

        testGreenhouse.buySeed();
        assertFalse(testGreenhouse.plantSeed("Lilly"));
        expectedPlants = testGreenhouse.getPlants();
        assertEquals(2, expectedPlants.size());
        assertEquals(expectedPlants.get(0), testGreenhouse.getPlant("Lilly"));
        assertEquals(expectedPlants.get(1), testGreenhouse.getPlant("Cactus"));
    }


    @Test
    void testUpdateTime() {
        testGreenhouse.updateTime(60000);
        assertEquals(60, testGreenhouse.getTime());
        testGreenhouse.updateTime(120000);
        assertEquals(120, testGreenhouse.getTime());
    }

    //test that all plant objects in plants lise age and dehydrate after updated a given length of time
    @Test
    void testUpdatePlants() {
        testGreenhouse.buySeed();
        testGreenhouse.buySeed();
        testGreenhouse.plantSeed("Lilly");
        testGreenhouse.updateTime(60000);
        testGreenhouse.updatePlants();
        testGreenhouse.plantSeed("Cactus");
        testGreenhouse.updateTime(120000);
        testGreenhouse.updatePlants();

        Plant testLilly = testGreenhouse.getPlant("Lilly");
        assertEquals( 120 / testLilly.GROWTH_RATE, testLilly.getAge());
        assertEquals( 100 - (120 / testLilly.DEHYDRATION_RATE), testLilly.getHydration());
        Plant testCactus = testGreenhouse.getPlant("Cactus");
        assertEquals( 60 / testCactus.GROWTH_RATE, testCactus.getAge());
        assertEquals( 100 - (60 / testCactus.DEHYDRATION_RATE), testCactus.getHydration());
    }

    //test that plants are waters when using correct name and no existing name
    @Test
    void testWaterPlant(){
        testGreenhouse.buySeed();
        testGreenhouse.buySeed();
        testGreenhouse.plantSeed("Lilly");
        testGreenhouse.updateTime(60000);
        testGreenhouse.updatePlants();
        testGreenhouse.plantSeed("Cactus");
        testGreenhouse.updateTime(120000);
        testGreenhouse.updatePlants();

        assertFalse(testGreenhouse.waterPlant("Fern"));

        assertTrue(testGreenhouse.waterPlant("Cactus"));

        Plant testLilly = testGreenhouse.getPlant("Lilly");
        assertEquals( 100 - (120 / testLilly.DEHYDRATION_RATE), testLilly.getHydration());
        Plant testCactus = testGreenhouse.getPlant("Cactus");
        assertEquals( 100, testCactus.getHydration());
    }

    //test selling plant and sale algorithm works
    @Test
    void testSellPlant() {
        testGreenhouse.wallet = 10;
        testGreenhouse.buySeed();
        testGreenhouse.plantSeed("Lilly");
        testGreenhouse.updateTime(30000);
        testGreenhouse.updatePlants();
        assertFalse(testGreenhouse.sellPlant("Cactus"));
        assertTrue(testGreenhouse.sellPlant("Lilly"));
        assertTrue(testGreenhouse.getWallet() > 0);
        assertEquals(0, testGreenhouse.getPlants().size());

        testGreenhouse.wallet = 20;
        testGreenhouse.buySeed();
        testGreenhouse.buySeed();
        testGreenhouse.plantSeed("Lilly");
        testGreenhouse.plantSeed("Cactus");
        testGreenhouse.updateTime(120000);
        testGreenhouse.updatePlants();
        assertTrue(testGreenhouse.sellPlant("Lilly"));
        assertTrue(testGreenhouse.getWallet() > 0);
        assertEquals(1, testGreenhouse.getPlants().size());
    }

    //test that plant returned is in plants list and that if plant name does not exist then what is returned is Null
    @Test
    void testGetPlant(){
        testGreenhouse.buySeed();
        testGreenhouse.buySeed();
        testGreenhouse.plantSeed("Lilly");
        testGreenhouse.plantSeed("Cactus");

        Plant expectedNull = testGreenhouse.getPlant("Fern");
        assertTrue(Objects.isNull(expectedNull));

        List<Plant> plantList = testGreenhouse.getPlants();
        Plant expectedPlant = testGreenhouse.getPlant("Lilly");
        assertTrue(plantList.contains(expectedPlant));
    }

    @Test
    void testPayDebt() {
        testGreenhouse.wallet = 2000;
        assertEquals(1000, testGreenhouse.getDebt());
        testGreenhouse.payDebt(-100);
        testGreenhouse.payDebt(2001);
        testGreenhouse.payDebt(100);
        assertEquals(900, testGreenhouse.getDebt());
        assertEquals(1900, testGreenhouse.getWallet());

        assertFalse(testGreenhouse.isDebtPaidOff());
        testGreenhouse.payDebt(1000);
        assertEquals(0, testGreenhouse.getDebt());
        assertEquals(1000, testGreenhouse.getWallet());
        assertTrue(testGreenhouse.isDebtPaidOff());


    }
}
