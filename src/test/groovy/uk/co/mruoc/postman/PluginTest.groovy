package uk.co.mruoc.postman

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
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
    public WireMockRule wireMockRule = new WireMockRule(8081);

    def "can execute postman collection with environment file"() {
        given:
        stubFor(get(urlEqualTo("/gradle-postman-runner-test"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"field1\":\"ONE\",\"field2\":2}")));

        when:
        def result = GradleRunner.create()
                .withProjectDir(new File('src/test/resources/test-build'))
                .withArguments('postman')
                .withPluginClasspath()
                .build()

        then:
        result.task(":postman").outcome == SUCCESS
    }

}