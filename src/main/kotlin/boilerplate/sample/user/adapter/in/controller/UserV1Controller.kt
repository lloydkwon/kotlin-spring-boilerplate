package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.port.`in`.CreateUserUseCase
import jakarta.validation.Valid
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
    fun createUser(@RequestBody @Valid request: CreateUserRequest) {
        val command = CreateUserCommand(
            password = request.password,
            name = request.name,
        )
        crerateUserUsecase.execute(command)
    }
}