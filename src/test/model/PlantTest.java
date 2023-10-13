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
    }

    @Test
    void testGrow() {
        testPlant.grow(testPlant.DEHYDRATION_RATE*2);
        assertEquals(98, testPlant.getHydration());
        assertEquals(0, testPlant.getAge());

        testPlant.grow(testPlant.GROWTH_RATE*2);
        assertEquals(2, testPlant.getAge());
    }

    @Test
    void testMinimumHydrationLevel(){
        testPlant.grow(testPlant.DEHYDRATION_RATE*1000);
        assertEquals(0, testPlant.getHydration());
    }

    @Test
    void testWaterPlant(){
         testPlant.grow(testPlant.DEHYDRATION_RATE*50);
         assertEquals(50, testPlant.getHydration());
         testPlant.waterPlant(testPlant.DEHYDRATION_RATE*50+1);
         assertEquals(100, testPlant.getHydration());
     }
}
