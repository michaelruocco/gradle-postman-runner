package com.github.michaelruocco;

import com.github.michaelruocco.task.DeployNewmanWrapperTask;
import com.github.michaelruocco.task.InstallNewmanTask;
import com.github.michaelruocco.task.PostmanTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

public class PostmanRunnerPlugin implements Plugin<Project> {

    private static final String COM_MOOWORK_NODE = "com.moowork.node";

    public final static String GROUP_NAME = "Postman";

    @Override
    public void apply(Project project) {
        createTasks(project);
        createExtension(project);

        project.getPluginManager().apply(COM_MOOWORK_NODE);
    }

    private void createExtension(Project project) {
        project.getExtensions().create(PostmanExtension.NAME, PostmanExtension.class, project);
    }

    private void createTasks(Project project) {
        project.task(type(PostmanTask.class), PostmanTask.NAME);
        project.task(type(InstallNewmanTask.class), InstallNewmanTask.NAME);
        project.task(type(DeployNewmanWrapperTask.class), DeployNewmanWrapperTask.NAME);
    }

    private <T> Map<String, Class<T>> type(Class<T> clazz) {
        HashMap<String, Class<T>> map = new HashMap<>();
        map.put("type", clazz);
        return map;
    }
}
