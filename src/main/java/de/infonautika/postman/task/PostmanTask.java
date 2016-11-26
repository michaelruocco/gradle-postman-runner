package de.infonautika.postman.task;

import com.moowork.gradle.node.exec.NodeExecRunner;
import com.moowork.gradle.node.task.SetupTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.io.File;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;
import static java.util.Arrays.asList;

public class PostmanTask extends AbstractPostmanRunnerTask {
    public final static String NAME = "postman";

    private NodeExecRunner runner;

    public PostmanTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");
        dependsOn(asList(SetupTask.NAME, InstallNewmanTask.NAME, DeployPostmanWrapperTask.NAME));
    }

    @TaskAction
    public void runPostmanCollections() {
        createRunner();
        if (!runCollections()) {
            throw new GradleException("There were failing tests.");
        }
    }

    private boolean runCollections() {
        if (getConfig().getStopOnError()) {
            return runUntilFail();
        }
        return runAllCollections();
    }

    private boolean runUntilFail() {
        for (File collection : getConfig().getCollections()) {
            if (!runSingleCollection(collection)) {
                return false;
            }
        }
        return true;
    }

    private boolean runAllCollections() {
        boolean success = true;
        for (File collection : getConfig().getCollections()) {
            success &= runSingleCollection(collection);
        }
        return success;
    }

    private boolean runSingleCollection(File collection) {
        runner.setArguments(asList(
                getWrapperAbsolutePath().toString(),
                getNewmanConfiguration(collection)));
        try {
            return runner.execute().getExitValue() == 0;
        } catch (ExecException ignored) {
            return false;
        }
    }

    private String getNewmanConfiguration(File collection) {
        return new NewmanConfig(getProject(), collection).toJson().replaceAll("\"", "<>");
    }

    private void createRunner() {
        runner = new NodeExecRunner(getProject());
        runner.setIgnoreExitValue(!getConfig().getStopOnError());
    }
}
