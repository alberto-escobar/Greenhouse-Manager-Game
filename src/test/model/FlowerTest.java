package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlowerTest {
    Flower testFlower;

    @BeforeEach
    void runBefore() {
        testFlower = new Flower("Lilly", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Flower", testFlower.getType());
        assertEquals("None", testFlower.getColour());
    }

    @Test
    void testGrowWithBloom() {
        List<String> flowerColourList = testFlower.getColourList();
        testFlower.grow(testFlower.getGrowthRate() *2);
        assertEquals(2, testFlower.getAge());
        assertFalse(flowerColourList.contains(testFlower.getColour()));
        assertEquals("None", testFlower.getColour());

        testFlower.grow(testFlower.getGrowthRate() *4);
        assertEquals(4, testFlower.getAge());
        assertTrue(flowerColourList.contains(testFlower.getColour()));
    }

    @Test
    void testSalePriceWithBloom() {
        List<String> flowerColourList = testFlower.getColourList();
        testFlower.grow(testFlower.getGrowthRate() *2);
        assertFalse(flowerColourList.contains(testFlower.getColour()));
        assertEquals("None", testFlower.getColour());
        assertEquals(10, testFlower.salePrice());

        testFlower.grow(testFlower.getGrowthRate() *4);
        assertEquals(4, testFlower.getAge());
        assertTrue(flowerColourList.contains(testFlower.getColour()));
        int expectedPrice = ((20 * testFlower.getAge() + 10) * (flowerColourList.indexOf(testFlower.getColour()) + 1));
        assertEquals(expectedPrice, testFlower.salePrice());
    }

}
