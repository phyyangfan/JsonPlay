package json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.testng.annotations.*;
import static org.testng.Assert.*;

/**
 * Test class for {@link JsonUtil}
 */
public class JsonUtilTest {
    @DataProvider
    Object[][] parseAndFlattenJsonObjectFromStringTestData() {
        return new Object[][] {
                //@formatter:off
                {
                        "{\"id\": 123}",
                        "{\"id\":123}",
                },
                {
                        "{\"id\": 123," +
                                "\"description\": \"This is a test.\"}",
                        "{\"id\":123," +
                                "\"description\":\"This is a test.\"}",
                },
                {
                        "{\n" +
                                "\"a\": 1,\n" +
                                "\"b\": true,\n" +
                                "\"c\": {\n" +
                                "    \"d\": 3,\n" +
                                "    \"e\": \"test\"\n" +
                                "}\n" +
                                "}",
                        "{" +
                                "\"a\":1," +
                                "\"b\":true," +
                                "\"c.d\":3," +
                                "\"c.e\":\"test\"" +
                                "}",
                },
                {
                        "        {\n" +
                                "\"a\": 1,\n" +
                                "\"b\": true,\n" +
                                "\"c\": {\n" +
                                "    \"d\": 3,\n" +
                                "    \"e\": {\n" +
                                "        \"id\": 123,\n" +
                                "        \"Full Name\": \"John Smith\",\n" +
                                "        \"Current Employment\": {\n" +
                                "            \"Company\": \"Some Corporation\",\n" +
                                "            \"Title\": \"Staff Software Engineer\",\n" +
                                "            \"years of employment\": 3.5,\n" +
                                "            \"Contact Person\": null\n" +
                                "        },\n" +
                                "        \"enrolled\": true\n" +
                                "    },\n" +
                                "    \"status\": \"\"," +
                                "    \"f\": 3.14159" +
                                "}\n" +
                                "}",
                        "{" +
                                "\"a\":1," +
                                "\"b\":true," +
                                "\"c.d\":3," +
                                "\"c.e.id\":123," +
                                "\"c.e.Full Name\":\"John Smith\"," +
                                "\"c.e.Current Employment.Company\":\"Some Corporation\"," +
                                "\"c.e.Current Employment.Title\":\"Staff Software Engineer\"," +
                                "\"c.e.Current Employment.years of employment\":3.5," +
                                "\"c.e.Current Employment.Contact Person\":null," +
                                "\"c.e.enrolled\":true," +
                                "\"c.status\":\"\"," +
                                "\"c.f\":3.14159" +
                                "}",
                },
                //@formatter:on
        };
    }

    @Test(dataProvider = "parseAndFlattenJsonObjectFromStringTestData")
    public void parseAndFlattenJsonObjectFromStringTest(
            String inputJsonString,
            String expectedFlattenedJsonString) {
        JsonObject actualFlattenedJsonObject = JsonUtil.parseAndFlattenJsonObjectFromString(inputJsonString);
        assertEquals(actualFlattenedJsonObject.toString(), expectedFlattenedJsonString);
    }

    @DataProvider
    Object[][] parseAndFlattenJsonObjectFromInvalidStringTestData() {
        return new Object[][] {
                //@formatter:off
                {
                        "\"name\": null",
                },
                {
                        "\"id\": 123",
                },
                {
                        "{\"id\": 123},",
                },
                {
                        "abc[\n" +
                                "  {\"id\": 123,\n" +
                                "   \"description\": \"This is a test.\"},\n" +
                                "  {\"id\": 456,\n" +
                                "   \"description\": \"This is a second test.\"}\n" +
                                "]",
                },
                //@formatter:on
        };
    }

    @Test(dataProvider = "parseAndFlattenJsonObjectFromInvalidStringTestData",
            expectedExceptions = {JsonSyntaxException.class})
    public void parseAndFlattenJsonObjectFromInvalidStringTest(String inputJsonString) {
        JsonUtil.parseAndFlattenJsonObjectFromString(inputJsonString);
    }

    @DataProvider
    Object[][] parseAndFlattenJsonObjectFromUnsupportedStringTestData() {
        return new Object[][] {
                //@formatter:off
                {
                        "null",
                },
                {
                        "",
                },
                {
                        "\"null\"",
                },
                {
                        "\"Google Chrome\"",
                },
                {
                        "123",
                },
                {
                        "3.14159",
                },
                {
                        "true",
                },
                {
                        "{\"id\": 123," +
                                "\"description\": \"This is a test.\"," +
                                "\"colors\":[\"red\", \"green\", \"blue\"]}",
                },
                {
                        "[\n" +
                                "  {\"id\": 123,\n" +
                                "   \"description\": \"This is a test.\"},\n" +
                                "  {\"id\": 456,\n" +
                                "   \"description\": \"This is a second test.\"}\n" +
                                "]",
                },
                //@formatter:on
        };
    }

    @Test(dataProvider = "parseAndFlattenJsonObjectFromUnsupportedStringTestData",
            expectedExceptions = {UnsupportedOperationException.class})
    public void parseAndFlattenJsonObjectFromUnsupportedStringTest(String inputJsonString) {
        JsonUtil.parseAndFlattenJsonObjectFromString(inputJsonString);
    }
}
