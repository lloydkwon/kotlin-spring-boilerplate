package boilerplate.sample.user.application.exception

import boilerplate.sample.core.exception.CustomException
import org.springframework.http.HttpStatus

class DuplicateNameException : CustomException(
    error = "",
    statusCode = HttpStatus.NOT_FOUND,
)
