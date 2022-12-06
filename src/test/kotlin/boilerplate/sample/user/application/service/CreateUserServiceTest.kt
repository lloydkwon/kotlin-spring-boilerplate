package boilerplate.sample.user.application.service

import boilerplate.sample.user.adapter.out.persistence.entity.UserEntity
import boilerplate.sample.user.adapter.out.persistence.repository.UserRepository
import boilerplate.sample.user.application.exception.DuplicateNameException
import boilerplate.sample.user.domain.command.CreateUserCommand
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CreateUserServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val createUserService = CreateUserService(userRepository = userRepository)

    fun makeCreateUserCommand(): CreateUserCommand {
        return CreateUserCommand(
            name = "test",
            password = "1234",
        )
    }

    fun makeUserEntity(): UserEntity {
        return UserEntity(name = "test", password = "1234")
    }

    given("CreateUserService") {
        val command = makeCreateUserCommand()
        val userEntity = makeUserEntity()

        `when`("중복되는 이름이 존재하면") {
            every { userRepository.findByName(any<String>()) } answers { userEntity }
            then("DuplicateNameException 예외 발생") {
                shouldThrow<DuplicateNameException> { createUserService.execute(command) }
            }
        }
        `when`("중복되는 이름이 없다면") {
            every { userRepository.findByName(any<String>()) } answers { null }
            every { userRepository.save(any<UserEntity>()) } answers { userEntity }
            then("정상적으로 저장된다") {
                val result = createUserService.execute(command)
                result.id shouldBe null
                result.name shouldBe "test"
            }
        }
    }
})
