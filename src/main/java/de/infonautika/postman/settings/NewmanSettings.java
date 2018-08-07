package de.infonautika.postman.settings;

import org.gradle.api.file.FileTree;

import java.io.File;

public interface NewmanSettings {
    FileTree getCollections();

    File getEnvironment();
    
    File getData();

    File getGlobals();

    String getTitle();

    boolean getCliReport();

    String getXmlReportDir();

    String getHtmlReportDir();

    String getHtmlTemplate();

    String getJsonReportDir();

    boolean getStopOnError();

    boolean getNoColor();

    boolean getDisableUnicode();
}
