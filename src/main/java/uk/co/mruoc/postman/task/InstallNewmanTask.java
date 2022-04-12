package uk.co.mruoc.postman.task;

import uk.co.mruoc.postman.PostmanRunnerPlugin;
import com.github.gradle.node.NodeExtension;
import com.github.gradle.node.npm.task.NpmSetupTask;
import com.github.gradle.node.npm.task.NpmTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;

import static java.util.Collections.singletonList;

public class InstallNewmanTask extends NpmTask {
    public final static String NAME = "installNewman";

    public InstallNewmanTask() {
        this.setGroup(PostmanRunnerPlugin.GROUP_NAME);
        this.setDescription("Install newman packages");
        setNpmCommand("install");
        setArgs(singletonList("newman" + getNewmanVersionString()));
        dependsOn(NpmSetupTask.NAME);
    }

    @OutputDirectory
    public File getNewmanOutputDir() {
        return new File(getProject().getExtensions().getByType(NodeExtension.class).getNodeProjectDir(),
                "node_modules/newman");
    }

    @Input
    public String getNewmanVersionString() {
        // TODO: 29.11.16 make version configurable
        return "";
    }
}
