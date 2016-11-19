package de.infonautika.postman.task

import com.moowork.gradle.node.task.SetupTask
import de.infonautika.postman.runner.NewmanRunner
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

class PostmanTask extends DefaultTask {
    public final static String NAME = 'postman'

    private FileTree collections
    private File environment

    PostmanTask() {
        collections = project.fileTree(dir: 'src/test', include: '**/*.postman_collection.json')

        this.group = 'Postman'
        this.description = 'what am I doing?'
        dependsOn([SetupTask.NAME, InstallNewmanTask.NAME])

        this.project.afterEvaluate {

        }
    }

    @TaskAction
    def exec() {

        def runner = new NewmanRunner(project, collections, environment)
        runner.run()
    }
}
