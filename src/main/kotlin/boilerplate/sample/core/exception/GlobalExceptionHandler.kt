package boilerplate.sample.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    protected fun handleCustomException(e: CustomException): ResponseEntity<CustomExceptionResponse> {
        val response = CustomExceptionResponse(error = e.error)
        return ResponseEntity.status(e.statusCode.value()).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ValidationErrorExceptionResponse> {
        val response = ValidationErrorExceptionResponse(error = e.bindingResult.toString())
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(response)
    }
}