package de.infonautika.postman.task;

import com.moowork.gradle.node.task.SetupTask;
import de.infonautika.postman.runner.NewmanRunner;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;
import static java.util.Arrays.asList;

public class PostmanTask extends DefaultTask {
    public final static String NAME = "postman";

    public PostmanTask() {
        setGroup(GROUP_NAME);
        setDescription("executes Postman collections");
        dependsOn(asList(SetupTask.NAME, InstallNewmanTask.NAME, DeployPostmanWrapperTask.NAME));
    }

    @TaskAction
    public void runPostmanCollections() {
        new NewmanRunner(getProject()).run();
    }
}
