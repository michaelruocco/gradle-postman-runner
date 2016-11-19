package de.infonautika.postman.task

import com.moowork.gradle.node.task.NpmSetupTask
import com.moowork.gradle.node.task.NpmTask

class InstallNewmanTask extends NpmTask {
    public final static String NAME = 'installNewman'

    InstallNewmanTask() {
        this.group = 'Postman'
        this.description = 'Install newman packages'
        setNpmCommand('install')
        setArgs(['newman'])
        dependsOn(NpmSetupTask.NAME)

        this.project.afterEvaluate {
            getOutputs().dir(new File((File) this.project.node.nodeModulesDir, 'node_modules/newman'))
        }
    }

}
