package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CactusTest {
    Cactus testCactus;

    @BeforeEach
    void runBefore() {
        testCactus = new Cactus("Aloe", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Cactus", testCactus.getType());
    }

}
