package persistence;

import org.json.JSONObject;

// Citation: JsonSerialization Demon
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
