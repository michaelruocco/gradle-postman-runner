# gradle-postman-runner

A gradle plugin to run [Postman](https://www.getpostman.com/) collections.

The runner makes use of [newman](https://github.com/postmanlabs/newman) run by [gradle-node-plugin](https://github.com/srs/gradle-node-plugin).

By default, all postman collection files (ending with `.postman_collection.json`) in `src/test` are run. Since postmanrunner is in very early stage, there is not much to configure right now.

#### Usage

###### Configure your gradle file

```
// postmanrunner is not available in a common repository right now
// maybe you want to use jitpack to check out master directly?
buildscript {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.simomat:gradle-postman-runner:-SNAPSHOT'
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

###### Apply the task

```
gradle postman
```
