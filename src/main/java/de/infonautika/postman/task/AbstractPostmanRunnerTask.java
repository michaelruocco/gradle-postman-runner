package de.infonautika.postman.task;

import org.gradle.api.DefaultTask;

import java.io.File;

import static de.infonautika.postman.PostmanExtension.getWrapperRelativePath;

public abstract class AbstractPostmanRunnerTask extends DefaultTask {

    protected File getWrapperAbsolutePath() {
        return new File(getProject().getProjectDir(), getWrapperRelativePath());
    }
}
