package de.infonautika.postman.runner

import de.infonautika.postman.task.PostmanExtension
import org.gradle.api.Project

class NewmanConfig {
    private Project project
    private File collection

    def NewmanConfig(Project project, File collection) {
        this.collection = collection
        this.project = project
    }

    def String toJson() {
        return "{${parameters}}"
    }

    def getParameters() {
        return parameterList.join(',')
    }

    private ArrayList getParameterList() {
        def parameters = addEnvironment(["\"collection\":\"${collection}\"" ])
        parameters = addReporters(parameters)
        return parameters
    }

    def addReporters(def params) {
        params.add("\"reporters\":[\"cli\"]")
        return params
    }

    def addEnvironment(def params) {
        if (config.environment != null) {
            params.add("\"environment\":\"${config.environment}\"")
        }
        return params
    }

    def getConfig() {
        return project.extensions.getByType(PostmanExtension)
    }
}
