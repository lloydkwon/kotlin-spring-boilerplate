package boilerplate.sample.user.application.exception

import boilerplate.sample.core.exception.CustomException
import org.springframework.http.HttpStatus

class UserNotFoundException : CustomException(
    statusCode = HttpStatus.NOT_FOUND,
    error = "USER__NOT_FOUND"
)
