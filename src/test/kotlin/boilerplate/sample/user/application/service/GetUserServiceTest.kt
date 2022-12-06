package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.application.exception.UserNotFoundException
import boilerplate.sample.user.domain.command.GetUserCommand
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
class GetUserServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val getUserService = GetUserService(userRepository = userRepository)

    fun makeGetUserCommand(): GetUserCommand {
        return GetUserCommand(userId = 1L)
    }

    fun makeUserEntity(): UserEntity {
        return UserEntity(name = "test", password = "1234")
    }

    given("GetUserService") {
        val command = makeGetUserCommand()
        val userEntity = makeUserEntity()

        `when`("유저가 존재하지 않는다면") {
            every { userRepository.findByIdOrNull(any<Long>()) } answers { null }
            then("UserNotFound 예외 발생") {
                shouldThrow<UserNotFoundException> { getUserService.execute(command) }
            }
        }
        `when`("유저가 존재한다면") {
            every { userRepository.findByIdOrNull(any<Long>()) } answers { userEntity }
            then("유저 정보를 반환") {
                val result = getUserService.execute(command)
                result.id shouldBe null
                result.name shouldBe "test"
            }
        }
    }
})
