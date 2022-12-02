package boilerplate.sample.core.filter.log

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

private val logger = KotlinLogging.logger {}

@Component
class RequestLoggingFilter : OncePerRequestFilter() {
    companion object {
        fun getRequestBody(request: ContentCachingRequestWrapper): String {
            val inputStream = ByteArrayInputStream(request.contentAsByteArray)

            return try {
                StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
            } catch (e: IOException) {
                ""
            }
        }

        fun getResponseBody(response: ContentCachingResponseWrapper): String {
            val inputStream = response.contentInputStream

            return try {
                val responseBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
                response.copyBodyToResponse()
                responseBody
            } catch (e: IOException) {
                ""
            }
        }

        fun logRequest(request: ContentCachingRequestWrapper) {
            val method = request.method
            val requestURI = request.requestURI
            val queryString = request.queryString
            val formattedQueryString = if (StringUtils.hasText(queryString)) "?$queryString" else ""

            val requestBody = getRequestBody(request)

            if (requestBody.isEmpty()) {
                logger.info { "Request | $method $requestURI$formattedQueryString" }
            } else {
                logger.info { "Request | $method $requestURI$formattedQueryString | body = $requestBody" }
            }
        }

        fun logResponse(
            request: ContentCachingRequestWrapper,
            response: ContentCachingResponseWrapper
        ) {
            val method = request.method
            val requestURI = request.requestURI
            val queryString = request.queryString
            val formattedQueryString = if (StringUtils.hasText(queryString)) "?$queryString" else ""
            val statusCode = response.status
            val responseBody = getResponseBody(response)

            if (statusCode < 500) {
                if (responseBody.isEmpty()) {
                    logger.info { "Response | $method $requestURI$formattedQueryString | $statusCode" }
                } else {
                    logger.info { "Response | $method $requestURI$formattedQueryString | $statusCode | body = $responseBody" }
                }
            } else {
                if (responseBody.isEmpty()) {
                    logger.error { "Response | $method $requestURI$formattedQueryString | $statusCode" }
                } else {
                    logger.error { "Response | $method $requestURI$formattedQueryString | $statusCode | body = $responseBody" }
                }
            }
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val cachedRequest = ContentCachingRequestWrapper(request)
        val cachedResponse = ContentCachingResponseWrapper(response)

        filterChain.doFilter(cachedRequest, cachedResponse)
        logRequest(cachedRequest)
        logResponse(cachedRequest, cachedResponse)
    }
}
