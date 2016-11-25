package de.infonautika.postman.runner

import com.moowork.gradle.node.exec.NodeExecRunner
import de.infonautika.postman.task.PostmanExtension
import org.apache.commons.io.FileUtils
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.process.internal.ExecException

class NewmanRunner {

    private String wrapperScriptName = '/startnewman.js'

    private Project project
    private NodeExecRunner runner

    public NewmanRunner(Project project) {
        this.project = project
    }

    def run() {
        createRunner()
        if (!runCollections()) {
            throw new GradleException("There were failing tests.")
        }
    }

    private boolean runCollections() {
        boolean allSuccessful = true
        if (config.stopOnError) {
            config.collections.find { collection ->
                def testPassed = runOneCollection(collection)
                allSuccessful &= testPassed
                return !allSuccessful
            }
        } else {
            allSuccessful = config.collections.collect { collection ->
                return runOneCollection(collection)
            }.every { result -> return result }
        }

        return allSuccessful
    }

    private boolean runOneCollection(File collection) {
        runner.arguments = [wrapper, new NewmanConfig(project, collection).toJson()]
        try {
            return runner.execute().exitValue == 0
        } catch (ExecException ignored) {
            return false
        }
    }

    private void createRunner() {
        runner = new NodeExecRunner(project)
        runner.ignoreExitValue = !config.stopOnError
    }

    def getWrapper() {
        def wrapperScript = new File(existingPostmanDir, wrapperScriptName)
        if (!wrapperScript.exists()) {
            FileUtils.copyURLToFile(newmanWrapper, wrapperScript)
        }
        return wrapperScript.absolutePath
    }

    private File getExistingPostmanDir() {
        def postmanDir = new File(new File("${project.projectDir}/.gradle"), 'postmanRunner')
        if (!postmanDir.exists()) {
            if (!postmanDir.mkdirs()) {
                throw new RuntimeException("could not create postmanRunner directory in ${postmanDir.absolutePath}")
            }
        }
        return postmanDir
    }

    private URL getNewmanWrapper() {
        def wrapperScriptResource = this.getClass().getResource(wrapperScriptName)
        if (wrapperScriptResource == null) {
            throw new RuntimeException('could not get wrapper script resource')
        }
        return wrapperScriptResource
    }

    private PostmanExtension getConfig() {
        return project.extensions.getByType(PostmanExtension)
    }
}
