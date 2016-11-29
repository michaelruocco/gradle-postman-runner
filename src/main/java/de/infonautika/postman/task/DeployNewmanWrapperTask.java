package de.infonautika.postman.task;

import de.infonautika.postman.NewmanWrapper;
import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static de.infonautika.postman.NewmanWrapper.getWrapperName;
import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;

public class DeployNewmanWrapperTask extends DefaultTask {
    public final static String NAME = "deployWrapper";

    public DeployNewmanWrapperTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");
    }

    @TaskAction
    public void deployNewmanWrapper() {
        try {
            FileUtils.copyURLToFile(getInternalWrapperUrl(), getWrapper());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getInternalWrapperUrl() {
        URL wrapperScriptResource = this.getClass().getResource(getWrapperName());
        if (wrapperScriptResource == null) {
            throw new RuntimeException("could not get wrapper script resource");
        }
        return wrapperScriptResource;
    }

    @OutputFile
    public File getWrapper() {
        return new NewmanWrapper(getProject()).getWrapperAbsolutePath();
    }
}
