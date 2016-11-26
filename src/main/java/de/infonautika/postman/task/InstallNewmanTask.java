package de.infonautika.postman.task;

import com.moowork.gradle.node.task.NpmSetupTask;
import com.moowork.gradle.node.task.NpmTask;

import static de.infonautika.postman.PostmanRunnerPlugin.GROUPNAME;
import static java.util.Collections.singletonList;

public class InstallNewmanTask extends NpmTask {
    public final static String NAME = "installNewman";

    public InstallNewmanTask() {
        this.setGroup(GROUPNAME);
        this.setDescription("Install newman packages");
        setNpmCommand("install");
        setArgs(singletonList("newman"));
        dependsOn(NpmSetupTask.NAME);
    }
}
