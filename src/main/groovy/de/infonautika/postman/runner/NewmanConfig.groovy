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
        return "{\"collection\":\"${collection}\",${environment}${reporters}${sentinel}}"
    }

    def getReporters() {
        return "\"reporters\":[\"cli\"],"
    }

    def getSentinel() {
        return "\"sentinel\":\"sentinel\""
    }

    def getEnvironment() {
        if (config.environment == null) {
            return ""
        }
        return "\"environment\":\"${config.environment}\","
    }

    def getConfig() {
        return project.extensions.getByType(PostmanExtension)
    }
}
