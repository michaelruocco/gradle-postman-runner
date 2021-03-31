package uk.co.mruoc.postman

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import spock.lang.Specification

class PluginTest extends Specification {

    @Rule
    private WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8081).httpsPort(8443))

    private def testProjectDir
    private def buildFile

    def setup() {
        testProjectDir = File.createTempDir()
        testProjectDir.deleteOnExit()

        buildFile = new File(testProjectDir.getPath() + "/build.gradle")
        buildFile.createNewFile()
        buildFile << """
            plugins {
                id 'com.moowork.node' version '1.3.1'
                id 'com.github.michaelruocco.gradle-postman-runner'
            }
        """

        FileUtils.copyDirectory(new File("src/test/resources"), testProjectDir)
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
                .withProjectDir(testProjectDir)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

    def "can execute postman collection with globals file"() {
        given:
        buildFile << """
            postman {
                collections = fileTree(dir: '.', include: '*postman_collection.json')
                globals = file('./postman_globals.json')
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
                .withProjectDir(testProjectDir)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
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
                .withProjectDir(testProjectDir)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

    def "can execute postman collection with env vars"() {
        given:
        buildFile << """
            postman {
                collections = fileTree(dir: '.', include: '*postman_collection.json')
                envVars = [ "host" : "http://localhost:8081" ]
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
                .withProjectDir(testProjectDir)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

    def "can execute postman collection with global vars"() {
        given:
        buildFile << """
            postman {
                collections = fileTree(dir: '.', include: '*postman_collection.json')
                globalVars = [ "host" : "http://localhost:8081" ]
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
                .withProjectDir(testProjectDir)
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        println result.output
        result.task(":postman").outcome == SUCCESS
    }

}