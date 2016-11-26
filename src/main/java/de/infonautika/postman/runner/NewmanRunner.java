package de.infonautika.postman.runner;

import com.moowork.gradle.node.exec.NodeExecRunner;
import de.infonautika.postman.PostmanExtension;
import org.apache.commons.io.FileUtils;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.process.internal.ExecException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.util.Arrays.asList;

public class NewmanRunner {
    private String wrapperScriptName = "/startnewman.js";

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
            return runFirstFailStops();
        }
        return runAllCollections();
    }

    private boolean runFirstFailStops() {
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
                getWrapper(),
                new NewmanConfig(project, collection).toJson().replaceAll("\"", "<>")));
        try {
            return runner.execute().getExitValue() == 0;
        } catch (ExecException ignored) {
            return false;
        }
    }

    private void createRunner() {
        runner = new NodeExecRunner(project);
        runner.setIgnoreExitValue(!getConfig().getStopOnError());
    }

    private String getWrapper() {
        File wrapperScript = new File(getExistingPostmanDir(), wrapperScriptName);
        if (!wrapperScript.exists()) {
            try {
                FileUtils.copyURLToFile(getNewmanWrapper(), wrapperScript);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return wrapperScript.getAbsolutePath();
    }

    private File getExistingPostmanDir() {
        File postmanDir = new File(new File(project.getProjectDir() + "/.gradle"), "postman-runner");
        if (!postmanDir.exists()) {
            if (!postmanDir.mkdirs()) {
                throw new RuntimeException("could not create postman-runner directory in " + postmanDir.getAbsolutePath());
            }
        }
        return postmanDir;
    }

    private URL getNewmanWrapper() {
        URL wrapperScriptResource = this.getClass().getResource(wrapperScriptName);
        if (wrapperScriptResource == null) {
            throw new RuntimeException("could not get wrapper script resource");
        }
        return wrapperScriptResource;
    }

    private PostmanExtension getConfig() {
        return project.getExtensions().getByType(PostmanExtension.class);
    }
}
