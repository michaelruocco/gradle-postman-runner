package uk.co.mruoc.postman

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.apache.commons.io.FileUtils
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class PluginTest extends Specification {

    @Rule
    private TemporaryFolder testProjectDir = new TemporaryFolder()

    @Rule
    private WireMockRule wireMockRule = new WireMockRule(8081)

    private def buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'com.moowork.node' version '1.3.1'
                id 'com.github.michaelruocco.gradle-postman-runner'
            }
        """

        FileUtils.copyDirectory(new File("src/test/resources"), testProjectDir.root)
    }

    def "can execute postman collection with environment file"() {
        given:
        buildFile << """
            postman {
                collections = fileTree(dir: '.', include: '*postman_collection.json')
                environment = file('./local.postman_environment.json')
            }
        """

        stubFor(get(urlEqualTo("/gradle-postman-runner-test"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"field1\":\"ONE\",\"field2\":2}")))

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

}