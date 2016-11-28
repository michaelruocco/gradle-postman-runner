package de.infonautika.postman.settings;

import de.infonautika.postman.task.util.Supplier;
import org.gradle.api.file.FileTree;

import java.io.File;

public class PreferredSettings implements NewmanSettings {
    private Supplier<NewmanSettings> defaultSettings;
    private FileTree collections;
    private File environment;
    private Boolean cliReport;
    private String xmlReportDir;
    private Boolean stopOnError;
    private Boolean noColor;
    private Boolean disableUnicode;

    public PreferredSettings(Supplier<NewmanSettings> defaultSettings) {
        this.defaultSettings = defaultSettings;
    }

    @Override
    public FileTree getCollections() {
        if (collections == null) {
            return defaultSettings.get().getCollections();
        }
        return collections;
    }

    @Override
    public File getEnvironment() {
        if (environment == null) {
            return defaultSettings.get().getEnvironment();
        }
        return environment;
    }

    @Override
    public boolean getCliReport() {
        if (cliReport == null) {
            return defaultSettings.get().getCliReport();
        }
        return cliReport;
    }

    @Override
    public String getXmlReportDir() {
        if (xmlReportDir == null) {
            return defaultSettings.get().getXmlReportDir();
        }
        return xmlReportDir;
    }

    @Override
    public boolean getStopOnError() {
        if (stopOnError == null) {
            return defaultSettings.get().getStopOnError();
        }
        return stopOnError;
    }

    @Override
    public boolean getNoColor() {
        if (noColor == null) {
            return defaultSettings.get().getNoColor();
        }
        return noColor;
    }

    @Override
    public boolean getDisableUnicode() {
        if (disableUnicode == null) {
            return defaultSettings.get().getDisableUnicode();
        }
        return disableUnicode;
    }

    public void setCollections(FileTree collections) {
        this.collections = collections;
    }

    public void setEnvironment(File environment) {
        this.environment = environment;
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
}
