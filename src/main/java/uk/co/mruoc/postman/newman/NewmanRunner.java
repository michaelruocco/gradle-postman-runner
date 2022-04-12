package uk.co.mruoc.postman.newman;

import com.github.gradle.node.exec.NodeExecRunner;
import uk.co.mruoc.postman.settings.PreferredSettings;
import org.gradle.api.Project;
import org.gradle.process.internal.ExecException;

import java.io.File;

import static java.util.Arrays.asList;

public class NewmanRunner {
    private final NodeExecRunner nodeExecRunner;
    private final Project project;
    private final PreferredSettings settings;

    public NewmanRunner(Project project, PreferredSettings settings) {
        this.project = project;
        this.settings = settings;
        nodeExecRunner = new NodeExecRunner(project);
    }

    public boolean runCollections() {
        nodeExecRunner.setIgnoreExitValue(!settings.getStopOnError());

        if (settings.getStopOnError()) {
            return runUntilFail();
        }
        return runAllCollections();
    }

    private boolean runUntilFail() {
        for (File collection : settings.getCollections()) {
            if (!runSingleCollection(collection)) {
                return false;
            }
        }
        return true;
    }

    private boolean runAllCollections() {
        boolean success = true;
        for (File collection : settings.getCollections()) {
            success &= runSingleCollection(collection);
        }
        return success;
    }

    private boolean runSingleCollection(File collection) {
        nodeExecRunner.setArguments(asList(
                getWrapperAbsolutePath().toString(),
                getNewmanConfiguration(collection)));

        try {
            return nodeExecRunner.execute().getExitValue() == 0;
        } catch (ExecException ignored) {
            return false;
        }
    }

    private File getWrapperAbsolutePath() {
        return new NewmanWrapper(project).getWrapperAbsolutePath();
    }

    private String getNewmanConfiguration(File collection) {
        NewmanConfig newmanConfig = new NewmanConfig(project, settings);
        return newmanConfig.toJsonFor(collection).replace("\"", "<>");
    }
}
