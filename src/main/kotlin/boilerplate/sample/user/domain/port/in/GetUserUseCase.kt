package boilerplate.sample.user.domain.port.`in`

import boilerplate.sample.user.application.dto.GetUserResponseDto
import boilerplate.sample.user.domain.command.GetUserCommand

interface GetUserUseCase {
    fun execute(command: GetUserCommand): GetUserResponseDto
}