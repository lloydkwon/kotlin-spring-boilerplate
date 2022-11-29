package boilerplate.sample.core.exception

import org.springframework.http.HttpStatusCode

open class CustomException(
    open val statusCode: HttpStatusCode,
    open val error: String,
) : RuntimeException()
