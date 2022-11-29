package boilerplate.sample.user.domain.port.`in`

import boilerplate.sample.user.domain.command.CreateUserCommand

interface CreateUserUseCase {
    fun execute(command: CreateUserCommand)
}