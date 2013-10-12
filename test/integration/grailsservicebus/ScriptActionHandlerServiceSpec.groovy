package grailsservicebus

import spock.lang.Specification

class ScriptActionHandlerServiceSpec extends Specification {
    //static transactional = false
    ScriptActionHandlerService scriptActionHandlerService

    def setup() {
    }

    def cleanup() {
    }

    void "test good service"() {
        given:
        def source = """
package grailsservicebus.script.unittest

class UnitTest {
    def execute(message, properties) {
        message.hello = "world"
    }
}
"""
        def message = [service:[name:"unittest"]]
        def properties = [:]

        when:
        scriptActionHandlerService.execute(source, message, properties)

        then:
        message == [service:[name:"unittest"], hello:"world"]
    }

    void "test a manually thrown NPE from the service"() {
        def source = """
package grailsservicebus.script.unittest

class UnitTest {
    def execute(message, properties) {
        message.hello = "world"
        throw new NullPointerException()
    }
}
"""
        def message = [service:[name:"unittest"]]
        def properties = [:]

        when:
        scriptActionHandlerService.execute(source, message, properties)

        then:
        println message
        message == [service:[name:"unittest"], hello:"world"]

    }
}