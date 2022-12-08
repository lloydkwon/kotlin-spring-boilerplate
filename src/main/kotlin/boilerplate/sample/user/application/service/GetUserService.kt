package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.application.exception.UserNotFoundException
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.converter.UserConverter
import boilerplate.sample.user.domain.model.User
import boilerplate.sample.user.domain.port.`in`.GetUserUseCase
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetUserService(
    private val userRepository: UserRepository,
) : GetUserUseCase {
    override fun execute(command: GetUserCommand): User {
        val user = userRepository.findByIdOrNull(command.userId) ?: throw UserNotFoundException()
        return UserConverter.from(user)
    }
}
