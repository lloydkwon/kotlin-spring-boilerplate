package boilerplate.sample.core.util

import boilerplate.sample.core.exception.CustomException
import org.springframework.http.HttpStatus

class JwtTokenDecodeException : CustomException(
    error = "AUTH__DECODE_TOKEN_ERROR",
    statusCode = HttpStatus.UNAUTHORIZED,
)