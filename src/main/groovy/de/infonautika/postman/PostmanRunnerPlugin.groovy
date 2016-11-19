package de.infonautika.postman

import com.moowork.gradle.node.NodePlugin
import de.infonautika.postman.task.InstallNewmanTask
import de.infonautika.postman.task.PostmanTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PostmanRunnerPlugin implements Plugin<Project>  {

    @Override
    void apply(Project project) {
        project.task(PostmanTask.NAME, type: PostmanTask)
        project.task(InstallNewmanTask.NAME, type: InstallNewmanTask)
        new NodePlugin().apply(project)
    }

}
