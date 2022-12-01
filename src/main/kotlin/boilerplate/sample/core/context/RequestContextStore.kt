package boilerplate.sample.core.context

import org.slf4j.MDC

class RequestContextStore {
    companion object {
        private const val CORRELATION_ID_MDC_KEY: String = "CorrelationId"
        var INITIALIZED: RequestContext = RequestContext("NOT Initialized")
        private val requestContextContainer: ThreadLocal<RequestContext> = ThreadLocal()

        fun set(requestContext: RequestContext) {
            requestContextContainer.set(requestContext)
            MDC.put(CORRELATION_ID_MDC_KEY, requestContext.correlationId)
        }

        fun get(): RequestContext {
            return requestContextContainer.get() ?: INITIALIZED
        }

        fun clear() {
            requestContextContainer.set(INITIALIZED)
            MDC.remove(CORRELATION_ID_MDC_KEY)
        }
    }
}
