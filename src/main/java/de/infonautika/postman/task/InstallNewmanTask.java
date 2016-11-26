package de.infonautika.postman.task;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.task.NpmSetupTask;
import com.moowork.gradle.node.task.NpmTask;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUP_NAME;
import static java.util.Collections.singletonList;

public class InstallNewmanTask extends NpmTask {
    public final static String NAME = "installNewman";

    public InstallNewmanTask() {
        this.setGroup(GROUP_NAME);
        this.setDescription("Install newman packages");
        setNpmCommand("install");
        setArgs(singletonList("newman"));
        dependsOn(NpmSetupTask.NAME);
    }

    @OutputDirectory
    public File getNewmanOutputDir() {
        return new File(getProject().getExtensions().getByType(NodeExtension.class).getNodeModulesDir(),
                "node_modules/newman");
    }
}
