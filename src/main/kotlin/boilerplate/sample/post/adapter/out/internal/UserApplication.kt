package boilerplate.sample.post.adapter.out.internal

import boilerplate.sample.post.domain.port.out.GetUserOutputPort
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User
import boilerplate.sample.user.domain.port.`in`.GetUserUseCase

class UserApplication(
    private val getUserUseCase: GetUserUseCase,
) : GetUserOutputPort {
    override fun getUser(command: GetUserCommand): User {
        return getUserUseCase.execute(command)
    }
}
