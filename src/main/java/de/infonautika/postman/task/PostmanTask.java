package de.infonautika.postman.task;

import com.moowork.gradle.node.task.SetupTask;
import de.infonautika.postman.PostmanExtension;
import de.infonautika.postman.newman.NewmanRunner;
import de.infonautika.postman.settings.NewmanSettings;
import de.infonautika.postman.settings.PreferredSettings;
import de.infonautika.postman.task.util.Supplier;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;
import static java.util.Arrays.asList;

public class PostmanTask extends DefaultTask {
    public final static String NAME = "postman";

    private PreferredSettings settings;

    public PostmanTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");
        dependsOn(asList(SetupTask.NAME, InstallNewmanTask.NAME, DeployNewmanWrapperTask.NAME));

        buildSettings();
    }

    private void buildSettings() {
        settings = new PreferredSettings(new Supplier<NewmanSettings>() {
            @Override
            public NewmanSettings get() {
                return getProject().getExtensions().getByType(PostmanExtension.class);
            }

        });
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
    
    public void setData(File data) {
    	settings.setData(data);
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

    public void setHtmlReportDir(String htmlReportDir) {
        settings.setHtmlReportDir(htmlReportDir);
    }

    public void setHtmlTemplate(String htmlTemplate) {
        settings.setHtmlTemplate(htmlTemplate);
    }

    public void setJsonReportDir(String jsonReportDir) {
        settings.setJsonReportDir(jsonReportDir);
    }
}
