# gradle-postman-runner

A gradle plugin to run [Postman](https://www.getpostman.com/) collections.

The runner makes use of [newman](https://github.com/postmanlabs/newman) run by [gradle-node-plugin](https://github.com/srs/gradle-node-plugin).

By default, all postman collection files (ending with `.postman_collection.json`) in `src/test` are run.

#### Usage

```groovy
// postmanrunner is not available in a common repository right now
// maybe you want to use jitpack to check out from github directly?
buildscript {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.simomat:gradle-postman-runner:0.0.3'
    }
}


apply plugin: 'postmanrunner'

// configure gradle-node-plugin
// at least download has to be enabled as of version 0.13
// see gradle-node-plugin documentation for more options
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

