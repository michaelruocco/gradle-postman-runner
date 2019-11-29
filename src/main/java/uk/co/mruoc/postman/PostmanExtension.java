package uk.co.mruoc.postman;

import uk.co.mruoc.postman.settings.NewmanSettings;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostmanExtension implements NewmanSettings {
    public final static String NAME = "postman";

    private FileTree collections;
    private File environment;
    private boolean cliReport = true;
    private String xmlReportDir;
    private String htmlReportDir;
    private String htmlTemplate;
    private String jsonReportDir;
    private boolean stopOnError = false;
    private boolean noColor = true;
    private boolean disableUnicode = false;

    @Override
    public FileTree getCollections() {
        return collections;
    }

    public void setCollections(FileTree collections) {
        this.collections = collections;
    }

    @Override
    public File getEnvironment() {
        return environment;
    }

    public void setEnvironment(File environment) {
        this.environment = environment;
    }

    @Override
    public boolean getCliReport() {
        return cliReport;
    }

    public void setCliReport(boolean cliReporter) {
        this.cliReport = cliReporter;
    }

    @Override
    public String getXmlReportDir() {
        return xmlReportDir;
    }

    @Override
    public String getHtmlReportDir() {
        return htmlReportDir;
    }

    @Override
    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    @Override
    public String getJsonReportDir() {
        return jsonReportDir;
    }

    public void setXmlReportDir(String xmlReport) {
        this.xmlReportDir = xmlReport;
    }

    @Override
    public boolean getStopOnError() {
        return stopOnError;
    }

    void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    @Override
    public boolean getNoColor() {
        return noColor;
    }

    @Override
    public boolean getDisableUnicode() {
        return disableUnicode;
    }

    public void setNoColor(boolean noColor) {
        this.noColor = noColor;
    }

    public void setDisableUnicode(boolean disableUnicode) {
        this.disableUnicode = disableUnicode;
    }

    public void setHtmlReportDir(String htmlReportDir) {
        this.htmlReportDir = htmlReportDir;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public void setJsonReportDir(String jsonReportDir) {
        this.jsonReportDir = jsonReportDir;
    }

    public PostmanExtension(final Project project) {
        Map<String, String> treeConfig = new HashMap<>();
        treeConfig.put("dir", "src/test");
        treeConfig.put("include", "**/*.postman_collection.json");
        this.collections = project.fileTree(treeConfig);
    }
}
