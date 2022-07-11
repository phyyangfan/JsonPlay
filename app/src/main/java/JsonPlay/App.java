package JsonPlay;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import json.JsonUtil;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // read input string from the standard input stream
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        StringBuilder inputStringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            inputStringBuilder.append(scanner.next());
        }
        scanner.close();

        // flatten the JSON object represented by the input string (if valid)
        try {
            JsonObject flattenedJsonObject = JsonUtil.parseAndFlattenJsonObjectFromString(inputStringBuilder.toString());
            System.out.println(flattenedJsonObject);
        } catch (JsonSyntaxException jsonSyntaxException) {
            System.out.println("Error: input JSON string is invalid.");
        } catch (UnsupportedOperationException unsupportedOperationException) {
            System.out.println(
                    "Error: input JSON string should not represent a JsonArray, a JsonPrimitive, a JsonNull," +
                            " or a JsonObject containing JsonArrays."
            );
        }
    }
}
