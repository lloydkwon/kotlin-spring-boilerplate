package boilerplate.sample.core.exception

import org.springframework.http.HttpStatus

open class CustomException(
    open val statusCode: HttpStatus,
    open val error: String,
) : RuntimeException()
