package de.infonautika.postman.runner

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import de.infonautika.postman.task.PostmanExtension
import org.gradle.api.Project


class NewmanConfig {
    private Project project
    private File collection
    private JsonObject params

    def NewmanConfig(Project project, File collection) {
        this.collection = collection
        this.project = project
    }

    def String toJson() {
        params = object()
        buildParameters()
        return params.toString()
    }

    def buildParameters() {
        addCollection()
        addEnvironment()
        addReporters()
    }

    def addCollection() {
        params.add('collection', primitive(collection.toString()))
    }

    def addReporters() {
        JsonArray reporters = array()
        JsonObject reporter = object()

        if (config.cliReport) {
            reporters.add(primitive('cli'))
        }

        if (config.xmlReportDir?.trim()) {
            def xmlReportFile = new File(new File(project.projectDir, config.xmlReportDir), "TEST-postman-${collection.name}.xml")
            reporters.add(primitive('junit'))
            reporter.add('junit', object('export', primitive("${xmlReportFile}")))
        }

        if (!empty(reporters)) {
            params.add('reporters', reporters)
        }

        if (!empty(reporter)) {
            params.add('reporter', reporter)
        }

        return params
    }

    def addEnvironment() {
        if (config.environment != null) {
            params.add('environment', new JsonPrimitive("${config.environment}"))
        }
    }

    def getConfig() {
        return project.extensions.getByType(PostmanExtension)
    }

    private static boolean empty(JsonObject reporter) {
        return reporter.entrySet().size() == 0
    }

    private static boolean empty(JsonArray reporters) {
        return reporters.size() == 0
    }

    private static JsonObject object() {
        return new JsonObject()
    }

    private static JsonObject object(String key, JsonElement value) {
        def junit = object()
        junit.add(key, value)
        return junit
    }

    private static JsonPrimitive primitive(String string) {
        return new JsonPrimitive(string)
    }

    private static JsonArray array() {
        return new JsonArray()
    }

}
