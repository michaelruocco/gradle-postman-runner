package de.infonautika.postman.task

import org.gradle.api.Project
import org.gradle.api.file.FileTree

class PostmanExtension {
    final static String NAME = 'postman'

    def FileTree collections
    def File environment
    def boolean cliReport = true
    def String xmlReportDir

    FileTree getCollections() {
        return collections
    }

    public void setCollections(FileTree collections) {
        this.collections = collections
    }

    File getEnvironment() {
        return environment
    }

    void setEnvironment(File environment) {
        this.environment = environment
    }

    boolean getCliReport() {
        return cliReport
    }

    void setCliReport(boolean cliReporter) {
        this.cliReport = cliReporter
    }

    String getXmlReportDir() {
        return xmlReportDir
    }

    void setXmlReportDir(String xmlReport) {
        this.xmlReportDir = xmlReport
    }

    PostmanExtension(final Project project) {
        this.collections = project.fileTree(dir: 'src/test', include: '**/*.postman_collection.json')
    }
}
