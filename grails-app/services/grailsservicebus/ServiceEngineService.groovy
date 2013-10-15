package grailsservicebus

import org.apache.commons.logging.LogFactory

class ServiceEngineService {
    private static final log = LogFactory.getLog(this)
    def grailsApplication
    def serviceDefinitionService

    static transactional = false

    @org.grails.plugins.yammermetrics.groovy.Timed
    @org.grails.plugins.yammermetrics.groovy.Metered
    def execute(message) {
        if (log.isTraceEnabled()) {
            log.trace "Entered def execute(message)"
            log.trace "message = \"${message}\""
        }

        log.trace "Getting definition of service name = \"${message.service.name}\""
        def definition = serviceDefinitionService.getDefinition(message.service.name, message)

        if (log.isTraceEnabled()) {
            log.trace "definition = \"${definition}\""
            log.trace "looping actions"
        }

        for(action in definition.actions) {
            if (log.isTraceEnabled()) {
                log.trace "Action = \"${action}\""
            }
            def actionHandlerName = "${action.handler}ActionHandlerService"

            log.trace "Loading \"${actionHandlerName}\" service"
            def actionHandler = grailsApplication.mainContext."${actionHandlerName}"

            if (log.isTraceEnabled()) {
                log.trace "Executing the action with:"
                log.trace "action = \"${action}\""
                log.trace "message = \"${message}\""
                log.trace "properties = \"${action.properties}\""
            }
            actionHandler.execute(action, message, action.properties)
            log.trace "return from executing action"
        }

//        } else {
//            def errorMessage = "Definition file \"${definitionFile}\" does not exist."
//            log.error errorMessage
//            ServiceUtil.throwException(message, "ServiceRegistryException", errorMessage)
//        }
        if (log.isTraceEnabled()) {
            log.trace "returning message = \"${message}\""
            log.trace "Leaving def execute(message)"
        }
    }
}
