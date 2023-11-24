package persistence;

import model.Cactus;
import model.Flower;
import model.Greenhouse;
import model.Plant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a writer that reads JSON representation of greenhouse to greenhouse object in game
// Inspired by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads greenhouse from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Greenhouse read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGreenhouse(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses greenhouse from JSON object and returns greenhouse object
    private Greenhouse parseGreenhouse(JSONObject jsonObject) {
        String owner = jsonObject.getString("owner");
        int wallet = jsonObject.getInt("wallet");
        int debt = jsonObject.getInt("debt");
        int pots = jsonObject.getInt("pots");
        int greenhouseTime = jsonObject.getInt("greenhouseTime");
        long currentTime = System.currentTimeMillis();
        List<Plant> plants = parsePlants(jsonObject);
        return new Greenhouse(owner, wallet, debt, pots, greenhouseTime, currentTime, plants);
    }

    // EFFECTS: parses plants array from JSON object and returns plants array
    private List<Plant> parsePlants(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("plants");
        List<Plant>  plants = new ArrayList<>();
        for (Object plantObject : jsonArray) {
            JSONObject plantJson = (JSONObject) plantObject;
            plants.add(parsePlant(plantJson));
        }
        return plants;
    }

    // EFFECTS: parses plant from JSON object and returns plant object
    private Plant parsePlant(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        Plant plant;
        if (type.equals("Flower")) {
            plant = new Flower(jsonObject);
        } else if (type.equals("Cactus")) {
            plant = new Cactus(jsonObject);
        } else {
            plant = new Plant(jsonObject);
        }
        return plant;
    }
}
