package com.github.michaelruocco.newman;

import org.gradle.api.Project;

import java.io.File;

public class NewmanWrapper {
    private static final String WRAPPER_SCRIPT_NAME = "/startnewman.js";
    private static final String GRADLE_POSTMAN_RUNNER = "/.gradle/postman-runner";
    private Project project;

    public static String getWrapperName() {
        return WRAPPER_SCRIPT_NAME;
    }

    public static String getWrapperRelativePath() {
        return GRADLE_POSTMAN_RUNNER + getWrapperName();
    }

    public NewmanWrapper(Project project) {
        this.project = project;
    }

    public File getWrapperAbsolutePath() {
        return new File(project.getProjectDir(), getWrapperRelativePath());
    }
}
