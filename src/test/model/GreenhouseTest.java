package model;

import model.exceptions.DuplicatePlantException;
import model.exceptions.InsufficientFundsException;
import model.exceptions.InsufficientSpaceException;
import model.exceptions.InvalidAmountException;
import org.junit.jupiter.api.Assertions;
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
        assertEquals(10000, testGreenhouse.getDebt());
        assertEquals(3, testGreenhouse.getPots());
        assertEquals(0, testGreenhouse.getPlants().size());
        assertEquals(0, testGreenhouse.getTime());
    }

    @Test
    void testUpdateTime() {
        testGreenhouse.updateTime(60000);
        assertEquals(60, testGreenhouse.getTime());
        testGreenhouse.updateTime(120000);
        assertEquals(120, testGreenhouse.getTime());
    }

    @Test
    void testBuyPots() {
        testGreenhouse.setWallet(200);
        try {
            testGreenhouse.buyPots();
            testGreenhouse.buyPots();

        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        assertEquals(5, testGreenhouse.getPots());
        assertEquals(0, testGreenhouse.getWallet());

        try {
            testGreenhouse.buyPots();
            fail("InsufficientFundsException not thrown");
        } catch (InsufficientFundsException e) {
            assertEquals(5, testGreenhouse.getPots());
            assertEquals(0, testGreenhouse.getWallet());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    void testAvailablePots() {
        assertEquals(3, testGreenhouse.availablePots());
        try {
            testGreenhouse.buyPlant("Cactus");
        } catch (Exception e) {
            fail("Unexpected exception.");
        }
        assertEquals(2, testGreenhouse.availablePots());
    }

    //test planting with no seed, planting one seed, and planting two seeds, planting plant with the same
    @Test
    void testBuyPlant() {
        testGreenhouse.setWallet(30);
        try {
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.buyPlant("Cactus");
            testGreenhouse.buyPlant("Fern");
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        //TODO check the correct plants are there
        List<Plant> testPlants = testGreenhouse.getPlants();
        assertEquals(3, testGreenhouse.getNumPlants());

        try {
            testGreenhouse.buyPlant("Lilly");
            fail("Exception not thrown");
        } catch (InsufficientFundsException a) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }

        try {
            testGreenhouse.setWallet(10);
            testGreenhouse.buyPlant("Lilly");
            fail("Exception not thrown");
        } catch (DuplicatePlantException a) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }

        try {
            testGreenhouse.buyPlant("Herb");
            fail("Exception not thrown");
        } catch (InsufficientSpaceException a) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception thrown");
        }

        try {
            testGreenhouse.setWallet(110);
            testGreenhouse.buyPots();
            testGreenhouse.buyPlant("Herb");
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        //TODO check the correct plants are there
        assertEquals(4, testGreenhouse.getNumPlants());

    }

    //test that all plant objects in plants lise age and dehydrate after updated a given length of time
    @Test
    void testUpdatePlants() {
        try {
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.updateTime(60000);
            testGreenhouse.updatePlants();
            testGreenhouse.buyPlant("Cactus");
            testGreenhouse.updateTime(120000);
            testGreenhouse.updatePlants();
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }


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
        try {
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.updateTime(60000);
            testGreenhouse.updatePlants();
            testGreenhouse.buyPlant("Cactus");
            testGreenhouse.updateTime(120000);
            testGreenhouse.updatePlants();
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

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

        try {
            testGreenhouse.setWallet(10);
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.updateTime(30000);
            testGreenhouse.updatePlants();
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
        assertFalse(testGreenhouse.sellPlant("Cactus"));
        assertTrue(testGreenhouse.sellPlant("Lilly"));
        assertTrue(testGreenhouse.getWallet() > 0);
        assertEquals(0, testGreenhouse.getPlants().size());
        try {
            testGreenhouse.setWallet(20);
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.buyPlant("Cactus");
            testGreenhouse.updateTime(120000);
            testGreenhouse.updatePlants();
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        assertTrue(testGreenhouse.sellPlant("Lilly"));
        assertTrue(testGreenhouse.getWallet() > 0);
        assertEquals(1, testGreenhouse.getPlants().size());
    }

    //test that plant returned is in plants list and that if plant name does not exist then what is returned is Null
    @Test
    void testGetPlant(){
        try {
            testGreenhouse.buyPlant("Lilly");
            testGreenhouse.buyPlant("Cactus");
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        Plant expectedNull = testGreenhouse.getPlant("Fern");
        assertTrue(Objects.isNull(expectedNull));

        List<Plant> plantList = testGreenhouse.getPlants();
        Plant expectedPlant = testGreenhouse.getPlant("Lilly");
        assertTrue(plantList.contains(expectedPlant));
    }

    @Test
    void testPayDebtAndIsDebtPaidOff() {
        try {
            testGreenhouse.payDebt(-100);
            fail("Exception not thrown");
        } catch (InvalidAmountException a) {
            //pass
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            testGreenhouse.payDebt(100000000);
            fail("Exception not thrown");
        } catch (InsufficientFundsException a) {
            //pass
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        try {
            testGreenhouse.payDebt(100);
            assertFalse(testGreenhouse.isDebtPaidOff());
            assertEquals(9900, testGreenhouse.getDebt());
            assertEquals(0, testGreenhouse.getWallet());
            testGreenhouse.setWallet(10000);
            testGreenhouse.payDebt(10000);
            assertEquals(0, testGreenhouse.getDebt());
            assertEquals(100, testGreenhouse.getWallet());
            assertTrue(testGreenhouse.isDebtPaidOff());
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }
}
