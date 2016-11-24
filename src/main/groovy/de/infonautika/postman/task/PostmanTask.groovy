package de.infonautika.postman.task

import com.moowork.gradle.node.task.SetupTask
import de.infonautika.postman.runner.NewmanRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PostmanTask extends DefaultTask {
    public final static String NAME = 'postman'

    PostmanTask() {
        this.group = 'Postman'
        this.description = 'executes Postman collections'
        dependsOn([SetupTask.NAME, InstallNewmanTask.NAME])
    }

    @TaskAction
    def exec() {
        new NewmanRunner(project, config.collections).run()
    }

    def getConfig() {
        return project.extensions.getByType(PostmanExtension)
    }
}
