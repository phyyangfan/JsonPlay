package json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

/**
 * Utility class for handling JSON objects.
 */
public class JsonUtil {
    /**
     * Parse the JSON object represented by the input string and create a flattened copy of it.
     * @param jsonString: String representing the input JSON Object
     * @return the flattened copy of the input JSON Object
     * @throws JsonSyntaxException: if the input string is invalid
     * @throws UnsupportedOperationException: if the input string is valid but does not represent a JSON Object
     */
    public static JsonObject parseAndFlattenJsonObjectFromString(String jsonString)
            throws JsonSyntaxException, UnsupportedOperationException {
        // parse the input string
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        // check whether the parsed JsonElement is a JsonObject
        if (jsonElement.isJsonArray() || jsonElement.isJsonPrimitive() || jsonElement.isJsonNull()) {
            throw new UnsupportedOperationException();
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // create a flattened copy of the parsed JsonObject
        return getFlattenedJsonObject(jsonObject);
    }

    /**
     * Create a new JsonObject which is the flattened version of the input JsonObject
     * @param jsonObject: input JsonObject to be flattened
     * @return the flattened copy of the input JSON Object
     * @throws UnsupportedOperationException: if the input JsonObject contains JsonArray (which is an unsupported
     *                                        JSON element) at any level in its nested structure
     */
    public static JsonObject getFlattenedJsonObject(JsonObject jsonObject)
            throws UnsupportedOperationException {
        return getFlattenedJsonObject("", jsonObject);
    }

    /**
     * Traverse the nested structure of the input JsonObject and create a new JsonObject which is its flattened version,
     * with keys as the path to every terminal element in the JSON structure.
     * @param prefix: prefix of the key, which is the path to the input JsonObject
     * @param jsonObject: input JsonObject
     * @return the flattened copy of the input JSON Object
     * @throws UnsupportedOperationException: if the input JsonObject contains JsonArray (which is an unsupported
     *                                        JSON element) at any level in its nested structure
     */
    private static JsonObject getFlattenedJsonObject(String prefix, JsonObject jsonObject)
            throws UnsupportedOperationException {
        // instantiate a new JsonObject which will be the flattened version of the input JsonObject
        JsonObject flattenedJsonOjbect = new JsonObject();
        // process each member of the input JsonObject.
        for (Map.Entry<String, JsonElement> member : jsonObject.entrySet()) {
            JsonElement jsonElement = member.getValue();
            // check whether the current member is a JsonArray
            if (jsonElement.isJsonArray()) {
                throw new UnsupportedOperationException();
            }
            // construct the new key of the current member, or prefix of the nested JsonObject
            String memberName = prefix + member.getKey();
            if (jsonElement.isJsonObject()) {
                // current member is a nested JsonObject, hence recursively flatten the nested object
                JsonObject innerFlattenedJsonObject =
                        getFlattenedJsonObject(memberName + ".", jsonElement.getAsJsonObject());
                // merge the flattened version of the nested JsonObject into the current flattened JsonObject
                for (Map.Entry<String, JsonElement> innerMember : innerFlattenedJsonObject.entrySet()) {
                    flattenedJsonOjbect.add(innerMember.getKey(), innerMember.getValue());
                }
            } else {
                // current member is a terminal element (either JsonPrimitive or JsonNull), hence add it to the
                // flattened JsonObject
                flattenedJsonOjbect.add(memberName, jsonElement);
            }
        }
        return flattenedJsonOjbect;
    }
}
