package de.infonautika.postman.runner;

import com.moowork.gradle.node.exec.NodeExecRunner;
import de.infonautika.postman.PostmanExtension;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.process.internal.ExecException;

import java.io.File;

import static java.util.Arrays.asList;

public class NewmanRunner {

    private Project project;
    private NodeExecRunner runner;

    public NewmanRunner(Project project) {
        this.project = project;
    }

    public void run() {
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
                getWrapperAbsolutePath(),
                getNewmanConfiguration(collection)));
        try {
            return runner.execute().getExitValue() == 0;
        } catch (ExecException ignored) {
            return false;
        }
    }

    private String getWrapperAbsolutePath() {
        return new File(project.getProjectDir(), getConfig().getWrapperRelativePath()).toString();
    }

    private String getNewmanConfiguration(File collection) {
        return new NewmanConfig(project, collection).toJson().replaceAll("\"", "<>");
    }

    private void createRunner() {
        runner = new NodeExecRunner(project);
        runner.setIgnoreExitValue(!getConfig().getStopOnError());
    }

    private PostmanExtension getConfig() {
        return project.getExtensions().getByType(PostmanExtension.class);
    }
}
