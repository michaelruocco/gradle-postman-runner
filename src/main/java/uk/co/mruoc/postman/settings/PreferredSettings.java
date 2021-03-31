package uk.co.mruoc.postman.settings;

import uk.co.mruoc.postman.task.util.Supplier;
import org.gradle.api.file.FileTree;

import java.io.File;
import java.util.Map;

public class PreferredSettings implements NewmanSettings {

    private final Supplier<NewmanSettings> defaultSettings;
    private FileTree collections;
    private File environment;
    private File globals;
    private Boolean cliReport;
    private String xmlReportDir;
    private String htmlReportDir;
    private String htmlTemplate;
    private String jsonReportDir;
    private Boolean stopOnError;
    private Boolean noColor;
    private Boolean disableUnicode;
    private Boolean secure;
    private Boolean ignoreRedirects;
    private Map<String, String> envVars;
    private Map<String, String> globalVars;

    public PreferredSettings(Supplier<NewmanSettings> defaultSettings) {
        this.defaultSettings = defaultSettings;
    }

    @Override
    public FileTree getCollections() {
        if (collections == null) {
            return getNewmanSettings().getCollections();
        }
        return collections;
    }

    @Override
    public File getEnvironment() {
        if (environment == null) {
            return getNewmanSettings().getEnvironment();
        }
        return environment;
    }

    @Override
    public File getGlobals() {
        if (globals == null) {
            return getNewmanSettings().getGlobals();
        }
        return globals;
    }

    @Override
    public boolean getCliReport() {
        if (cliReport == null) {
            return getNewmanSettings().getCliReport();
        }
        return cliReport;
    }

    @Override
    public String getXmlReportDir() {
        if (xmlReportDir == null) {
            return getNewmanSettings().getXmlReportDir();
        }
        return xmlReportDir;
    }

    @Override
    public String getHtmlReportDir() {
        if (htmlReportDir == null) {
            return getNewmanSettings().getHtmlReportDir();
        }
        return htmlReportDir;
    }

    @Override
    public String getHtmlTemplate() {
        if (htmlReportDir == null) {
            return getNewmanSettings().getHtmlTemplate();
        }
        return htmlTemplate;
    }

    @Override
    public String getJsonReportDir() {
        if (jsonReportDir == null) {
            return getNewmanSettings().getJsonReportDir();
        }
        return jsonReportDir;
    }

    @Override
    public boolean getStopOnError() {
        if (stopOnError == null) {
            return getNewmanSettings().getStopOnError();
        }
        return stopOnError;
    }

    @Override
    public boolean getNoColor() {
        if (noColor == null) {
            return getNewmanSettings().getNoColor();
        }
        return noColor;
    }

    @Override
    public boolean getDisableUnicode() {
        if (disableUnicode == null) {
            return getNewmanSettings().getDisableUnicode();
        }
        return disableUnicode;
    }

    @Override
    public boolean getSecure() {
        if (secure == null) {
            return getNewmanSettings().getSecure();
        }
        return secure;
    }

    @Override
    public boolean getIgnoreRedirects() {
        if (ignoreRedirects == null) {
            return getNewmanSettings().getIgnoreRedirects();
        }
        return ignoreRedirects;
    }

    @Override
    public Map<String, String> getEnvVars() {
        if (envVars == null) {
            return getNewmanSettings().getEnvVars();
        }
        return envVars;
    }

    @Override
    public Map<String, String> getGlobalVars() {
        if (globalVars == null) {
            return getNewmanSettings().getGlobalVars();
        }
        return globalVars;
    }

    public void setCollections(FileTree collections) {
        this.collections = collections;
    }

    public void setEnvironment(File environment) {
        this.environment = environment;
    }

    public void setGlobals(File globals) {
        this.globals = globals;
    }

    public void setCliReport(boolean cliReport) {
        this.cliReport = cliReport;
    }

    public void setXmlReportDir(String xmlReportDir) {
        this.xmlReportDir = xmlReportDir;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
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

    public void setEnvVars(Map<String, String> envVars) {
        this.envVars = envVars;
    }

    public void setGlobalVars(Map<String, String> globalVars) {
        this.globalVars = globalVars;
    }

    private NewmanSettings getNewmanSettings() {
        return defaultSettings.get();
    }
}
