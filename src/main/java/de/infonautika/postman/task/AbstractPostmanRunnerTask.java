package de.infonautika.postman.task;

import de.infonautika.postman.PostmanExtension;
import org.gradle.api.DefaultTask;

import java.io.File;

public abstract class AbstractPostmanRunnerTask extends DefaultTask {

    protected PostmanExtension getConfig() {
        return getProject().getExtensions().getByType(PostmanExtension.class);
    }

    protected File getWrapperAbsolutePath() {
        return new File(getProject().getProjectDir(), getConfig().getWrapperRelativePath());
    }
}
