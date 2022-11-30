package boilerplate.sample.user.application.exception

import boilerplate.sample.core.exception.CustomException
import org.springframework.http.HttpStatus

class DuplicateNameException : CustomException(
    error = "USER__DUPLICATE_NAME",
    statusCode = HttpStatus.BAD_REQUEST,
)
