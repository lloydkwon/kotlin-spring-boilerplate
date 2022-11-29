package boilerplate.sample.core.interceptor

import boilerplate.sample.core.exception.CustomException
import org.springframework.http.HttpStatus

class AuthenticationException : CustomException(
    error = "AUTH__ERROR",
    statusCode = HttpStatus.UNAUTHORIZED,
)
