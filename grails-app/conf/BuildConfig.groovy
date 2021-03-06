grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// this allows to have some common test class in src/groovy and yet remove them from the WAR file
// http://www.obsidianscheduler.com/blog/testing-in-grails-common-base-class-for-unit-integration-functional-tests/
grails.war.resources = { stagingDir ->
    delete(dir:"${stagingDir}/WEB-INF/classes/grailsservicebus/test")
}

//grails.project.fork = [
//        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
//        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
//
//        // configure settings for the test-app JVM, uses the daemon by default
//        test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
//        // configure settings for the run-app JVM
//        run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
//        // configure settings for the run-war JVM
//        war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
//        // configure settings for the Console UI JVM
//        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
//]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        mavenLocal()
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.24'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"

        test "org.codehaus.geb:geb-core:0.6.0",
                "org.gmock:gmock:0.8.1"
        test    "org.codehaus.groovy.modules.http-builder:http-builder:0.5.0", {
            excludes "commons-logging", "xml-apis", "groovy", "httpclient"
        }
        test    "org.seleniumhq.selenium:selenium-htmlunit-driver:2.0a7", {
            excludes "xml-apis", "htmlunit"
        }
        test    "net.sourceforge.htmlunit:htmlunit:2.8", {
            excludes "xml-apis", "commons-logging"
        }

        //build 'com.h2database:h2:1.3.173'
        compile 'net.sf.jt400:jt400-full:6.0'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.42"

        compile ":yammer-metrics:2.1.2-6"

        // plugins for the compile step
        compile ":scaffolding:2.0.0"
        compile ':cache:1.1.1'

        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.1" // or ":hibernate4:4.1.11.1"
        runtime ":database-migration:1.3.5"
        runtime ":jquery:1.10.2"
        runtime ":resources:1.2"
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"
        test    ":geb:0.6.0",
                ":spock:0.7", {
            excludes 'xml-apis'
            exclude "spock-grails-support"
        }
    }
}