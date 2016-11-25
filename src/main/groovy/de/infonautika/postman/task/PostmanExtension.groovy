package de.infonautika.postman.task

import org.gradle.api.Project
import org.gradle.api.file.FileTree

class PostmanExtension {
    final static String NAME = 'postman'

    private FileTree collections
    private File environment
    private boolean cliReport = true
    private String xmlReportDir
    private boolean stopOnError = false

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

    boolean getStopOnError() {
        return stopOnError
    }

    void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError
    }

    PostmanExtension(final Project project) {
        this.collections = project.fileTree(dir: 'src/test', include: '**/*.postman_collection.json')
    }
}
