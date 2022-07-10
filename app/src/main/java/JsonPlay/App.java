package JsonPlay;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import json.JsonUtil;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(
                    "Error: an input string representing a valid JSON object (not containing JSON arrays) is required."
            );
            return;
        }
        try {
            JsonObject flattenedJsonObject = JsonUtil.parseAndFlattenJsonObjectFromString(args[0]);
            System.out.println(flattenedJsonObject);
        } catch (JsonSyntaxException jsonSyntaxException) {
            System.out.println("Error: input JSON string is invalid.");
        } catch (UnsupportedOperationException unsupportedOperationException) {
            System.out.println("Error: input JSON string should be a valid JSON object not containing JSON arrays.");
        }
    }
}
