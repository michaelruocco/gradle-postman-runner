package uk.co.mruoc.postman

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class InsecureTest extends Specification {

    @Rule
    private TemporaryFolder testProjectDir = new TemporaryFolder()

    @Rule
    private WireMockRule wireMockRule = new WireMockRule(wireMockConfig().httpsPort(8443))

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

    def "can execute postman collection with https"() {
        given:
        buildFile << """
            postman {
                collections = fileTree(dir: '.', include: '*postman_collection.json')
                environment = file('./https.postman_environment.json')
                secure = false
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