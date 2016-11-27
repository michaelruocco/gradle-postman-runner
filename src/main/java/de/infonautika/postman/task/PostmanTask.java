package de.infonautika.postman.task;

import com.moowork.gradle.node.exec.NodeExecRunner;
import com.moowork.gradle.node.task.SetupTask;
import de.infonautika.postman.PostmanExtension;
import de.infonautika.postman.settings.NewmanSettings;
import de.infonautika.postman.settings.PreferredSettings;
import de.infonautika.postman.task.util.Supplier;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.internal.ExecException;

import java.io.File;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;
import static java.util.Arrays.asList;

public class PostmanTask extends AbstractPostmanRunnerTask {
    public final static String NAME = "postman";

    private NodeExecRunner runner;
    private NewmanConfig newmanConfig;
    private PreferredSettings settings;

    public PostmanTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");
        dependsOn(asList(SetupTask.NAME, InstallNewmanTask.NAME, DeployPostmanWrapperTask.NAME));

        buildConfig();
    }

    private void buildConfig() {
        settings = new PreferredSettings(new Supplier<NewmanSettings>() {
            @Override
            public NewmanSettings get() {
                return getProject().getExtensions().getByType(PostmanExtension.class);
            }

        });
        newmanConfig = new NewmanConfig(getProject(), settings);
    }

    @TaskAction
    public void runPostmanCollections() {
        createRunner();
        if (!runCollections()) {
            throw new GradleException("There were failing tests.");
        }
    }

    private boolean runCollections() {
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
        return newmanConfig.toJsonFor(collection).replaceAll("\"", "<>");
    }

    private void createRunner() {
        runner = new NodeExecRunner(getProject());
        runner.setIgnoreExitValue(!settings.getStopOnError());
    }

    public void setCollections(FileTree collections) {
        settings.setCollections(collections);
    }

    public void setEnvironment(File environment) {
        settings.setEnvironment(environment);
    }

    public void setCliReport(boolean cliReport) {
        settings.setCliReport(cliReport);
    }

    public void setXmlReportDir(String xmlReportDir) {
        settings.setXmlReportDir(xmlReportDir);
    }

    public void setStopOnError(boolean stopOnError) {
        settings.setStopOnError(stopOnError);
    }
}
