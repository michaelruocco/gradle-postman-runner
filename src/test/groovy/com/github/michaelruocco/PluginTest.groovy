package com.github.michaelruocco

import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class PluginTest extends Specification {

    def "can execute postman collection with environment file"() {
        when:
        def result = GradleRunner.create()
                .withProjectDir(new File('src/test/resources/test-build'))
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

}