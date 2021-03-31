package uk.co.mruoc.postman.settings;

import org.gradle.api.file.FileTree;

import java.io.File;
import java.util.Map;

public interface NewmanSettings {
    FileTree getCollections();

    File getEnvironment();

    File getGlobals();

    boolean getCliReport();

    String getXmlReportDir();

    String getHtmlReportDir();

    String getHtmlTemplate();

    String getJsonReportDir();

    boolean getStopOnError();

    boolean getNoColor();

    boolean getDisableUnicode();

    boolean getSecure();

    Map<String, String> getEnvVars();

    Map<String, String> getGlobalVars();

}
