package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.port.`in`.CreateUserUseCase
import org.springframework.stereotype.Service

@Service
class CreateUserService(
    userRepository: UserRepository,
) : CreateUserUseCase {
    override fun execute(command: CreateUserCommand) {
        TODO("Not yet implemented")
    }
}