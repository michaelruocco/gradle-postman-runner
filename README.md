# gradle-postman-runner

A gradle plugin to run [Postman](https://www.getpostman.com/) collections.

The runner makes use of [newman](https://github.com/postmanlabs/newman) run by [gradle-node-plugin](https://github.com/srs/gradle-node-plugin).

By default, all postman collection files (ending with `.postman_collection.json`) in `src/test` are run.

#### Usage

To test the plugin from local repo (If updated plugin source is not updated in gradle plugin repository)

Copy the generated jar file postman-runner-0.0.7.jar and other dependent jar files in your project lib directory.

buildscript {
    dependencies {
        classpath files('lib/postman-runner-0.0.7.jar')
        classpath files('lib/gradle-node-plugin-1.1.0.jar')
        classpath files('lib/gson-2.8.2.jar')
        classpath files('lib/commons-io-1.3.2.jar')
    }
}

apply plugin: 'de.infonautika.postman'


For gradle version < 2.1:

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.de.infonautika.postman:postman-runner:0.0.5"
  }
}

apply plugin: "de.infonautika.postman"
```

For newer gradle versions:

```groovy
plugins {
  id "de.infonautika.postman" version "0.0.5"
}
```

If not already done, you may want to configure the gradle-node-plugin. At least download has to be enabled as of version 0.13. See [gradle-node-plugin](https://github.com/srs/gradle-node-plugin) documentation for more options.

```groovy
node {
   download = true
}
```

That's all needed to execute the postman task:
`gradle postman`

An optional configuration of postman itself is done with the `postman` extension:

```groovy

// postmanrunner configuration
postman {
    // specifies collection file pattern
    // default: src/test/**/*.postman_collection.json
    collections = fileTree(dir: 'src/test', include: '**/*.myCollection*')
    
    // specifies the test environment to execute the collections with
    // default: no environment
    environment = file('src/test/some_environment.postman_environment.json')

    // specifies the data json to execute the collection with
    // default: no data files
    data = file('src/test/some_data_file.json')
    
    // stops entire execution on first failing test in a collection
    // default: false
    stopOnError = true

    // reports to stdout
    // default: true
    cliReport = false
    
    // when windows gives you lemons on cli output...
    // default: false
    disableUnicode = false
    
    // creates junit compatible XML result files in directory
    // default: off
    xmlReportDir = 'build/testoutput'
    
    // adds html output with default template to given path
    // default: off
    htmlReportDir = 'build/tesdtoutput/html'
    
    // define a handlebars template for html output
    // default: newman's default template https://github.com/postmanlabs/newman/blob/develop/lib/reporters/html/template-default.hbs
    htmlTemplate = 'custom-template.hbs'
    
    // adds plain json output with default template to given path
    // default: off
    jsonReportDir = 'build/tesdtoutput/json'
}

```


Besides a global configuration with the 'postman' extension as seen above, it's also possible to create a custom task and override all of the global configuration values (if any given):

```groovy
task postmanOnDifferentEnvifonment(type: de.infonautika.postman.task.PostmanTask) {
    environment = file('different_environment.postman_environment.json')
    stopOnError = false
}
```

