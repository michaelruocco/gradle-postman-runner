package de.infonautika.postman.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.net.URL;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;

public class DeployPostmanWrapperTask extends AbstractPostmanRunnerTask {
    public final static String NAME = "deployWrapper";

    public DeployPostmanWrapperTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");

        getProject().afterEvaluate(addNewmanWrapperToOutputs());
    }

    private Action<? super Project> addNewmanWrapperToOutputs() {
        return new Action<Project>() {
            @Override
            public void execute(Project project) {
                getOutputs().file(getWrapperAbsolutePath());
            }
        };
    }

    @TaskAction
    public void createNewmanWrapper() {
        try {
            FileUtils.copyURLToFile(getInternalWrapperUrl(), getWrapperAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getInternalWrapperUrl() {
        URL wrapperScriptResource = this.getClass().getResource(getConfig().getWrapperName());
        if (wrapperScriptResource == null) {
            throw new RuntimeException("could not get wrapper script resource");
        }
        return wrapperScriptResource;
    }
}
