package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.application.service.CreateUserService
import boilerplate.sample.user.application.service.GetUserService
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.ExpectSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@ExtendWith(RestDocumentationExtension::class, MockKExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserV1ControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private var getUserService: GetUserService,
    @MockkBean private var createUserService: CreateUserService,
    @MockkBean private var objectMapper: ObjectMapper,
) : ExpectSpec({

    fun makeGetUserCommand(): GetUserCommand {
        return GetUserCommand(userId = 1L)
    }

    fun makeUser(): User {
        return User(id = 1L, name = "test", password = "1234")
    }

    fun makeCreateUserRequest(): CreateUserRequest {
        return CreateUserRequest(
            name = "test",
            password = "123",
        )
    }

    fun makeCreateUserCommand(): CreateUserCommand {
        return CreateUserCommand(name = "test", password = "123")
    }

    context("유저 조회 API") {
        expect("유저가 있는 경우 유저 정보를 응답으로 내려보낸다") {
            val command = makeGetUserCommand()
            val user = makeUser()

            every { getUserService.execute(command) } answers { user }
            mockMvc.get("/api/v1/users/1")
                .andExpect {
                    status { isOk() }
                    jsonPath("id") { "1" }
                    jsonPath("name") { "test" }
                }
                .andDo { print() }
                .andDo {
                    document("/api/v1/users/{userId}")
                }
        }
    }
})
