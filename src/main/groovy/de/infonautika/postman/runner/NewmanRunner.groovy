package de.infonautika.postman.runner

import com.moowork.gradle.node.exec.NodeExecRunner
import org.apache.commons.io.FileUtils
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.process.internal.ExecException

class NewmanRunner {

    private String wrapperScriptName = '/startnewman.js'

    private Project project
    private FileCollection collections
    private File environment

    public NewmanRunner(Project project, FileCollection collections, File environment) {
        this.project = project
        this.collections = collections
        this.environment = environment
    }

    def run() {
        NodeExecRunner runner = createRunner()

        def results = collections.collect { collection ->
            runner.arguments = [wrapper, collection] + wrapperArguments
            try {
                return runner.execute().exitValue == 0
            } catch (ExecException e) {
                return false
            }
        }

        if (!results.every { result -> return result }) {
            throw new GradleException("There were failing tests.")
        }
    }

    private NodeExecRunner createRunner() {
        def runner = new NodeExecRunner(project)
        runner.ignoreExitValue = true
        return runner
    }

    def getWrapperArguments() {
        if (environment != null) {
            return [ environment ]
        }
        return []
    }

    def getWrapper() {
        def wrapperScript = new File(existingPostmanDir, wrapperScriptName)
        if (!wrapperScript.exists()) {
            FileUtils.copyURLToFile(newmanWrapper, wrapperScript)
        }
        return wrapperScript.absolutePath
    }

    private File getExistingPostmanDir() {
        def postmanDir = new File(project.getBuildDir(), 'postmanRunner')
        if (!postmanDir.exists()) {
            if (!postmanDir.mkdirs()) {
                throw new RuntimeException('could not create postmanRunner directory in ' + postmanDir.absolutePath)
            }
        }
        return postmanDir
    }

    URL getNewmanWrapper() {
        def wrapperScriptResource = this.getClass().getResource(wrapperScriptName)
        if (wrapperScriptResource == null) {
            throw new RuntimeException('could not get wrapper script resource')
        }
        return wrapperScriptResource
    }
}
