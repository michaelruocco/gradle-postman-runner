package de.infonautika.postman.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.infonautika.postman.settings.NewmanSettings;
import org.gradle.api.Project;

import java.io.File;

import static de.infonautika.postman.task.util.Json.*;

class NewmanConfig {
    private Project project;
    private NewmanSettings settings;

    NewmanConfig(Project project, NewmanSettings settings) {
        this.project = project;
        this.settings = settings;
    }

    String toJsonFor(File collection) {
        return new JsonBuilder(collection).build();
    }

    class JsonBuilder {

        private JsonObject params = object();
        private File collection;

        public JsonBuilder(File collection) {
            this.collection = collection;
        }

        private String build() {
            buildParameters();
            return params.toString();
        }

        private void buildParameters() {
            addCollection();
            addEnvironment();
            addReporters();
            addBail();
            addNoColor();
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
            if (settings.getXmlReportDir() != null) {
                reporters.add(primitive("junit"));
                File xmlReportFile = new File(new File(project.getProjectDir(), settings.getXmlReportDir()), "TEST-postman-" + collection.getName() + ".xml");
                reporter.add("junit", object("export", primitive(xmlReportFile.toString())));
            }
        }

        private void addCli(JsonArray reporters) {
            if (settings.getCliReport()) {
                reporters.add(primitive("cli"));
            }
        }

        private void addEnvironment() {
            if (settings.getEnvironment() != null) {
                params.add("environment", new JsonPrimitive(settings.getEnvironment().toString()));
            }
        }

        private void addBail() {
            params.add("bail", primitive(settings.getStopOnError()));
        }

        private void addNoColor() {
            params.add("noColor", primitive(true));
        }
    }


}
