package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CactusTest {
    Plant testCactus;

    @BeforeEach
    void runBefore() {
        testCactus = new Cactus("Aloe", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Cactus", testCactus.getType());
    }

    @Test
    void testSalePrice(){
        testCactus.grow(testCactus.getGrowthRate() *2);
        assertEquals(2, testCactus.getAge());
        assertEquals(10, testCactus.salePrice());

        testCactus.grow(testCactus.getGrowthRate() *4);
        assertEquals(4, testCactus.getAge());
        assertEquals(10, testCactus.salePrice());

        testCactus.grow(testCactus.getGrowthRate() *6);
        assertEquals(6, testCactus.getAge());
        assertTrue(testCactus.salePrice() > 10);
    }
}
