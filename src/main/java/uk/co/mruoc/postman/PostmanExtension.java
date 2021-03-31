package uk.co.mruoc.postman;

import uk.co.mruoc.postman.settings.NewmanSettings;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostmanExtension implements NewmanSettings {
    public static final String NAME = "postman";

    private FileTree collections;
    private File environment;
    private File globals;
    private boolean cliReport = true;
    private String xmlReportDir;
    private String htmlReportDir;
    private String htmlTemplate;
    private String jsonReportDir;
    private boolean stopOnError = false;
    private boolean noColor = true;
    private boolean disableUnicode = false;
    private boolean secure = true;
    private boolean ignoreRedirects = false;
    private Map<String, String> envVars;
    private Map<String, String> globalsVars;

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
    public File getGlobals() {
        return globals;
    }

    public void setGlobals(File globals) {
        this.globals = globals;
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

    @Override
    public boolean getSecure() {
        return secure;
    }

    @Override
    public boolean getIgnoreRedirects() {
        return ignoreRedirects;
    }

    @Override
    public Map<String, String> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(Map<String, String> envVars) {
        this.envVars = envVars;
    }

    @Override
    public Map<String, String> getGlobalVars() {
        return globalsVars;
    }

    public void setGlobalVars(Map<String, String> globalsVars) {
        this.globalsVars = globalsVars;
    }

    public void setNoColor(boolean noColor) {
        this.noColor = noColor;
    }

    public void setDisableUnicode(boolean disableUnicode) {
        this.disableUnicode = disableUnicode;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setIgnoreRedirects(boolean ignoreRedirects) {
        this.ignoreRedirects = ignoreRedirects;
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
