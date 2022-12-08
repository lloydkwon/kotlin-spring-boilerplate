package boilerplate.sample.post.domain.port.out

import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User

interface GetUserOutputPort {
    fun getUser(command: GetUserCommand): User
}
