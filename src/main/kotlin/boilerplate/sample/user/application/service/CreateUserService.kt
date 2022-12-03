package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.application.exception.DuplicateNameException
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.converter.UserConverter
import boilerplate.sample.user.domain.model.User
import boilerplate.sample.user.domain.port.`in`.CreateUserUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userRepository: UserRepository,
) : CreateUserUseCase {

    @Transactional
    override fun execute(command: CreateUserCommand): User {
        userRepository.findByName(command.name)?.let { throw DuplicateNameException() }
        val user = UserEntity(name = command.name, password = command.password)
        return UserConverter.from(user)
    }
}