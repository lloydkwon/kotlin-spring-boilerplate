package boilerplate.sample.user.domain.port.`in`

import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.model.User

interface CreateUserUseCase {
    fun execute(command: CreateUserCommand): User
}