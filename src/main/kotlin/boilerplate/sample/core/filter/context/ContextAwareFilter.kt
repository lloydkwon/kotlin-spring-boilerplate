package boilerplate.sample.core.filter.context

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class ContextAwareFilter : Filter {
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        val httpServletRequest = request as HttpServletRequest
        val correlationId = retrieveCorrelationId(httpServletRequest)
        RequestContextStore.set(RequestContext(correlationId))
        chain?.doFilter(request, response)
        RequestContextStore.clear()
    }

    companion object {
        private const val CORRELATION_ID_HEADER_NAME: String = "X-Correlation-Id"

        fun retrieveCorrelationId(httpServletRequest: HttpServletRequest): String {
            return httpServletRequest.getHeader(CORRELATION_ID_HEADER_NAME) ?: UUID.randomUUID()
                .toString()
        }
    }
}
