package de.infonautika.postman.settings;

import org.gradle.api.file.FileTree;

import java.io.File;

public interface NewmanSettings {
    FileTree getCollections();

    File getEnvironment();

    boolean getCliReport();

    String getXmlReportDir();

    boolean getStopOnError();

    boolean getNoColor();

    boolean getDisableUnicode();
}
