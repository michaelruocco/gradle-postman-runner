package de.infonautika.postman.task.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class Json {
    public static boolean empty(JsonObject reporter) {
        return reporter.entrySet().size() == 0;
    }

    public static boolean empty(JsonArray reporters) {
        return reporters.size() == 0;
    }

    public static JsonObject object() {
        return new JsonObject();
    }

    public static JsonObject object(String key, String value) {
        JsonObject object = object();
        object.addProperty(key, value);
        return object;
    }

    public static JsonPrimitive primitive(String string) {
        return new JsonPrimitive(string);
    }

    public static JsonPrimitive primitive(boolean bool) {
        return new JsonPrimitive(bool);
    }

    public static JsonArray array() {
        return new JsonArray();
    }
}
