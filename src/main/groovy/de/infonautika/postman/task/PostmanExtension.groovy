package de.infonautika.postman.task

import org.gradle.api.Project
import org.gradle.api.file.FileTree

class PostmanExtension {
    final static String NAME = 'postman'

    def FileTree collections
    def File environment

    FileTree getCollections() {
        return collections
    }

    public void setCollections(FileTree collections) {
        this.collections = collections
    }

    File getEnvironment() {
        return environment
    }

    void setEnvironment(File environment) {
        this.environment = environment
    }

    PostmanExtension(final Project project) {
        this.collections = project.fileTree(dir: 'src/test', include: '**/*.postman_collection.json')
    }
}
