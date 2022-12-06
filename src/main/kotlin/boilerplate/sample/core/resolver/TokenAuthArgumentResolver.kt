package boilerplate.sample.core.resolver

import boilerplate.sample.core.util.JwtTokenUtil
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class TokenAuthArgumentResolver(
    private val jwtTokenUtil: JwtTokenUtil,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(TokenAuth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): CurrentUser {
        val currentUser = CurrentUser()
        val authorization = webRequest.getHeader("Authorization") ?: return currentUser

        try {
            val token = authorization.split(" ")[1]
            val payload = jwtTokenUtil.decode(token)
            currentUser.userId = payload.userId
        } catch (e: Exception) {
            return currentUser
        }
        return currentUser
    }
}
