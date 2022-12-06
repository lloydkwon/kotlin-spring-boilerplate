package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.adapter.`in`.controller.response.CreateUserResponse
import boilerplate.sample.user.adapter.`in`.controller.response.GetUserResponse
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.port.`in`.CreateUserUseCase
import boilerplate.sample.user.domain.port.`in`.GetUserUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserV1Controller(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) {
    @GetMapping("{userId}")
    fun getUser(@PathVariable("userId") userId: Long): ResponseEntity<GetUserResponse> {
        val command = GetUserCommand(userId = userId)
        val user = getUserUseCase.execute(command)
        val response = GetUserResponse(id = user.id, name = user.name)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("")
    fun createUser(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<CreateUserResponse> {
        val command = CreateUserCommand(
            password = request.password,
            name = request.name,
        )
        val user = createUserUseCase.execute(command)
        val response = CreateUserResponse(id = user.id, name = user.name)
        return ResponseEntity.ok().body(response)
    }
}
