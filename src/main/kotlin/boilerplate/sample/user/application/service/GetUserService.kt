package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.application.dto.GetUserResponseDto
import boilerplate.sample.user.application.exception.UserNotFoundException
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.port.`in`.GetUserUseCase
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val userRepository: UserRepository,
) : GetUserUseCase {
    override fun execute(command: GetUserCommand): GetUserResponseDto {
        val user = userRepository.findByIdOrNull(command.userId) ?: throw UserNotFoundException()
        return GetUserResponseDto(id = user.id, name = user.name)
    }
}