package boilerplate.sample.user.domain.port.`in`

import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User

interface GetUserUseCase {
    fun execute(command: GetUserCommand): User
}
