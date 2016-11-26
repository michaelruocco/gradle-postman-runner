package de.infonautika.postman.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.infonautika.postman.PostmanExtension;
import org.gradle.api.Project;

import java.io.File;

import static de.infonautika.postman.task.util.Json.*;

class NewmanConfig {
    private Project project;
    private File collection;
    private JsonObject params;

    NewmanConfig(Project project, File collection) {
        this.collection = collection;
        this.project = project;
    }

    String toJson() {
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

}
