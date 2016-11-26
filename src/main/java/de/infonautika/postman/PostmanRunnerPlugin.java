package de.infonautika.postman;

import com.moowork.gradle.node.NodePlugin;
import de.infonautika.postman.task.InstallNewmanTask;
import de.infonautika.postman.task.PostmanTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

public class PostmanRunnerPlugin implements Plugin<Project> {

    public final static String GROUPNAME = "Postman";

    @Override
    public void apply(Project project) {
        project.task(type(PostmanTask.class), PostmanTask.NAME);
        project.task(type(InstallNewmanTask.class), InstallNewmanTask.NAME);

        project.getExtensions().create(PostmanExtension.NAME, PostmanExtension.class, project);

        new NodePlugin().apply(project);
    }

    private <T> Map<String, Class<T>> type(Class<T> clazz) {
        HashMap<String, Class<T>> map = new HashMap<>();
        map.put("type", clazz);
        return map;
    }
}
