package de.infonautika.postman.runner

import com.moowork.gradle.node.exec.NodeExecRunner
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.file.FileCollection

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
        def runner = new NodeExecRunner(project)
        collections.each { collection ->
            runner.arguments = [ wrapper, collection ] + wrapperArguments
            runner.execute()
        }
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
