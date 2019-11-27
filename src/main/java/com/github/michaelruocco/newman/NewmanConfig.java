package com.github.michaelruocco.newman;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.github.michaelruocco.settings.NewmanSettings;
import org.gradle.api.Project;

import java.io.File;

import static com.github.michaelruocco.task.util.Json.*;

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

    private class JsonBuilder {

        private JsonObject params = object();
        private File collection;

        JsonBuilder(File collection) {
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
            addDisableUnicode();
        }

        private void addCollection() {
            params.add("collection", primitive(collection.toString()));
        }

        private void addReporters() {
            JsonArray reporters = array();
            JsonObject reporter = object();

            addCli(reporters);
            addJunit(reporters, reporter);
            addJson(reporters, reporter);
            addHtml(reporters, reporter);

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
                reporter.add(
                    "junit",
                    object(
                        "export",
                        projectFile(settings.getXmlReportDir(), "TEST-postman-" + collection.getName() + ".xml")));
            }
        }

        private void addJson(JsonArray reporters, JsonObject reporter) {
            if (settings.getJsonReportDir() != null) {
                reporters.add("json");
                reporter.add(
                    "json",
                    object(
                        "export",
                        projectFile(settings.getJsonReportDir(), endsWithJson("TEST-postman-" + collection.getName()))));
            }
        }

        private void addHtml(JsonArray reporters, JsonObject reporter) {
            if (settings.getHtmlReportDir() != null) {
                reporters.add("html");

                JsonObject htmlOptions = object();
                reporter.add("html", htmlOptions);

                htmlOptions.addProperty(
                    "export",
                    projectFile(settings.getHtmlReportDir(), "TEST-postman-" + collection.getName() + ".html"));

                if (settings.getHtmlTemplate() != null) {
                    htmlOptions.addProperty(
                        "template",
                        projectFile(settings.getHtmlTemplate()));
                }
            }
        }

        private String projectFile(String... parts) {
            return compose(project.getProjectDir(), parts).toString();
        }

        private File compose(File file, String[] parts) {
            for (String part : parts) {
                file = new File(file, part);
            }
            return file;
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
            params.add("noColor", primitive(settings.getNoColor()));
        }

        private void addDisableUnicode() {
            params.add("disableUnicode", primitive(settings.getDisableUnicode()));
        }

        private String endsWithJson(String fileName) {
            if (fileName.endsWith(".json")) {
                return fileName;
            }
            return fileName + ".json";
        }
    }


}
