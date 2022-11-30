package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.adapter.`in`.controller.response.CreateUserResponse
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.port.`in`.CreateUserUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserV1Controller(
    private val crerateUserUsecase: CreateUserUseCase,
) {
    @PostMapping("")
    fun createUser(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<CreateUserResponse> {
        val command = CreateUserCommand(
            password = request.password,
            name = request.name,
        )
        val user = crerateUserUsecase.execute(command)
        val response = CreateUserResponse(id = user.id, name = user.name)
        return ResponseEntity.ok().body(response)
    }
}