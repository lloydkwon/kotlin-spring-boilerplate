package boilerplate.sample.user.adapter.`in`.controller

import boilerplate.sample.core.util.JwtTokenUtil
import boilerplate.sample.user.adapter.`in`.controller.request.CreateUserRequest
import boilerplate.sample.user.adapter.`in`.controller.response.CreateUserResponse
import boilerplate.sample.user.adapter.`in`.controller.response.GetUserResponse
import boilerplate.sample.user.application.service.CreateUserService
import boilerplate.sample.user.application.service.GetUserService
import boilerplate.sample.user.domain.command.CreateUserCommand
import boilerplate.sample.user.domain.command.GetUserCommand
import boilerplate.sample.user.domain.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.ExpectSpec
import io.mockk.every
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(UserV1Controller::class)
class UserV1ControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val getUserService: GetUserService,
    @MockkBean private val createUserService: CreateUserService,
    @MockkBean private val jpaMetamodelMappingContext: JpaMetamodelMappingContext,
    @MockkBean private val jwtTokenUtil: JwtTokenUtil,
) : ExpectSpec({
    val objectMapper = ObjectMapper().registerKotlinModule()

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

    context("유저 조회 API") {
        expect("유저가 있는 경우 유저 정보를 응답으로 내려보낸다") {
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
                    print()
                    document("GET /api/v1/users/{userId}")
                }
        }
    }

    context("유저 생성 API") {
        expect("유저 생성에 성공하면 생성한 유저의 정보를 응답으로 내려보낸다") {
            val command = makeCreateUserCommand()
            val user = makeUser()
            val request = makeCreateUserRequest()
            val response = makeCreateUserResponse(user.id, user.name)

            every { createUserService.execute(command) } answers { user }
            mockMvc.post("/api/v1/users") {
                content = objectMapper.writeValueAsString(request)
                contentType = MediaType.APPLICATION_JSON
            }
                .andExpect {
                    status { isOk() }
                    content { string(objectMapper.writeValueAsString(response)) }
                }
                .andDo {
                    print()
                    document("POST /api/v1/users")
                }
        }
    }
})
