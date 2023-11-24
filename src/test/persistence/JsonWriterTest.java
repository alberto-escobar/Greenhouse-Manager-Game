package persistence;

import model.Greenhouse;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Inspired by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Greenhouse gh = new Greenhouse("John", 1000);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGreenhouse() {
        try {
            Greenhouse gh = new Greenhouse("Empty", 1000);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGreenhouse.json");
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGreenhouse.json");
            gh = reader.read();
            checkGreenhouse("Empty", 100, 10000, 3, 0, 0, gh);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGreenhouse() {
        try {
            Greenhouse gh = new Greenhouse("General", 1000);
            gh.setWallet(210);
            gh.buyPots();
            gh.buyPlant("Lilly");
            gh.buyFlower("Poppy");
            gh.buyCactus("Zucc");
            gh.updateTime(141000);
            gh.updatePlants();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGreenhouse.json");
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGreenhouse.json");
            gh = reader.read();
            checkGreenhouse("General", 40, 10000, 4, 140, 3, gh);

            Plant testLilly = gh.getPlant("Lilly");
            Plant testPoppy = gh.getPlant("Poppy");
            Plant testZucc = gh.getPlant("Zucc");
            checkPlant("Lilly", "Plant", 1, 72, 0, 0, testLilly);
            checkPlant("Poppy", "Flower", 1, 54, 0, 0, testPoppy);
            checkPlant("Zucc", "Cactus", 0, 86, 0, 0, testZucc);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }


}