package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlantTest {
    Plant testPlant;

    @BeforeEach
    void runBefore() {
        testPlant = new Plant("Lilly", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Lilly", testPlant.getName());
        assertEquals(0, testPlant.getAge());
        assertEquals(100, testPlant.getHydration());
        assertEquals(2, testPlant.getMinAgeToSell());
    }

    @Test
    void testGrow() {
        testPlant.grow(testPlant.getDehydrationRate() *2);
        assertEquals(98, testPlant.getHydration());
        assertEquals(0, testPlant.getAge());

        testPlant.grow(testPlant.getGrowthRate() *2);
        assertEquals(2, testPlant.getAge());
    }

    @Test
    void testMinimumHydrationLevel(){
        testPlant.grow(testPlant.getDehydrationRate() *1000);
        assertEquals(0, testPlant.getHydration());
    }

    @Test
    void testWaterPlant(){
         testPlant.grow(testPlant.getDehydrationRate() *50);
         assertEquals(50, testPlant.getHydration());
         testPlant.waterPlant(testPlant.getDehydrationRate() *50+1);
         assertEquals(100, testPlant.getHydration());
     }

    @Test
    void testSalePrice(){
        testPlant.grow(testPlant.getGrowthRate() *1);
        assertEquals(1, testPlant.getAge());
        assertEquals(10, testPlant.salePrice());


        testPlant.grow(testPlant.getGrowthRate() *3);
        assertEquals(3, testPlant.getAge());
        assertEquals(70, testPlant.salePrice());

        testPlant.grow(testPlant.getGrowthRate() *200);
        assertEquals(210, testPlant.salePrice());
    }
}
