package persistence;

import model.Greenhouse;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// Inspired by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Greenhouse gh = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGreenhouse.json");
        try {
            Greenhouse gh = reader.read();
            checkGreenhouse("Empty", 100, 10000, 3, 0, 0, gh);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGreenhouse.json");
        try {
            Greenhouse gh = reader.read();
            checkGreenhouse("General", 0, 10000, 4, 140, 1, gh);

            Plant testLilly = gh.getPlant("Lilly");
            checkPlant("Lilly", "Plant", 1, 72, 0, 0, testLilly);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
