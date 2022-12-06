package boilerplate.sample.core.interceptor

import boilerplate.sample.core.util.JwtTokenUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenAuthInterceptor(
    private val authHeader: String = "Authorization",
    private val jwtTokenUtil: JwtTokenUtil,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }

        val token = extraceTokenFromRequest(request)
        try {
            jwtTokenUtil.decode(token)
        } catch (e: Exception) {
            throw AuthenticationException()
        }
        return true
    }

    private fun extraceTokenFromRequest(request: HttpServletRequest): String {
        val authorization = request.getHeader(authHeader) ?: throw AuthenticationException()

        try {
            return authorization.split(" ")[1]
        } catch (e: Exception) {
            throw AuthenticationException()
        }
    }
}
