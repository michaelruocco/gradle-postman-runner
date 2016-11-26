package de.infonautika.postman.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.infonautika.postman.PostmanExtension;
import de.infonautika.postman.task.util.Json;
import org.gradle.api.Project;

import java.io.File;

class NewmanConfig {
    private Project project;
    private File collection;
    private JsonObject params;

    NewmanConfig(Project project, File collection) {
        this.collection = collection;
        this.project = project;
    }

    String toJson() {
        params = Json.object();
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
        params.add("collection", Json.primitive(collection.toString()));
    }

    private void addReporters() {
        JsonArray reporters = Json.array();
        JsonObject reporter = Json.object();

        addCli(reporters);
        addJunit(reporters, reporter);

        if (!Json.empty(reporters)) {
            params.add("reporters", reporters);
        }

        if (!Json.empty(reporter)) {
            params.add("reporter", reporter);
        }
    }

    private void addJunit(JsonArray reporters, JsonObject reporter) {
        if (getConfig().getXmlReportDir() != null) {
            reporters.add(Json.primitive("junit"));
            File xmlReportFile = new File(new File(project.getProjectDir(), getConfig().getXmlReportDir()), "TEST-postman-" + collection.getName() + ".xml");
            reporter.add("junit", Json.object("export", Json.primitive(xmlReportFile.toString())));
        }
    }

    private void addCli(JsonArray reporters) {
        if (getConfig().getCliReport()) {
            reporters.add(Json.primitive("cli"));
        }
    }

    private void addEnvironment() {
        if (getConfig().getEnvironment() != null) {
            params.add("environment", new JsonPrimitive(getConfig().getEnvironment().toString()));
        }
    }

    private void addBail() {
        params.add("bail", Json.primitive(getConfig().getStopOnError()));
    }

    private PostmanExtension getConfig() {
        return project.getExtensions().getByType(PostmanExtension.class);
    }

}
