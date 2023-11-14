package persistence;

import model.Greenhouse;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation: JsonSerialization Demon
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
            checkGreenhouse("Empty", 100, 0, 0, 0, gh);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGreenhouse() {
        try {
            Greenhouse gh = new Greenhouse("General", 1000);
            gh.buySeed();
            gh.buySeed();
            gh.buySeed();
            gh.plantSeed("Lilly");
            gh.updateTime(141000);
            gh.updatePlants();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGreenhouse.json");
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGreenhouse.json");
            gh = reader.read();
            checkGreenhouse("General", 70, 2, 140, 1, gh);

            Plant testLilly = gh.getPlant("Lilly");
            checkPlant("Lilly", "plant", 1, 30, 0, 0, testLilly);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}