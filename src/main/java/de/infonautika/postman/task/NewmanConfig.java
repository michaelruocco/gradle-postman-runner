package de.infonautika.postman.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.infonautika.postman.PostmanExtension;
import org.gradle.api.Project;

import java.io.File;

public class NewmanConfig {
    private Project project;
    private File collection;
    private JsonObject params;

    public NewmanConfig(Project project, File collection) {
        this.collection = collection;
        this.project = project;
    }

    public String toJson() {
        params = object();
        buildParameters();
        return params.toString();
    }

    private void buildParameters() {
        addCollection();
        addEnvironment();
        addReporters();
        addBail();
    }

    private void addCollection() {
        params.add("collection", primitive(collection.toString()));
    }

    private void addReporters() {
        JsonArray reporters = array();
        JsonObject reporter = object();

        addCli(reporters);
        addJunit(reporters, reporter);

        if (!empty(reporters)) {
            params.add("reporters", reporters);
        }

        if (!empty(reporter)) {
            params.add("reporter", reporter);
        }
    }

    private void addJunit(JsonArray reporters, JsonObject reporter) {
        if (getConfig().getXmlReportDir() != null) {
            reporters.add(primitive("junit"));
            File xmlReportFile = new File(new File(project.getProjectDir(), getConfig().getXmlReportDir()), "TEST-postman-" + collection.getName() + ".xml");
            reporter.add("junit", object("export", primitive(xmlReportFile.toString())));
        }
    }

    private void addCli(JsonArray reporters) {
        if (getConfig().getCliReport()) {
            reporters.add(primitive("cli"));
        }
    }

    private void addEnvironment() {
        if (getConfig().getEnvironment() != null) {
            params.add("environment", new JsonPrimitive(getConfig().getEnvironment().toString()));
        }
    }

    private void addBail() {
        params.add("bail", primitive(getConfig().getStopOnError()));
    }

    private PostmanExtension getConfig() {
        return project.getExtensions().getByType(PostmanExtension.class);
    }

    private static boolean empty(JsonObject reporter) {
        return reporter.entrySet().size() == 0;
    }

    private static boolean empty(JsonArray reporters) {
        return reporters.size() == 0;
    }

    private static JsonObject object() {
        return new JsonObject();
    }

    private static JsonObject object(String key, JsonElement value) {
        JsonObject junit = object();
        junit.add(key, value);
        return junit;
    }

    private static JsonPrimitive primitive(String string) {
        return new JsonPrimitive(string);
    }

    private static JsonPrimitive primitive(boolean bool) {
        return new JsonPrimitive(bool);
    }

    private static JsonArray array() {
        return new JsonArray();
    }
}
