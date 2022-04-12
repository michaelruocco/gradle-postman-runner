package uk.co.mruoc.postman.task;

import uk.co.mruoc.postman.PostmanExtension;
import uk.co.mruoc.postman.PostmanRunnerPlugin;
import uk.co.mruoc.postman.newman.NewmanRunner;
import uk.co.mruoc.postman.settings.PreferredSettings;
import com.github.gradle.node.task.NodeSetupTask;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.Map;

import static java.util.Arrays.asList;

public class PostmanTask extends DefaultTask {
    public static final String NAME = "postman";

    private PreferredSettings settings;

    public PostmanTask() {
        setGroup(PostmanRunnerPlugin.GROUP_NAME);
        setDescription("executes Postman collections");
        dependsOn(asList(NodeSetupTask.NAME, InstallNewmanTask.NAME, DeployNewmanWrapperTask.NAME));

        buildSettings();
    }

    private void buildSettings() {
        settings = new PreferredSettings(() -> getProject().getExtensions().getByType(PostmanExtension.class));
    }

    @TaskAction
    public void runPostmanCollections() {
        NewmanRunner newmanRunner = new NewmanRunner(getProject(), settings);
        if (!newmanRunner.runCollections()) {
            throw new GradleException("There were failing tests.");
        }
    }

    public void setCollections(FileTree collections) {
        settings.setCollections(collections);
    }

    public void setEnvironment(File environment) {
        settings.setEnvironment(environment);
    }

    public void setGlobals(File globals) {
        settings.setGlobals(globals);
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

    public void setNoColor(boolean noColor) {
        settings.setNoColor(noColor);
    }

    public void setDisableUnicode(boolean disableUnicode) {
        settings.setDisableUnicode(disableUnicode);
    }

    public void setSecure(boolean secure) {
        settings.setSecure(secure);
    }

    public void setIgnoreRedirects(boolean ignoreRedirects) {
        settings.setIgnoreRedirects(ignoreRedirects);
    }

    public void setHtmlReportDir(String htmlReportDir) {
        settings.setHtmlReportDir(htmlReportDir);
    }

    public void setHtmlTemplate(String htmlTemplate) {
        settings.setHtmlTemplate(htmlTemplate);
    }

    public void setJsonReportDir(String jsonReportDir) {
        settings.setJsonReportDir(jsonReportDir);
    }

    public void setEnvVars(Map<String, String> envVars) {
        settings.setEnvVars(envVars);
    }

    public void setGlobalVars(Map<String, String> globalVars) {
        settings.setGlobalVars(globalVars);
    }
}
