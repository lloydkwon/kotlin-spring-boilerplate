package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.adapter.`in`.controller.response.CreateUserResponse
import boilerplate.sample.user.adapter.`in`.controller.response.GetUserResponse
import boilerplate.sample.user.application.service.CreateUserService
import boilerplate.sample.user.application.service.GetUserService
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(UserV1Controller::class)
class UserV1ControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var getUserService: GetUserService

    @MockkBean
    private lateinit var createUserService: CreateUserService

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

    fun makeGetUserResponse(id: Long?, name: String): GetUserResponse {
        return GetUserResponse(id = id, name = name)
    }

    fun makeCreateUserResponse(id: Long?, name: String): CreateUserResponse {
        return CreateUserResponse(id = id, name = name)
    }


    @Test
    fun `유저를 조회한다`() {
        val command = makeGetUserCommand()
        val user = makeUser()
        val response = makeGetUserResponse(user.id, user.name)
        every { getUserService.execute(command) } answers { user }

        mockMvc.get("/api/v1/users/1")
            .andExpect {
                status { isOk() }
                content { string(objectMapper.writeValueAsString(response)) }
            }
            .andDo {
                handle(document("GET /api/v1/users/{userId}"))
            }
    }

    @Test
    fun `유저를 생성한다`() {
        val command = makeCreateUserCommand()
        val user = makeUser()
        val request = makeCreateUserRequest()
        val response = makeCreateUserResponse(user.id, user.name)

        every { createUserService.execute(command) } answers { user }
        mockMvc.post("/api/v1/users") {
            jsonContent(request)
        }
            .andExpect {
                status { isOk() }
                content { (objectMapper.writeValueAsString(response)) }
            }
            .andDo {
                handle(document("POST /api/v1/users"))
            }
    }
}
